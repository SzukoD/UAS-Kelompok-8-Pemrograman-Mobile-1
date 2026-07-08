package com.utb.inventoryapp.ui.admin.laporan

import android.content.Intent
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.os.Bundle
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.utb.inventoryapp.databinding.FragmentLaporanDetailBinding
import com.utb.inventoryapp.util.GenericViewModelFactory
import com.utb.inventoryapp.util.inventoryApp

class LaporanDetailFragment : Fragment() {

    private var _binding: FragmentLaporanDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: LaporanViewModel

    // 1. Membuat Launcher untuk membuka jendela "Save As" (File Picker) bawaan HP
    private val createPdfLauncher = registerForActivityResult(ActivityResultContracts.CreateDocument("application/pdf")) { uri: Uri? ->
        if (uri != null) {
            // Jika user memilih folder dan menekan "Save", jalankan proses pembuatan PDF
            val title = binding.tvReportTitle.text.toString()
            val content = binding.tvReportContent.text.toString()
            generateAndSavePdfToUri(uri, title, content)
        } else {
            // Jika user menekan tombol "Batal" atau "Back" di File Manager
            Toast.makeText(requireContext(), "Penyimpanan dibatalkan", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentLaporanDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val repo = requireContext().inventoryApp().repository
        viewModel = ViewModelProvider(this, GenericViewModelFactory { LaporanViewModel(repo) })[LaporanViewModel::class.java]

        val typeName = arguments?.getString("laporanType") ?: LaporanType.BARANG.name
        val laporanType = LaporanType.valueOf(typeName)

        binding.tvReportTitle.text = laporanType.title
        binding.btnBack.setOnClickListener { findNavController().popBackStack() }

        viewModel.reportText.observe(viewLifecycleOwner) { text ->
            binding.tvReportContent.text = text
        }
        viewModel.loadReport(laporanType)

        // 2. Aksi saat tombol Cetak ditekan
        binding.btnCetak.setOnClickListener {
            val title = binding.tvReportTitle.text.toString()
            // Menyiapkan rekomendasi nama file
            val fileName = "${title.replace(" ", "_")}_${System.currentTimeMillis()}.pdf"

            // Memanggil File Picker
            createPdfLauncher.launch(fileName)
        }
    }

    // 3. Fungsi untuk menggambar PDF dan menyimpannya ke Uri (Lokasi) yang dipilih user
    private fun generateAndSavePdfToUri(uri: Uri, title: String, content: String) {
        val pdfDocument = PdfDocument()

        val titlePaint = Paint().apply {
            textSize = 18f
            isFakeBoldText = true
            color = android.graphics.Color.BLACK
        }

        val textPaint = TextPaint().apply {
            textSize = 14f
            color = android.graphics.Color.BLACK
        }

        val pageWidth = 595
        val margin = 50
        val contentWidth = pageWidth - (margin * 2)

        val staticLayout = StaticLayout.Builder.obtain(content, 0, content.length, textPaint, contentWidth)
            .setAlignment(Layout.Alignment.ALIGN_NORMAL)
            .setLineSpacing(0f, 1f)
            .setIncludePad(false)
            .build()

        val pageHeight = staticLayout.height + 150
        val finalHeight = if (pageHeight > 842) pageHeight else 842

        val pageInfo = PdfDocument.PageInfo.Builder(pageWidth, finalHeight, 1).create()
        val page = pdfDocument.startPage(pageInfo)
        val canvas = page.canvas

        // Gambar Judul & Garis
        canvas.drawText(title, margin.toFloat(), 50f, titlePaint)
        canvas.drawLine(margin.toFloat(), 65f, (pageWidth - margin).toFloat(), 65f, titlePaint)

        // Gambar Konten
        canvas.save()
        canvas.translate(margin.toFloat(), 80f)
        staticLayout.draw(canvas)
        canvas.restore()

        pdfDocument.finishPage(page)

        // 4. Menyimpan PDF menggunakan ContentResolver ke alamat yang dipilih user
        try {
            requireContext().contentResolver.openOutputStream(uri)?.use { outputStream ->
                pdfDocument.writeTo(outputStream)
                Toast.makeText(requireContext(), "PDF berhasil disimpan!", Toast.LENGTH_LONG).show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(requireContext(), "Gagal menyimpan PDF: ${e.message}", Toast.LENGTH_SHORT).show()
        } finally {
            pdfDocument.close()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
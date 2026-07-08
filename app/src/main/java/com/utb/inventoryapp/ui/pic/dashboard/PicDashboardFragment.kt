package com.utb.inventoryapp.ui.pic.dashboard

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.utb.inventoryapp.R
import com.utb.inventoryapp.data.local.entity.StatusTransaksi
import com.utb.inventoryapp.databinding.FragmentPicDashboardBinding
import com.utb.inventoryapp.util.inventoryApp
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class PicDashboardFragment : Fragment() {

    private var _binding: FragmentPicDashboardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentPicDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val app = requireContext().inventoryApp()
        val userId = app.sessionManager.getUserId()

        binding.tvNamaLengkap.text = app.sessionManager.getNamaLengkap()
        binding.btnSettings.setOnClickListener { findNavController().navigate(R.id.pengaturanFragment) }

        // PERBAIKAN: Mengubah tvStatLabel menjadi tvStatTitle sesuai XML
        binding.statPeminjaman.tvStatTitle.text = "Peminjaman Saya"
        binding.statSparepart.tvStatTitle.text = "Permintaan Sparepart"
        binding.statPending.tvStatTitle.text = "Menunggu Approval"
        binding.statSelesai.tvStatTitle.text = "Selesai"

        viewLifecycleOwner.lifecycleScope.launch {
            val peminjaman = app.repository.getPeminjamanByUser(userId).first()
            val sparepart = app.repository.getPermintaanSparepartByUser(userId).first()

            binding.statPeminjaman.tvStatValue.text = peminjaman.size.toString()
            binding.statSparepart.tvStatValue.text = sparepart.size.toString()
            binding.statPending.tvStatValue.text =
                (peminjaman.count { it.status == StatusTransaksi.PENDING } + sparepart.count { it.status == StatusTransaksi.PENDING }).toString()
            binding.statSelesai.tvStatValue.text = peminjaman.count { it.status == StatusTransaksi.SELESAI }.toString()

            // Setup grafik mini (sparkline) menyesuaikan warna mockup
            setupMiniChart(binding.statPeminjaman.miniLineChart, arrayListOf(Entry(0f, 2f), Entry(1f, 5f), Entry(2f, 3f), Entry(3f, peminjaman.size.toFloat())), "#2196F3") // Biru
            setupMiniChart(binding.statSparepart.miniLineChart, arrayListOf(Entry(0f, 1f), Entry(1f, 4f), Entry(2f, 2f), Entry(3f, sparepart.size.toFloat())), "#9C27B0") // Ungu
            setupMiniChart(binding.statPending.miniLineChart, arrayListOf(Entry(0f, 5f), Entry(1f, 3f), Entry(2f, 4f), Entry(3f, 2f)), "#FFC107") // Kuning
            setupMiniChart(binding.statSelesai.miniLineChart, arrayListOf(Entry(0f, 10f), Entry(1f, 15f), Entry(2f, 12f), Entry(3f, 20f)), "#4CAF50") // Hijau
        }
    }

    private fun setupMiniChart(chart: LineChart, dataValues: ArrayList<Entry>, colorHex: String) {
        val dataSet = LineDataSet(dataValues, "").apply {
            color = Color.parseColor(colorHex)
            lineWidth = 2f
            setDrawCircles(false)
            setDrawValues(false)
            mode = LineDataSet.Mode.CUBIC_BEZIER // Membuat garis melengkung mulus
            setDrawFilled(true)
            fillColor = Color.parseColor(colorHex)
            fillAlpha = 15 // Transparansi background bawah garis
        }

        chart.apply {
            data = LineData(dataSet)
            description.isEnabled = false
            legend.isEnabled = false
            xAxis.isEnabled = false
            axisLeft.isEnabled = false
            axisRight.isEnabled = false
            setTouchEnabled(false)
            invalidate()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
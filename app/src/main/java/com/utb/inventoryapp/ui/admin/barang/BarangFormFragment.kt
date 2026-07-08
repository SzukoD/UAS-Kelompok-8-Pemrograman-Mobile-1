package com.utb.inventoryapp.ui.admin.barang

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.utb.inventoryapp.data.local.entity.KondisiBarang
import com.utb.inventoryapp.databinding.FragmentBarangFormBinding
import com.utb.inventoryapp.util.GenericViewModelFactory
import com.utb.inventoryapp.util.inventoryApp
import com.utb.inventoryapp.util.toast
import kotlinx.coroutines.launch

class BarangFormFragment : Fragment() {

    companion object {
        private const val ARG_ID = "barangId"
        fun newInstance(id: Long) = BarangFormFragment().apply {
            arguments = Bundle().apply { putLong(ARG_ID, id) }
        }
    }

    private var _binding: FragmentBarangFormBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: BarangViewModel
    private var editingId: Long = 0L

    private var kategoriOptions = listOf<RefOption>()
    private var jenisOptions = listOf<RefOption>()
    private var satuanOptions = listOf<RefOption>()
    private var supplierOptions = listOf<RefOption>()
    private var lokasiOptions = listOf<RefOption>()
    private val kondisiList = listOf(KondisiBarang.BAIK, KondisiBarang.RUSAK, KondisiBarang.HILANG)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentBarangFormBinding.inflate(inflater, container, false)
        editingId = arguments?.getLong(ARG_ID, 0L) ?: 0L
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val repo = requireContext().inventoryApp().repository
        viewModel = ViewModelProvider(this, GenericViewModelFactory { BarangViewModel(repo) })[BarangViewModel::class.java]

        binding.tvFormTitle.text = if (editingId > 0L) "Ubah Barang" else "Tambah Barang"
        binding.btnBack.setOnClickListener { findNavController().popBackStack() }

        binding.spKondisi.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, kondisiList)

        viewModel.kategoriOptions.observe(viewLifecycleOwner) { kategoriOptions = it; refreshSpinner(binding.spKategori, it) }
        viewModel.jenisOptions.observe(viewLifecycleOwner) { jenisOptions = it; refreshSpinner(binding.spJenis, it) }
        viewModel.satuanOptions.observe(viewLifecycleOwner) { satuanOptions = it; refreshSpinner(binding.spSatuan, it) }
        viewModel.supplierOptions.observe(viewLifecycleOwner) { supplierOptions = it; refreshSpinner(binding.spSupplier, it) }
        viewModel.lokasiOptions.observe(viewLifecycleOwner) { lokasiOptions = it; refreshSpinner(binding.spLokasi, it) }

        if (editingId > 0L) {
            viewLifecycleOwner.lifecycleScope.launch {
                val barang = repo.getBarangById(editingId)
                if (barang != null) {
                    binding.etKode.setText(barang.kode)
                    binding.etNama.setText(barang.nama)
                    binding.etStok.setText(barang.stok.toString())
                    binding.etDeskripsi.setText(barang.deskripsi)
                    selectSpinnerById(binding.spKategori, kategoriOptions, barang.kategoriId)
                    selectSpinnerById(binding.spJenis, jenisOptions, barang.jenisBarangId)
                    selectSpinnerById(binding.spSatuan, satuanOptions, barang.satuanId)
                    selectSpinnerById(binding.spSupplier, supplierOptions, barang.supplierId)
                    selectSpinnerById(binding.spLokasi, lokasiOptions, barang.lokasiId)
                    binding.spKondisi.setSelection(kondisiList.indexOf(barang.kondisi).coerceAtLeast(0))
                }
            }
        }

        binding.btnSimpan.setOnClickListener { save() }
    }

    private fun refreshSpinner(spinner: android.widget.Spinner, options: List<RefOption>) {
        spinner.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, options)
    }

    private fun selectSpinnerById(spinner: android.widget.Spinner, options: List<RefOption>, id: Long) {
        val idx = options.indexOfFirst { it.id == id }
        if (idx >= 0) spinner.setSelection(idx)
    }

    private fun save() {
        val kode = binding.etKode.text.toString().trim()
        val nama = binding.etNama.text.toString().trim()
        val stokText = binding.etStok.text.toString().trim()

        if (kode.isEmpty() || nama.isEmpty() || stokText.isEmpty()) {
            toast("Kode, nama, dan stok wajib diisi")
            return
        }
        if (kategoriOptions.isEmpty() || jenisOptions.isEmpty() || satuanOptions.isEmpty() ||
            supplierOptions.isEmpty() || lokasiOptions.isEmpty()
        ) {
            toast("Lengkapi dahulu data master (kategori/jenis/satuan/supplier/lokasi)")
            return
        }

        val kategoriId = (binding.spKategori.selectedItem as RefOption).id
        val jenisId = (binding.spJenis.selectedItem as RefOption).id
        val satuanId = (binding.spSatuan.selectedItem as RefOption).id
        val supplierId = (binding.spSupplier.selectedItem as RefOption).id
        val lokasiId = (binding.spLokasi.selectedItem as RefOption).id
        val kondisi = binding.spKondisi.selectedItem as String
        val stok = stokText.toIntOrNull() ?: 0
        val deskripsi = binding.etDeskripsi.text.toString().trim()

        viewModel.save(editingId, kode, nama, kategoriId, jenisId, satuanId, supplierId, lokasiId, stok, kondisi, deskripsi) {
            toast("Data barang berhasil disimpan")
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

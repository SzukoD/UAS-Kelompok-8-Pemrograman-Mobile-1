package com.utb.inventoryapp.ui.admin.supplier

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.utb.inventoryapp.data.local.entity.Supplier
import com.utb.inventoryapp.databinding.DialogSupplierFormBinding
import com.utb.inventoryapp.databinding.FragmentListGenericBinding
import com.utb.inventoryapp.ui.common.GenericListAdapter
import com.utb.inventoryapp.util.GenericViewModelFactory
import com.utb.inventoryapp.util.inventoryApp
import com.utb.inventoryapp.util.toast

class SupplierListFragment : Fragment() {

    private var _binding: FragmentListGenericBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: SupplierViewModel
    private lateinit var adapter: GenericListAdapter<Supplier>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentListGenericBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val repo = requireContext().inventoryApp().repository
        viewModel = ViewModelProvider(this, GenericViewModelFactory { SupplierViewModel(repo) })[SupplierViewModel::class.java]

        binding.tvHeaderTitle.text = "Kelola Supplier"

        // --- KODE BARU UNTUK TOMBOL KEMBALI ---
        binding.btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        binding.swipeRefresh.isEnabled = false

        adapter = GenericListAdapter(
            areItemsSame = { a, b -> a.id == b.id },
            areContentsSame = { a, b -> a == b },
            onBind = { itemBinding, item ->
                itemBinding.tvTitle.text = item.nama
                itemBinding.tvSubtitle.text = if (item.kontak.isNotBlank()) item.kontak else item.alamat
                itemBinding.tvStatus.visibility = View.GONE
            },
            onEdit = { item -> showForm(item) },
            onDelete = { item -> confirmDelete(item) }
        )
        binding.recyclerView.adapter = adapter
        binding.fabAdd.setOnClickListener { showForm(null) }

        viewModel.items.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
            binding.emptyState.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
        }
    }

    private fun showForm(existing: Supplier?) {
        val dialogBinding = DialogSupplierFormBinding.inflate(LayoutInflater.from(requireContext()))
        dialogBinding.tvDialogTitle.text = if (existing == null) "Tambah Supplier" else "Ubah Supplier"
        dialogBinding.etNama.setText(existing?.nama ?: "")
        dialogBinding.etKontak.setText(existing?.kontak ?: "")
        dialogBinding.etAlamat.setText(existing?.alamat ?: "")

        AlertDialog.Builder(requireContext())
            .setView(dialogBinding.root)
            .setPositiveButton("Simpan") { _, _ ->
                val nama = dialogBinding.etNama.text.toString().trim()
                if (nama.isEmpty()) {
                    toast("Nama wajib diisi")
                    return@setPositiveButton
                }
                viewModel.save(
                    existing?.id ?: 0L, nama,
                    dialogBinding.etKontak.text.toString().trim(),
                    dialogBinding.etAlamat.text.toString().trim()
                )
                toast("Data berhasil disimpan")
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    private fun confirmDelete(item: Supplier) {
        AlertDialog.Builder(requireContext())
            .setTitle("Konfirmasi Hapus")
            .setMessage("Hapus supplier \"${item.nama}\"?")
            .setPositiveButton("Hapus") { _, _ ->
                viewModel.delete(item)
                toast("Data berhasil dihapus")
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
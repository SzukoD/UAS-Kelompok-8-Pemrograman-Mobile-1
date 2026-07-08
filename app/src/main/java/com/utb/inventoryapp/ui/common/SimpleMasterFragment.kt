package com.utb.inventoryapp.ui.common

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.utb.inventoryapp.databinding.DialogMasterFormBinding
import com.utb.inventoryapp.databinding.FragmentListGenericBinding
import com.utb.inventoryapp.util.GenericViewModelFactory
import com.utb.inventoryapp.util.inventoryApp
import com.utb.inventoryapp.util.toast

class SimpleMasterFragment : Fragment() {

    companion object {
        private const val ARG_TYPE = "arg_type"
        fun newInstance(type: MasterType): SimpleMasterFragment {
            val fragment = SimpleMasterFragment()
            fragment.arguments = Bundle().apply { putString(ARG_TYPE, type.name) }
            return fragment
        }
    }

    private var _binding: FragmentListGenericBinding? = null
    private val binding get() = _binding!!

    private lateinit var type: MasterType
    private lateinit var viewModel: SimpleMasterViewModel
    private lateinit var adapter: GenericListAdapter<SimpleMasterItem>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentListGenericBinding.inflate(inflater, container, false)
        type = MasterType.valueOf(requireArguments().getString(ARG_TYPE)!!)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repo = requireContext().inventoryApp().repository
        viewModel = ViewModelProvider(this, GenericViewModelFactory { SimpleMasterViewModel(repo, type) })[SimpleMasterViewModel::class.java]

        binding.tvHeaderTitle.text = type.title

        // DIPERBARUI: Menggunakan parentFragmentManager untuk kembali ke fragment sebelumnya
        binding.btnBack.setOnClickListener {
            // Menutup Fragment saat ini dan memuat Fragment sebelumnya dari memori
            parentFragmentManager.popBackStack()

            // Catatan: Jika project ini memakai Navigation Component (NavHost),
            // gunakan kode ini sebagai gantinya:
            // findNavController().navigateUp()
        }

        binding.swipeRefresh.isEnabled = false

        adapter = GenericListAdapter(
            areItemsSame = { a, b -> a.id == b.id },
            areContentsSame = { a, b -> a == b },
            onBind = { itemBinding, item ->
                itemBinding.tvTitle.text = item.nama
                if (type.hasKeterangan && item.keterangan.isNotBlank()) {
                    itemBinding.tvSubtitle.visibility = View.VISIBLE
                    itemBinding.tvSubtitle.text = item.keterangan
                } else {
                    itemBinding.tvSubtitle.visibility = View.GONE
                }
                itemBinding.tvStatus.visibility = View.GONE
            },
            onEdit = { item -> showFormDialog(item) },
            onDelete = { item -> confirmDelete(item) }
        )
        binding.recyclerView.adapter = adapter

        binding.fabAdd.setOnClickListener { showFormDialog(null) }

        viewModel.items.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
            binding.emptyState.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
        }
    }

    private fun showFormDialog(existing: SimpleMasterItem?) {
        val dialogBinding = DialogMasterFormBinding.inflate(LayoutInflater.from(requireContext()))
        dialogBinding.tvDialogTitle.text = if (existing == null) "Tambah ${type.title.removePrefix("Kelola ")}" else "Ubah ${type.title.removePrefix("Kelola ")}"
        dialogBinding.etNama.setText(existing?.nama ?: "")
        if (type.hasKeterangan) {
            dialogBinding.etKeterangan.visibility = View.VISIBLE
            dialogBinding.etKeterangan.setText(existing?.keterangan ?: "")
        }

        AlertDialog.Builder(requireContext())
            .setView(dialogBinding.root)
            .setPositiveButton("Simpan") { _, _ ->
                val nama = dialogBinding.etNama.text.toString().trim()
                val keterangan = dialogBinding.etKeterangan.text.toString().trim()
                if (nama.isEmpty()) {
                    toast("Nama wajib diisi")
                    return@setPositiveButton
                }
                viewModel.save(existing?.id ?: 0L, nama, keterangan)
                toast("Data berhasil disimpan")
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    private fun confirmDelete(item: SimpleMasterItem) {
        AlertDialog.Builder(requireContext())
            .setTitle("Konfirmasi Hapus")
            .setMessage("Apakah Anda yakin ingin menghapus \"${item.nama}\"?")
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
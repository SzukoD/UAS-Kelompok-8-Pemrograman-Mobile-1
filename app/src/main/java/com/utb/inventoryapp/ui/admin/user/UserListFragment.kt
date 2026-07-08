package com.utb.inventoryapp.ui.admin.user

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.utb.inventoryapp.data.local.entity.Role
import com.utb.inventoryapp.data.local.entity.UserEntity
import com.utb.inventoryapp.databinding.DialogUserFormBinding
import com.utb.inventoryapp.databinding.FragmentListGenericBinding
import com.utb.inventoryapp.ui.common.GenericListAdapter
import com.utb.inventoryapp.util.GenericViewModelFactory
import com.utb.inventoryapp.util.inventoryApp
import com.utb.inventoryapp.util.roleLabel
import com.utb.inventoryapp.util.toast

class UserListFragment : Fragment() {

    private var _binding: FragmentListGenericBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: UserViewModel
    private lateinit var adapter: GenericListAdapter<UserEntity>
    private val roles = listOf(Role.ADMIN, Role.PIC, Role.KEPALA_GUDANG)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentListGenericBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val repo = requireContext().inventoryApp().repository
        viewModel = ViewModelProvider(this, GenericViewModelFactory { UserViewModel(repo) })[UserViewModel::class.java]

        binding.tvHeaderTitle.text = "Kelola Pengguna"

        // --- KODE BARU UNTUK TOMBOL KEMBALI ---
        binding.btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        binding.swipeRefresh.isEnabled = false

        adapter = GenericListAdapter(
            areItemsSame = { a, b -> a.id == b.id },
            areContentsSame = { a, b -> a == b },
            onBind = { itemBinding, item ->
                itemBinding.tvTitle.text = "${item.namaLengkap} (${item.username})"
                itemBinding.tvSubtitle.text = roleLabel(item.role)
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

    private fun showForm(existing: UserEntity?) {
        val dialogBinding = DialogUserFormBinding.inflate(LayoutInflater.from(requireContext()))
        dialogBinding.tvDialogTitle.text = if (existing == null) "Tambah Pengguna" else "Ubah Pengguna"
        dialogBinding.etNamaLengkap.setText(existing?.namaLengkap ?: "")
        dialogBinding.etUsername.setText(existing?.username ?: "")
        dialogBinding.etPassword.setText(existing?.password ?: "")
        dialogBinding.spRole.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, roles.map { roleLabel(it) })
        if (existing != null) {
            val idx = roles.indexOf(existing.role)
            if (idx >= 0) dialogBinding.spRole.setSelection(idx)
        }

        AlertDialog.Builder(requireContext())
            .setView(dialogBinding.root)
            .setPositiveButton("Simpan") { _, _ ->
                val nama = dialogBinding.etNamaLengkap.text.toString().trim()
                val username = dialogBinding.etUsername.text.toString().trim()
                val password = dialogBinding.etPassword.text.toString().trim()
                if (nama.isEmpty() || username.isEmpty() || password.isEmpty()) {
                    toast("Semua field wajib diisi")
                    return@setPositiveButton
                }
                val role = roles[dialogBinding.spRole.selectedItemPosition]
                viewModel.save(existing?.id ?: 0L, username, password, nama, role)
                toast("Data berhasil disimpan")
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    private fun confirmDelete(item: UserEntity) {
        AlertDialog.Builder(requireContext())
            .setTitle("Konfirmasi Hapus")
            .setMessage("Hapus pengguna \"${item.namaLengkap}\"?")
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
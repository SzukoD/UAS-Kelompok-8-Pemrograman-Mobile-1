package com.utb.inventoryapp.ui.pic.sparepart

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.utb.inventoryapp.databinding.DialogSparepartFormBinding
import com.utb.inventoryapp.databinding.FragmentListGenericBinding
import com.utb.inventoryapp.ui.admin.barang.RefOption
import com.utb.inventoryapp.ui.common.GenericListAdapter
import com.utb.inventoryapp.util.GenericViewModelFactory
import com.utb.inventoryapp.util.inventoryApp
import com.utb.inventoryapp.util.statusLabel
import com.utb.inventoryapp.util.toFormattedDate
import com.utb.inventoryapp.util.toast

class PermintaanSparepartListFragment : Fragment() {

    private var _binding: FragmentListGenericBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: PermintaanSparepartViewModel
    private lateinit var adapter: GenericListAdapter<SparepartRow>
    private var barangOptions = listOf<RefOption>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentListGenericBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val app = requireContext().inventoryApp()
        val userId = app.sessionManager.getUserId()
        viewModel = ViewModelProvider(this, GenericViewModelFactory { PermintaanSparepartViewModel(app.repository, userId) })[PermintaanSparepartViewModel::class.java]

        binding.tvHeaderTitle.text = "Permintaan Sparepart"
        binding.swipeRefresh.isEnabled = false

        adapter = GenericListAdapter(
            areItemsSame = { a, b -> a.permintaan.id == b.permintaan.id },
            areContentsSame = { a, b -> a == b },
            onBind = { itemBinding, item ->
                itemBinding.tvTitle.text = "${item.barangNama} (x${item.permintaan.jumlah})"
                itemBinding.tvSubtitle.text = "Diajukan: ${item.permintaan.tanggal.toFormattedDate()}"
                itemBinding.tvStatus.visibility = View.VISIBLE
                itemBinding.tvStatus.text = statusLabel(item.permintaan.status)
                itemBinding.btnEdit.visibility = View.GONE
                itemBinding.btnDelete.visibility = View.GONE
            }
        )
        binding.recyclerView.adapter = adapter
        binding.fabAdd.setOnClickListener { showForm() }

        viewModel.barangOptions.observe(viewLifecycleOwner) { barangOptions = it }
        viewModel.items.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
            binding.emptyState.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
        }
    }

    private fun showForm() {
        if (barangOptions.isEmpty()) {
            toast("Belum ada data barang")
            return
        }
        val dialogBinding = DialogSparepartFormBinding.inflate(LayoutInflater.from(requireContext()))
        dialogBinding.spBarang.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, barangOptions)

        AlertDialog.Builder(requireContext())
            .setView(dialogBinding.root)
            .setPositiveButton("Ajukan") { _, _ ->
                val jumlahText = dialogBinding.etJumlah.text.toString().trim()
                if (jumlahText.isEmpty()) {
                    toast("Jumlah wajib diisi")
                    return@setPositiveButton
                }
                val barang = dialogBinding.spBarang.selectedItem as RefOption
                viewModel.createPermintaan(
                    barang.id, jumlahText.toIntOrNull() ?: 1,
                    dialogBinding.etKeterangan.text.toString().trim()
                ) { toast("Permintaan berhasil diajukan") }
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

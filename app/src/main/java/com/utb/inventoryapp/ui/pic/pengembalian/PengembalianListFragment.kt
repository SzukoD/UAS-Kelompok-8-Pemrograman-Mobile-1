package com.utb.inventoryapp.ui.pic.pengembalian

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.utb.inventoryapp.databinding.DialogPengembalianFormBinding
import com.utb.inventoryapp.databinding.FragmentListGenericBinding
import com.utb.inventoryapp.ui.common.GenericListAdapter
import com.utb.inventoryapp.util.GenericViewModelFactory
import com.utb.inventoryapp.util.inventoryApp
import com.utb.inventoryapp.util.kondisiLabel
import com.utb.inventoryapp.util.toFormattedDate
import com.utb.inventoryapp.util.toast

class PengembalianListFragment : Fragment() {

    private var _binding: FragmentListGenericBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: PengembalianViewModel
    private lateinit var adapter: GenericListAdapter<PengembalianRow>
    private var peminjamanOptions = listOf<PeminjamanOption>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentListGenericBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val app = requireContext().inventoryApp()
        val userId = app.sessionManager.getUserId()
        viewModel = ViewModelProvider(this, GenericViewModelFactory { PengembalianViewModel(app.repository, userId) })[PengembalianViewModel::class.java]

        binding.tvHeaderTitle.text = "Pengembalian Alat"
        binding.swipeRefresh.isEnabled = false

        adapter = GenericListAdapter(
            areItemsSame = { a, b -> a.pengembalian.id == b.pengembalian.id },
            areContentsSame = { a, b -> a == b },
            onBind = { itemBinding, item ->
                itemBinding.tvTitle.text = item.barangNama
                itemBinding.tvSubtitle.text = "Dikembalikan: ${item.pengembalian.tanggalKembali.toFormattedDate()} • ${kondisiLabel(item.pengembalian.kondisiKembali)}"
                itemBinding.tvStatus.visibility = View.GONE
                itemBinding.btnEdit.visibility = View.GONE
                itemBinding.btnDelete.visibility = View.GONE
            }
        )
        binding.recyclerView.adapter = adapter
        binding.fabAdd.setOnClickListener { showForm() }

        viewModel.peminjamanAktif.observe(viewLifecycleOwner) { peminjamanOptions = it }
        viewModel.riwayatPengembalian.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
            binding.emptyState.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
        }
    }

    private fun showForm() {
        if (peminjamanOptions.isEmpty()) {
            toast("Tidak ada peminjaman yang aktif untuk dikembalikan")
            return
        }
        val dialogBinding = DialogPengembalianFormBinding.inflate(LayoutInflater.from(requireContext()))
        dialogBinding.spPeminjaman.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, peminjamanOptions)
        dialogBinding.spKondisi.adapter = ArrayAdapter(
            requireContext(), android.R.layout.simple_spinner_dropdown_item,
            viewModel.kondisiOptions.map { kondisiLabel(it) }
        )

        AlertDialog.Builder(requireContext())
            .setView(dialogBinding.root)
            .setPositiveButton("Kembalikan") { _, _ ->
                val peminjaman = dialogBinding.spPeminjaman.selectedItem as PeminjamanOption
                val kondisi = viewModel.kondisiOptions[dialogBinding.spKondisi.selectedItemPosition]
                viewModel.kembalikan(
                    peminjaman.peminjaman.id, kondisi,
                    dialogBinding.etKeterangan.text.toString().trim()
                ) { toast("Barang berhasil dikembalikan") }
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

package com.utb.inventoryapp.ui.admin.barang

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.utb.inventoryapp.databinding.FragmentListGenericBinding
import com.utb.inventoryapp.data.local.entity.Barang
import com.utb.inventoryapp.ui.common.GenericListAdapter
import com.utb.inventoryapp.util.GenericViewModelFactory
import com.utb.inventoryapp.util.inventoryApp
import com.utb.inventoryapp.util.kondisiLabel
import com.utb.inventoryapp.util.toast

class BarangListFragment : Fragment() {

    private var _binding: FragmentListGenericBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: BarangViewModel
    private lateinit var adapter: GenericListAdapter<Barang>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentListGenericBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val repo = requireContext().inventoryApp().repository
        viewModel = ViewModelProvider(this, GenericViewModelFactory { BarangViewModel(repo) })[BarangViewModel::class.java]

        binding.tvHeaderTitle.text = "Data Barang"
        binding.swipeRefresh.isEnabled = false
        binding.etSearch.visibility = View.VISIBLE
        binding.etSearch.hint = "Cari kode atau nama barang"
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.setSearchQuery(s?.toString() ?: "")
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        adapter = GenericListAdapter(
            areItemsSame = { a, b -> a.id == b.id },
            areContentsSame = { a, b -> a == b },
            onBind = { itemBinding, item ->
                itemBinding.tvTitle.text = "${item.kode} • ${item.nama}"
                itemBinding.tvSubtitle.text = "Stok: ${item.stok} • Kondisi: ${kondisiLabel(item.kondisi)}"
                itemBinding.tvStatus.visibility = View.GONE
            },
            onEdit = { item -> openForm(item) },
            onDelete = { item -> confirmDelete(item) }
        )
        binding.recyclerView.adapter = adapter
        binding.fabAdd.setOnClickListener { openForm(null) }

        viewModel.barangList.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
            binding.emptyState.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
        }
    }

    private fun openForm(item: Barang?) {
        val bundle = Bundle().apply { putLong("barangId", item?.id ?: 0L) }
        findNavController().navigate(com.utb.inventoryapp.R.id.barangFormFragment, bundle)
    }

    private fun confirmDelete(item: Barang) {
        AlertDialog.Builder(requireContext())
            .setTitle("Konfirmasi Hapus")
            .setMessage("Hapus barang \"${item.nama}\"?")
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

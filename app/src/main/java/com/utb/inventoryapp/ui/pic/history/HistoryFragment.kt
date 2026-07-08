package com.utb.inventoryapp.ui.pic.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.utb.inventoryapp.data.local.entity.Riwayat
import com.utb.inventoryapp.databinding.FragmentReadOnlyListBinding
import com.utb.inventoryapp.ui.common.GenericListAdapter
import com.utb.inventoryapp.util.inventoryApp
import com.utb.inventoryapp.util.toFormattedDate

class HistoryFragment : Fragment() {

    private var _binding: FragmentReadOnlyListBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: GenericListAdapter<Riwayat>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentReadOnlyListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val app = requireContext().inventoryApp()
        val userId = app.sessionManager.getUserId()

        binding.tvHeaderTitle.text = "History Aktivitas"
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter = GenericListAdapter(
            areItemsSame = { a, b -> a.id == b.id },
            areContentsSame = { a, b -> a == b },
            onBind = { itemBinding, item ->
                itemBinding.tvTitle.text = item.aktivitas
                itemBinding.tvSubtitle.text = item.tanggal.toFormattedDate("dd MMM yyyy HH:mm")
                itemBinding.tvStatus.visibility = View.GONE
                itemBinding.btnEdit.visibility = View.GONE
                itemBinding.btnDelete.visibility = View.GONE
            }
        )
        binding.recyclerView.adapter = adapter

        app.repository.getRiwayatByUser(userId).asLiveData().observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
            binding.emptyState.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

package com.utb.inventoryapp.ui.admin.approval

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.utb.inventoryapp.databinding.FragmentReadOnlyListBinding
import com.utb.inventoryapp.util.GenericViewModelFactory
import com.utb.inventoryapp.util.inventoryApp
import com.utb.inventoryapp.util.toast

class ApprovalFragment : Fragment() {

    private var _binding: FragmentReadOnlyListBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ApprovalViewModel
    private lateinit var adapter: ApprovalAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentReadOnlyListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val repo = requireContext().inventoryApp().repository
        viewModel = ViewModelProvider(this, GenericViewModelFactory { ApprovalViewModel(repo) })[ApprovalViewModel::class.java]

        binding.tvHeaderTitle.text = "Approval"
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter = ApprovalAdapter(
            onApprove = { item ->
                viewModel.approve(item, true)
                toast("Disetujui")
            },
            onReject = { item ->
                viewModel.approve(item, false)
                toast("Ditolak")
            }
        )
        binding.recyclerView.adapter = adapter

        viewModel.approvalItems.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
            binding.emptyState.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

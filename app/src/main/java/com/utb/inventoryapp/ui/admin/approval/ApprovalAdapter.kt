package com.utb.inventoryapp.ui.admin.approval

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.utb.inventoryapp.databinding.ItemApprovalRowBinding
import com.utb.inventoryapp.util.toFormattedDate

class ApprovalAdapter(
    private val onApprove: (ApprovalItem) -> Unit,
    private val onReject: (ApprovalItem) -> Unit
) : ListAdapter<ApprovalItem, ApprovalAdapter.VH>(object : DiffUtil.ItemCallback<ApprovalItem>() {
    override fun areItemsTheSame(oldItem: ApprovalItem, newItem: ApprovalItem) = oldItem.id == newItem.id && oldItem.jenis == newItem.jenis
    override fun areContentsTheSame(oldItem: ApprovalItem, newItem: ApprovalItem) = oldItem == newItem
}) {

    class VH(val binding: ItemApprovalRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemApprovalRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = getItem(position)
        holder.binding.tvJenis.text = if (item.jenis == ApprovalJenis.PEMINJAMAN) "PEMINJAMAN ALAT" else "PERMINTAAN SPAREPART"
        holder.binding.tvTitle.text = "${item.barangNama} (x${item.jumlah})"
        holder.binding.tvSubtitle.text = "Diajukan oleh ${item.picNama} • ${item.tanggal.toFormattedDate()}"
        holder.binding.btnSetujui.setOnClickListener { onApprove(item) }
        holder.binding.btnTolak.setOnClickListener { onReject(item) }
    }
}

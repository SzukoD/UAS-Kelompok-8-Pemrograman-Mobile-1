package com.utb.inventoryapp.ui.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.utb.inventoryapp.databinding.ItemMasterRowBinding

/**
 * Generic RecyclerView adapter used across all master-data & transaction lists in the app.
 * Callers supply how to bind each row and what happens on edit/delete/click.
 */
class GenericListAdapter<T : Any>(
    private val areItemsSame: (T, T) -> Boolean,
    private val areContentsSame: (T, T) -> Boolean,
    private val onBind: (ItemMasterRowBinding, T) -> Unit,
    private val onClick: ((T) -> Unit)? = null,
    private val onEdit: ((T) -> Unit)? = null,
    private val onDelete: ((T) -> Unit)? = null
) : ListAdapter<T, GenericListAdapter.VH>(object : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T) = areItemsSame(oldItem, newItem)
    override fun areContentsTheSame(oldItem: T, newItem: T) = areContentsSame(oldItem, newItem)
}) {

    class VH(val binding: ItemMasterRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemMasterRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = getItem(position)
        onBind(holder.binding, item)

        holder.binding.root.setOnClickListener { onClick?.invoke(item) }

        if (onEdit != null) {
            holder.binding.btnEdit.visibility = android.view.View.VISIBLE
            holder.binding.btnEdit.setOnClickListener { onEdit.invoke(item) }
        } else {
            holder.binding.btnEdit.visibility = android.view.View.GONE
        }

        if (onDelete != null) {
            holder.binding.btnDelete.visibility = android.view.View.VISIBLE
            holder.binding.btnDelete.setOnClickListener { onDelete.invoke(item) }
        } else {
            holder.binding.btnDelete.visibility = android.view.View.GONE
        }
    }
}

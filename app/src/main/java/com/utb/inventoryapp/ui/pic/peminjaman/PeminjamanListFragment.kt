package com.utb.inventoryapp.ui.pic.peminjaman

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.utb.inventoryapp.databinding.DialogPeminjamanFormBinding
import com.utb.inventoryapp.databinding.FragmentListGenericBinding
import com.utb.inventoryapp.ui.admin.barang.RefOption
import com.utb.inventoryapp.ui.common.GenericListAdapter
import com.utb.inventoryapp.util.GenericViewModelFactory
import com.utb.inventoryapp.util.inventoryApp
import com.utb.inventoryapp.util.statusLabel
import com.utb.inventoryapp.util.toFormattedDate
import com.utb.inventoryapp.util.toast
import java.util.Calendar

class PeminjamanListFragment : Fragment() {

    private var _binding: FragmentListGenericBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: PeminjamanViewModel
    private lateinit var adapter: GenericListAdapter<PeminjamanRow>
    private var barangOptions = listOf<RefOption>()
    private var tanggalRencanaKembali: Long = 0L

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentListGenericBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val app = requireContext().inventoryApp()
        val userId = app.sessionManager.getUserId()
        viewModel = ViewModelProvider(this, GenericViewModelFactory { PeminjamanViewModel(app.repository, userId) })[PeminjamanViewModel::class.java]

        binding.tvHeaderTitle.text = "Peminjaman Alat"
        binding.swipeRefresh.isEnabled = false

        adapter = GenericListAdapter(
            areItemsSame = { a, b -> a.peminjaman.id == b.peminjaman.id },
            areContentsSame = { a, b -> a == b },
            onBind = { itemBinding, item ->
                itemBinding.tvTitle.text = "${item.barangNama} (x${item.peminjaman.jumlah})"
                itemBinding.tvSubtitle.text = "Pinjam: ${item.peminjaman.tanggalPinjam.toFormattedDate()} • Kembali: ${item.peminjaman.tanggalRencanaKembali.toFormattedDate()}"
                itemBinding.tvStatus.visibility = View.VISIBLE
                itemBinding.tvStatus.text = statusLabel(item.peminjaman.status)
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
            toast("Tidak ada barang dengan stok tersedia")
            return
        }
        tanggalRencanaKembali = 0L
        val dialogBinding = DialogPeminjamanFormBinding.inflate(LayoutInflater.from(requireContext()))
        dialogBinding.spBarang.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, barangOptions)

        dialogBinding.btnPilihTanggal.setOnClickListener {
            val cal = Calendar.getInstance()
            DatePickerDialog(requireContext(), { _, year, month, day ->
                cal.set(year, month, day)
                tanggalRencanaKembali = cal.timeInMillis
                dialogBinding.btnPilihTanggal.text = tanggalRencanaKembali.toFormattedDate()
            }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        AlertDialog.Builder(requireContext())
            .setView(dialogBinding.root)
            .setPositiveButton("Ajukan") { _, _ ->
                val jumlahText = dialogBinding.etJumlah.text.toString().trim()
                if (jumlahText.isEmpty() || tanggalRencanaKembali == 0L) {
                    toast("Jumlah dan tanggal rencana kembali wajib diisi")
                    return@setPositiveButton
                }

                // Pastikan jumlahInput didefinisikan agar tidak error merah
                val jumlahInput = jumlahText.toIntOrNull() ?: 1
                if (jumlahInput <= 0) {
                    toast("Jumlah pinjam harus lebih besar dari 0")
                    return@setPositiveButton
                }

                // =======================================================
                // KODE YANG KAMU MAKSUD DIMASUKKAN DI SINI:
                // =======================================================
                val barang = dialogBinding.spBarang.selectedItem as RefOption

                // Panggil fungsi ViewModel dengan lambda onResult yang baru
                viewModel.createPeminjaman(
                    barang.id,
                    jumlahInput,
                    tanggalRencanaKembali,
                    dialogBinding.etKeterangan.text.toString().trim()
                ) { success, message ->
                    // Menampilkan pesan toast dinamis (Bisa pesan sukses ataupun pesan error stok kurang)
                    toast(message)
                }
                // =======================================================
            }
            .setNegativeButton("Batal", null)
            .show()
    }
}

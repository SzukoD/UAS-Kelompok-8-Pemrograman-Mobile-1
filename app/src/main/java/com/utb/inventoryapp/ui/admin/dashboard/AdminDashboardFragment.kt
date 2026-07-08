package com.utb.inventoryapp.ui.admin.dashboard

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.utb.inventoryapp.R
import com.utb.inventoryapp.databinding.FragmentAdminDashboardBinding
import com.utb.inventoryapp.util.inventoryApp
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class AdminDashboardFragment : Fragment() {

    private var _binding: FragmentAdminDashboardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAdminDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val app = requireContext().inventoryApp()

        binding.tvNamaLengkap.text = app.sessionManager.getNamaLengkap()
        binding.btnSettings.setOnClickListener { findNavController().navigate(R.id.pengaturanFragment) }

        // PERBAIKAN: Mengubah tvStatLabel menjadi tvStatTitle sesuai item_stat_card.xml
        binding.statBarang.tvStatTitle.text = "Total Barang"
        binding.statSupplier.tvStatTitle.text = "Total Supplier"
        binding.statPending.tvStatTitle.text = "Menunggu Approval"
        binding.statUser.tvStatTitle.text = "Total Pengguna"

        viewLifecycleOwner.lifecycleScope.launch {
            binding.statBarang.tvStatValue.text = app.repository.countBarang().toString()
            binding.statSupplier.tvStatValue.text = app.repository.countSupplier().toString()
            binding.statUser.tvStatValue.text = app.repository.countUser().toString()

            // Setup grafik mini (sparkline) untuk baris pertama data real
            setupMiniChart(binding.statBarang.miniLineChart, arrayListOf(Entry(0f, 10f), Entry(1f, 15f), Entry(2f, 12f), Entry(3f, 18f)), "#4CAF50") // Hijau
            setupMiniChart(binding.statSupplier.miniLineChart, arrayListOf(Entry(0f, 5f), Entry(1f, 8f), Entry(2f, 6f), Entry(3f, 9f)), "#2196F3") // Biru
        }

        viewLifecycleOwner.lifecycleScope.launch {
            val pendingPeminjaman = app.repository.getPeminjamanByStatus("PENDING").first().size
            val pendingSparepart = app.repository.getPermintaanSparepartByStatus("PENDING").first().size
            val totalPending = pendingPeminjaman + pendingSparepart
            binding.statPending.tvStatValue.text = totalPending.toString()

            // Setup grafik mini (sparkline) untuk baris kedua data real
            setupMiniChart(binding.statPending.miniLineChart, arrayListOf(Entry(0f, 12f), Entry(1f, 8f), Entry(2f, 15f), Entry(3f, totalPending.toFloat())), "#FFC107") // Kuning
            setupMiniChart(binding.statUser.miniLineChart, arrayListOf(Entry(0f, 4f), Entry(1f, 7f), Entry(2f, 5f), Entry(3f, 10f)), "#9C27B0") // Ungu
        }
    }

    private fun setupMiniChart(chart: LineChart, dataValues: ArrayList<Entry>, colorHex: String) {
        val dataSet = LineDataSet(dataValues, "").apply {
            color = Color.parseColor(colorHex)
            lineWidth = 2f
            setDrawCircles(false)
            setDrawValues(false)
            mode = LineDataSet.Mode.CUBIC_BEZIER
            setDrawFilled(true)
            fillColor = Color.parseColor(colorHex)
            fillAlpha = 15
        }

        chart.apply {
            data = LineData(dataSet)
            description.isEnabled = false
            legend.isEnabled = false
            xAxis.isEnabled = false
            axisLeft.isEnabled = false
            axisRight.isEnabled = false
            setTouchEnabled(false)
            invalidate()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
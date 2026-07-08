package com.utb.inventoryapp.ui.gudang.dashboard

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
import com.utb.inventoryapp.databinding.FragmentGudangDashboardBinding
import com.utb.inventoryapp.util.inventoryApp
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class GudangDashboardFragment : Fragment() {

    private var _binding: FragmentGudangDashboardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentGudangDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val app = requireContext().inventoryApp()

        binding.tvNamaLengkap.text = app.sessionManager.getNamaLengkap()
        binding.btnSettings.setOnClickListener { findNavController().navigate(R.id.pengaturanFragment) }

        // PERBAIKAN: Mengubah tvStatLabel menjadi tvStatTitle sesuai XML
        binding.statBarang.tvStatTitle.text = "Total Barang"
        binding.statRusak.tvStatTitle.text = "Barang Rusak"
        binding.statHilang.tvStatTitle.text = "Barang Hilang"
        binding.statPending.tvStatTitle.text = "Menunggu Approval"

        viewLifecycleOwner.lifecycleScope.launch {
            val totalBarang = app.repository.countBarang()
            val barangRusak = app.repository.countBarangRusak().first()
            val barangHilang = app.repository.countBarangHilang().first()

            binding.statBarang.tvStatValue.text = totalBarang.toString()
            binding.statRusak.tvStatValue.text = barangRusak.toString()
            binding.statHilang.tvStatValue.text = barangHilang.toString()

            val pendingPeminjaman = app.repository.getPeminjamanByStatus("PENDING").first().size
            val pendingSparepart = app.repository.getPermintaanSparepartByStatus("PENDING").first().size
            binding.statPending.tvStatValue.text = (pendingPeminjaman + pendingSparepart).toString()

            // Setup grafik mini (sparkline)
            setupMiniChart(binding.statBarang.miniLineChart, arrayListOf(Entry(0f, 10f), Entry(1f, 15f), Entry(2f, 13f), Entry(3f, totalBarang.toFloat())), "#4CAF50") // Hijau
            setupMiniChart(binding.statRusak.miniLineChart, arrayListOf(Entry(0f, 2f), Entry(1f, 1f), Entry(2f, 4f), Entry(3f, barangRusak.toFloat())), "#F44336") // Merah
            setupMiniChart(binding.statHilang.miniLineChart, arrayListOf(Entry(0f, 0f), Entry(1f, 2f), Entry(2f, 1f), Entry(3f, barangHilang.toFloat())), "#FF9800") // Orange
            setupMiniChart(binding.statPending.miniLineChart, arrayListOf(Entry(0f, 5f), Entry(1f, 8f), Entry(2f, 4f), Entry(3f, (pendingPeminjaman + pendingSparepart).toFloat())), "#FFC107") // Kuning
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
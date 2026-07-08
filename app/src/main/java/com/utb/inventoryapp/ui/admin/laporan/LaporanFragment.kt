package com.utb.inventoryapp.ui.admin.laporan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.utb.inventoryapp.R
import com.utb.inventoryapp.databinding.FragmentLaporanMenuBinding

class LaporanFragment : Fragment() {

    private var _binding: FragmentLaporanMenuBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentLaporanMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.cardLaporanBarang.tvMenuTitle.text = LaporanType.BARANG.title
        binding.cardLaporanBarang.ivMenuIcon.setImageResource(R.drawable.ic_box)
        binding.cardLaporanBarang.root.setOnClickListener { openDetail(LaporanType.BARANG) }

        binding.cardLaporanPeminjaman.tvMenuTitle.text = LaporanType.PEMINJAMAN.title
        binding.cardLaporanPeminjaman.ivMenuIcon.setImageResource(R.drawable.ic_report)
        binding.cardLaporanPeminjaman.root.setOnClickListener { openDetail(LaporanType.PEMINJAMAN) }

        binding.cardLaporanPengembalian.tvMenuTitle.text = LaporanType.PENGEMBALIAN.title
        binding.cardLaporanPengembalian.ivMenuIcon.setImageResource(R.drawable.ic_return)
        binding.cardLaporanPengembalian.root.setOnClickListener { openDetail(LaporanType.PENGEMBALIAN) }

        binding.cardLaporanAktivitas.tvMenuTitle.text = LaporanType.AKTIVITAS.title
        binding.cardLaporanAktivitas.ivMenuIcon.setImageResource(R.drawable.ic_history)
        binding.cardLaporanAktivitas.root.setOnClickListener { openDetail(LaporanType.AKTIVITAS) }
    }

    private fun openDetail(type: LaporanType) {
        findNavController().navigate(R.id.laporanDetailFragment, bundleOf("laporanType" to type.name))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

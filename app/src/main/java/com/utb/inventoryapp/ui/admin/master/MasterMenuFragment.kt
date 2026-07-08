package com.utb.inventoryapp.ui.admin.master

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.utb.inventoryapp.R
import com.utb.inventoryapp.databinding.FragmentMasterMenuBinding
import com.utb.inventoryapp.ui.common.MasterType

class MasterMenuFragment : Fragment() {

    private var _binding: FragmentMasterMenuBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMasterMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.cardKategori.tvMenuTitle.text = "Kategori"
        binding.cardKategori.ivMenuIcon.setImageResource(R.drawable.ic_category)
        binding.cardKategori.root.setOnClickListener { openMaster(MasterType.KATEGORI) }

        binding.cardSatuan.tvMenuTitle.text = "Satuan"
        binding.cardSatuan.ivMenuIcon.setImageResource(R.drawable.ic_box)
        binding.cardSatuan.root.setOnClickListener { openMaster(MasterType.SATUAN) }

        binding.cardJenisBarang.tvMenuTitle.text = "Jenis Barang"
        binding.cardJenisBarang.ivMenuIcon.setImageResource(R.drawable.ic_box)
        binding.cardJenisBarang.root.setOnClickListener { openMaster(MasterType.JENIS_BARANG) }

        binding.cardLokasi.tvMenuTitle.text = "Lokasi"
        binding.cardLokasi.ivMenuIcon.setImageResource(R.drawable.ic_location)
        binding.cardLokasi.root.setOnClickListener { openMaster(MasterType.LOKASI) }

        binding.cardSupplier.tvMenuTitle.text = "Supplier"
        binding.cardSupplier.ivMenuIcon.setImageResource(R.drawable.ic_supplier)
        binding.cardSupplier.root.setOnClickListener {
            findNavController().navigate(R.id.supplierListFragment)
        }

        binding.cardPengguna.tvMenuTitle.text = "Pengguna"
        binding.cardPengguna.ivMenuIcon.setImageResource(R.drawable.ic_user)
        binding.cardPengguna.root.setOnClickListener {
            findNavController().navigate(R.id.userListFragment)
        }
    }

    private fun openMaster(type: MasterType) {
        findNavController().navigate(R.id.simpleMasterFragment, bundleOf("arg_type" to type.name))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

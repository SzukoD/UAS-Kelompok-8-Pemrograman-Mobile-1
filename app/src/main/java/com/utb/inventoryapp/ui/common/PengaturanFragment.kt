package com.utb.inventoryapp.ui.common

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.utb.inventoryapp.databinding.FragmentPengaturanBinding
import com.utb.inventoryapp.ui.login.LoginActivity
import com.utb.inventoryapp.util.inventoryApp
import com.utb.inventoryapp.util.roleLabel

class PengaturanFragment : Fragment() {

    private var _binding: FragmentPengaturanBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentPengaturanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val session = requireContext().inventoryApp().sessionManager

        binding.tvNamaLengkap.text = session.getNamaLengkap()
        binding.tvUsername.text = session.getUsername()
        binding.tvRole.text = roleLabel(session.getRole())

        binding.btnLogout.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Konfirmasi Keluar")
                .setMessage("Apakah Anda yakin ingin keluar?")
                .setPositiveButton("Ya") { _, _ ->
                    session.clearSession()
                    val intent = Intent(requireContext(), LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    requireActivity().finish()
                }
                .setNegativeButton("Batal", null)
                .show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

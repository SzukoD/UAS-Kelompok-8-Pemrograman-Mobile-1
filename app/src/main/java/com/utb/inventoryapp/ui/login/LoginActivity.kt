package com.utb.inventoryapp.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.utb.inventoryapp.databinding.ActivityLoginBinding
import com.utb.inventoryapp.ui.main.MainActivity
import com.utb.inventoryapp.util.GenericViewModelFactory
import com.utb.inventoryapp.util.inventoryApp

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val app = inventoryApp()

        if (app.sessionManager.isLoggedIn()) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            return
        }

        viewModel = ViewModelProvider(this, GenericViewModelFactory { LoginViewModel(app.repository) })[LoginViewModel::class.java]

        binding.btnLogin.setOnClickListener {
            binding.tvError.visibility = View.GONE
            viewModel.login(
                binding.etUsername.text.toString(),
                binding.etPassword.text.toString()
            )
        }

        viewModel.loginResult.observe(this) { result ->
            when (result) {
                is LoginResult.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.btnLogin.isEnabled = false
                }
                is LoginResult.Success -> {
                    binding.progressBar.visibility = View.GONE
                    app.sessionManager.saveSession(
                        result.user.id, result.user.username, result.user.namaLengkap, result.user.role
                    )
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
                is LoginResult.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.btnLogin.isEnabled = true
                    binding.tvError.visibility = View.VISIBLE
                    binding.tvError.text = result.message
                }
            }
        }
    }
}

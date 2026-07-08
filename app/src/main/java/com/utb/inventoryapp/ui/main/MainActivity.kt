package com.utb.inventoryapp.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavGraph
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.utb.inventoryapp.R
import com.utb.inventoryapp.data.local.entity.Role
import com.utb.inventoryapp.databinding.ActivityMainBinding
import com.utb.inventoryapp.ui.login.LoginActivity
import com.utb.inventoryapp.util.inventoryApp

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val session = inventoryApp().sessionManager
        if (!session.isLoggedIn()) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navController = navHostFragment.navController
        val navInflater = navController.navInflater

        val graphRes: Int
        val menuRes: Int
        when (session.getRole()) {
            Role.ADMIN -> {
                graphRes = R.navigation.nav_admin
                menuRes = R.menu.menu_admin
            }
            Role.PIC -> {
                graphRes = R.navigation.nav_pic
                menuRes = R.menu.menu_pic
            }
            Role.KEPALA_GUDANG -> {
                graphRes = R.navigation.nav_gudang
                menuRes = R.menu.menu_gudang
            }
            else -> {
                graphRes = R.navigation.nav_pic
                menuRes = R.menu.menu_pic
            }
        }

        val graph: NavGraph = navInflater.inflate(graphRes)
        navController.graph = graph

        binding.bottomNav.inflateMenu(menuRes)
        binding.bottomNav.setupWithNavController(navController)
    }
}

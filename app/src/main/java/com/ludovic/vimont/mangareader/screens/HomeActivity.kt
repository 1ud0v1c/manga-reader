package com.ludovic.vimont.mangareader.screens

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.ludovic.vimont.mangareader.R
import com.ludovic.vimont.mangareader.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpNavigation()
    }

    private fun setUpNavigation() {
        with(binding) {
            val navHostFragment: NavHostFragment = supportFragmentManager.findFragmentById(R.id.activityMainProductsListHostFragment) as NavHostFragment
            val navController = navHostFragment.navController
            setupActionBarWithNavController(navController)
            bottomNavigationView.setupWithNavController(navController)
        }
    }
}
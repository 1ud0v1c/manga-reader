package com.ludovic.vimont.mangareader.screens

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.ludovic.vimont.mangareader.R
import com.ludovic.vimont.mangareader.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpNavigation()
    }

    private fun setUpNavigation() {
        with(binding) {
            val navHostFragment: NavHostFragment = supportFragmentManager.findFragmentById(R.id.activityMainProductsListHostFragment) as NavHostFragment
            navController = navHostFragment.navController
            val appBarConfiguration = AppBarConfiguration(setOf(R.id.listFragment, R.id.favoriteFragment))

            setupActionBarWithNavController(navController, appBarConfiguration)
            bottomNavigationView.setupWithNavController(navController)

            navController.addOnDestinationChangedListener { _, destination, _ ->
                when (destination.id) {
                    R.id.listFragment -> {
                        showBottomNavigationBar()
                    }
                    R.id.favoriteFragment -> {
                        showBottomNavigationBar()
                    }
                    else -> {
                        hideBottomNavigationBar()
                    }
                }
            }
        }
    }

    private fun showBottomNavigationBar() {
        with(binding) {
            bottomNavigationView.visibility = View.VISIBLE
        }
    }

    private fun hideBottomNavigationBar() {
        with(binding) {
            bottomNavigationView.visibility = View.GONE
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
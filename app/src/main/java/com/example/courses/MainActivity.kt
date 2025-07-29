package com.example.courses

import android.content.res.ColorStateList
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.courses.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var navController: NavController

    private val authDestinations = setOf(
        R.id.loginFragment,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNavigation()
    }

    private fun setupNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        navController = navHostFragment.navController

        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.bottomNav.isVisible = destination.id !in authDestinations
        }

        val states = arrayOf(
            intArrayOf(android.R.attr.state_checked),    // selected
            intArrayOf(-android.R.attr.state_checked)    // unselected
        )

        val colors = intArrayOf(
            getColor(R.color.green),      // selected icon/text color
            getColor(R.color.textWhite)   // unselected icon/text color
        )

        val colorStateList = ColorStateList(states, colors)

        binding.bottomNav.apply {
            itemIconTintList = colorStateList
            itemTextColor = colorStateList
            itemActiveIndicatorColor =
                ColorStateList.valueOf(getColor(R.color.bottomNavBarBackground))
        }

       binding.bottomNav.setupWithNavController(navController)
    }
}
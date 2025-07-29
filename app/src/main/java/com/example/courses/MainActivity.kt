package com.example.courses

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import com.example.courses.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import android.widget.LinearLayout
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.ImageView

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val authDestinations = setOf(
        R.id.loginFragment,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        val navController = navHostFragment.navController

        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.customBottomNav.isVisible = destination.id !in authDestinations
            updateBottomNavSelection(destination.id)
        }

        binding.navHome.setOnClickListener {
            val options = NavOptions.Builder()
                .setPopUpTo(R.id.homeFragment, inclusive = false, saveState = true)
                .setLaunchSingleTop(true)
                .setRestoreState(true)
                .build()
            navController.navigate(R.id.homeFragment, null, options)
        }

        binding.navFavorite.setOnClickListener {
            val options = NavOptions.Builder()
                .setPopUpTo(R.id.homeFragment, inclusive = false, saveState = true)
                .setLaunchSingleTop(true)
                .setRestoreState(true)
                .build()
            navController.navigate(R.id.favoritesFragment, null, options)
        }

        binding.navProfile.setOnClickListener {
            val options = NavOptions.Builder()
                .setPopUpTo(R.id.homeFragment, inclusive = false, saveState = true)
                .setLaunchSingleTop(true)
                .setRestoreState(true)
                .build()
            navController.navigate(R.id.profileFragment, null, options)
        }
    }

    private fun updateBottomNavSelection(destinationId: Int) {
        resetAllNavItems()

        when (destinationId) {
            R.id.homeFragment -> {
                setNavItemSelected(binding.navHome)
            }

            R.id.favoritesFragment -> {
                setNavItemSelected(binding.navFavorite)
            }

            R.id.profileFragment -> {
                setNavItemSelected(binding.navProfile)
            }
        }
    }

    private fun resetAllNavItems() {
        resetNavItem(binding.navHome)
        resetNavItem(binding.navFavorite)
        resetNavItem(binding.navProfile)
    }

    private fun resetNavItem(navItem: LinearLayout) {
        val frameLayout = navItem.getChildAt(0) as FrameLayout
        val textView = navItem.getChildAt(1) as TextView
        val imageView = frameLayout.getChildAt(0) as ImageView

        frameLayout.background = null

        imageView.setColorFilter(ContextCompat.getColor(this, R.color.textWhite))
        textView.setTextColor(ContextCompat.getColor(this, R.color.textWhite))
    }

    private fun setNavItemSelected(navItem: LinearLayout) {
        val frameLayout = navItem.getChildAt(0) as FrameLayout
        val textView = navItem.getChildAt(1) as TextView
        val imageView = frameLayout.getChildAt(0) as ImageView

        frameLayout.background = ContextCompat.getDrawable(this, R.drawable.nav_selected_bg)

        // Set green colors
        imageView.setColorFilter(ContextCompat.getColor(this, R.color.green))
        textView.setTextColor(ContextCompat.getColor(this, R.color.green))
    }
}
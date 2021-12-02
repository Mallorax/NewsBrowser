package com.example.newsbrowser.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.newsbrowser.R
import com.example.newsbrowser.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var bindig: ActivityMainBinding
    private val navController by lazy {
        Navigation.findNavController(this, R.id.nav_host_fragment)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindig = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setUpNavigation()
    }

    private fun setUpNavigation() {
        val navView = bindig.navView
        navView.setupWithNavController(navController)
        val appBarConfig = AppBarConfiguration.Builder(
            R.id.articles_browser_fragment,
            R.id.breaking_news_fragment
        )
            .setOpenableLayout(bindig.drawerLayout).build()
        setSupportActionBar(bindig.appToolbar)
        setupActionBarWithNavController(navController, appBarConfig)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navigateUp(navController, bindig.drawerLayout)
    }
}
package com.example.interntaskapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.interntaskapp.fragments.HomeFragment
import com.example.interntaskapp.fragments.ProfileFragment
import com.example.interntaskapp.fragments.SettingsFragment

class DashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)
        
        // Load default fragment
        loadFragment(HomeFragment())

        bottomNav.setOnItemSelectedListener {
            Log.d("Dashboard", "Bottom nav item clicked: ${it.itemId}")
            when (it.itemId) {
                R.id.nav_home -> {
                    Log.d("Dashboard", "Home tab selected")
                    loadFragment(HomeFragment())
                }
                R.id.nav_notifications -> {
                    Log.d("Dashboard", "Profile tab selected")
                    loadFragment(ProfileFragment())
                }
                R.id.nav_settings -> {
                    Log.d("Dashboard", "Settings tab selected")
                    loadFragment(SettingsFragment())
                }
            }
            true
        }
    }
    
    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}

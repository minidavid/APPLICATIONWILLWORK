package com.example.applicationwillwork

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.applicationwillwork.databinding.ActivityHomeBinding // Import your generated binding class

class Home : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater) // Initialize binding
        setContentView(binding.root)
        replaceFragment(ForumsFragment())

        binding.bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.forums -> replaceFragment(ForumsFragment())
                R.id.home -> replaceFragment(HomeFragment())
                R.id.articles -> replaceFragment(InformationFragment())
                else -> {
                    // Handle other menu items if needed
                }
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_layout, fragment)
            .commit()
    }
}

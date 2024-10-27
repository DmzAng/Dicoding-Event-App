package com.dimaz.dicodingeventapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.dimaz.dicodingeventapp.R
import com.dimaz.dicodingeventapp.databinding.ActivityMainBinding
import com.dimaz.dicodingeventapp.ui.setting.SettingViewModel
import com.dimaz.mydatastore.SettingPreferences
import com.dimaz.mydatastore.ViewModelFactory
import com.dimaz.mydatastore.dataStore

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private lateinit var settingViewModel: SettingViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = SettingPreferences.getInstance(dataStore)
        settingViewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(SettingViewModel::class.java)

        settingViewModel.getThemeSettings().observe(this) { isDarkModeActive ->
            AppCompatDelegate.setDefaultNightMode(
                if (isDarkModeActive) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
            )
        }

        viewPagerAdapter = ViewPagerAdapter(this)
        binding.viewPager.adapter = viewPagerAdapter

        binding.navView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    binding.viewPager.currentItem = 0
                    true
                }
                R.id.navigation_dashboard -> {
                    binding.viewPager.currentItem = 1
                    true
                }
                R.id.navtigation_favorite -> {
                    binding.viewPager.currentItem = 2
                    true
                }
                R.id.navtigation_setting -> {
                    binding.viewPager.currentItem = 3
                    true
                }
                else -> false
            }
        }

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> binding.navView.selectedItemId = R.id.navigation_home
                    1 -> binding.navView.selectedItemId = R.id.navigation_dashboard
                    2 -> binding.navView.selectedItemId = R.id.navtigation_favorite
                    3 -> binding.navView.selectedItemId = R.id.navtigation_setting
                }
            }
        })
    }
}

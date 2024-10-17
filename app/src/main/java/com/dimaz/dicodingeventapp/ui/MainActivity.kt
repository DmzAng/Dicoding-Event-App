package com.dimaz.dicodingeventapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.dimaz.dicodingeventapp.R
import com.dimaz.dicodingeventapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewPagerAdapter: ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
                else -> false
            }
        }

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> binding.navView.selectedItemId = R.id.navigation_home
                    1 -> binding.navView.selectedItemId = R.id.navigation_dashboard
                }
            }
        })
    }
}

package com.dimaz.dicodingeventapp.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dimaz.dicodingeventapp.ui.home.HomeFragment
import com.dimaz.dicodingeventapp.ui.dashboard.EventClearFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HomeFragment()
            1 -> EventClearFragment()
            else -> HomeFragment()
        }
    }
}

package com.dimaz.dicodingeventapp.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dimaz.dicodingeventapp.ui.home.HomeFragment
import com.dimaz.dicodingeventapp.ui.dashboard.EventClearFragment
import com.dimaz.dicodingeventapp.ui.favorite.FavoriteFragment
import com.dimaz.dicodingeventapp.ui.setting.SettingFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HomeFragment()
            1 -> EventClearFragment()
            2 -> FavoriteFragment()
            3 -> SettingFragment()
            else -> HomeFragment()
        }
    }
}

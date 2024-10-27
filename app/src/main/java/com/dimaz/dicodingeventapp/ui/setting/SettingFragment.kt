package com.dimaz.dicodingeventapp.ui.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.dimaz.dicodingeventapp.R
import com.dimaz.dicodingeventapp.databinding.FragmentSettingBinding
import com.dimaz.dicodingeventapp.databinding.EventCardItemBinding
import com.dimaz.mydatastore.SettingPreferences
import com.dimaz.mydatastore.ViewModelFactory
import com.dimaz.mydatastore.dataStore

class SettingFragment : Fragment() {

    private lateinit var settingViewModel: SettingViewModel
    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        val view = binding.root

        val pref = SettingPreferences.getInstance(requireContext().dataStore)
        settingViewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(SettingViewModel::class.java)

        settingViewModel.getThemeSettings().observe(viewLifecycleOwner) { isDarkModeActive ->
            AppCompatDelegate.setDefaultNightMode(
                if (isDarkModeActive) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
            )
            binding.switchTheme.isChecked = isDarkModeActive

            val eventCardBinding = EventCardItemBinding.inflate(inflater)
            eventCardBinding.cardEvent.setCardBackgroundColor(
                if (isDarkModeActive) {
                    ContextCompat.getColor(requireContext(), R.color.gray_dark)
                } else {
                    ContextCompat.getColor(requireContext(), R.color.white)
                }
            )
        }

        binding.switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            settingViewModel.saveThemeSettings(isChecked)
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

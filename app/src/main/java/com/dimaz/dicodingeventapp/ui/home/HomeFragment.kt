package com.dimaz.dicodingeventapp.ui.home

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dimaz.dicodingeventapp.R
import com.dimaz.dicodingeventapp.databinding.FragmentHomeBinding
import com.dimaz.dicodingeventapp.ui.EventAdapter
import com.dimaz.dicodingeventapp.ui.setting.SettingViewModel
import com.dimaz.mydatastore.SettingPreferences
import com.dimaz.mydatastore.ViewModelFactory
import com.dimaz.mydatastore.dataStore

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var eventAdapter: EventAdapter
    private val homeViewModel: HomeViewModel by activityViewModels()

    private lateinit var settingViewModel: SettingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        val pref = SettingPreferences.getInstance(requireContext().dataStore)
        settingViewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(SettingViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val nightModeFlags = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        val isDarkModeActive = nightModeFlags == Configuration.UI_MODE_NIGHT_YES

        binding.appTag.setCardBackgroundColor(
            if (isDarkModeActive) {
                ContextCompat.getColor(requireContext(), R.color.black)
            } else {
                ContextCompat.getColor(requireContext(), R.color.white)
            }
        )

        binding.rvEvent.layoutManager = LinearLayoutManager(context)
        eventAdapter = EventAdapter(isDarkModeActive)
        binding.rvEvent.adapter = eventAdapter

        setupObserver()
    }

    private fun setupObserver() {
        homeViewModel.eventList.observe(viewLifecycleOwner) { eventList ->
            eventAdapter.submitList(eventList)
        }

        homeViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

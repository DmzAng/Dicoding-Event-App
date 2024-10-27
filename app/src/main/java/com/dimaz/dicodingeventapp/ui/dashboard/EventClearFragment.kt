package com.dimaz.dicodingeventapp.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dimaz.dicodingeventapp.databinding.FragmentDashboardBinding
import com.dimaz.dicodingeventapp.ui.EventAdapter
import com.dimaz.dicodingeventapp.ui.setting.SettingViewModel
import com.dimaz.mydatastore.SettingPreferences
import com.dimaz.mydatastore.ViewModelFactory
import com.dimaz.mydatastore.dataStore

class EventClearFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private lateinit var eventAdapter: EventAdapter
    private val evenClearViewModel: EvenClearViewModel by activityViewModels()
    private lateinit var settingViewModel: SettingViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)

        val pref = SettingPreferences.getInstance(requireContext().dataStore)
        settingViewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(SettingViewModel::class.java)

        settingViewModel.getThemeSettings().observe(viewLifecycleOwner) { isDarkModeActive ->
            eventAdapter = EventAdapter(isDarkModeActive)
            binding.rvEventClear.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = eventAdapter
            }
            setupObserver()
        }
        return binding.root
    }

    private fun setupObserver() {
        evenClearViewModel.eventList.observe(viewLifecycleOwner) { eventList ->
            eventAdapter.submitList(eventList)
        }

        evenClearViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

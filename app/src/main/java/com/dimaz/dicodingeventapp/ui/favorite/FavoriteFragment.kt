package com.dimaz.dicodingeventapp.ui.favorite

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dimaz.dicodingeventapp.R
import com.dimaz.dicodingeventapp.data.local.room.EventDatabase
import com.dimaz.dicodingeventapp.databinding.FragmentFavoriteBinding
import com.dimaz.dicodingeventapp.ui.EventAdapter
import com.dimaz.dicodingeventapp.ui.setting.SettingViewModel
import com.dimaz.mydatastore.SettingPreferences
import com.dimaz.mydatastore.ViewModelFactory
import com.dimaz.mydatastore.dataStore

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    private lateinit var eventAdapter: EventAdapter
    private val favoriteViewModel: FavoriteViewModel by viewModels {
        FavoriteViewModelFactory(EventDatabase.getInstance(requireContext()))
    }

    private lateinit var settingViewModel: SettingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)

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

        eventAdapter = EventAdapter(isDarkModeActive)
        binding.rvEvent.layoutManager = LinearLayoutManager(context)
        binding.rvEvent.adapter = eventAdapter

        favoriteViewModel.favoriteEvents.observe(viewLifecycleOwner, Observer { favoriteEvents ->
            binding.progressBar.visibility = View.GONE
            eventAdapter.submitList(favoriteEvents)
        })

        binding.progressBar.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

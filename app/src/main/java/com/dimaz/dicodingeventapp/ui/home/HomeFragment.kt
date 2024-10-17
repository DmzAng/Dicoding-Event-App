package com.dimaz.dicodingeventapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dimaz.dicodingeventapp.databinding.FragmentHomeBinding
import com.dimaz.dicodingeventapp.ui.EventAdapter

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var eventAdapter: EventAdapter
    private val homeViewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.rvEvent.layoutManager = LinearLayoutManager(context)
        eventAdapter = EventAdapter()
        binding.rvEvent.adapter = eventAdapter

        setupObserver()
        return binding.root
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

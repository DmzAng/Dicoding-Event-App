package com.dimaz.dicodingeventapp.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dimaz.dicodingeventapp.databinding.FragmentDashboardBinding
import com.dimaz.dicodingeventapp.ui.EventAdapter

class EventClearFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private lateinit var eventAdapter: EventAdapter
    private val evenClearViewModel: EvenClearViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)

        binding.rvEventClear.layoutManager = LinearLayoutManager(context)
        eventAdapter = EventAdapter()
        binding.rvEventClear.adapter = eventAdapter

        setupObserver()
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

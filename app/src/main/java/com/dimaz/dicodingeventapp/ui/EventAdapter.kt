package com.dimaz.dicodingeventapp.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dimaz.dicodingeventapp.data.response.ListEventsItem
import com.dimaz.dicodingeventapp.databinding.EventCardItemBinding
import com.dimaz.dicodingeventapp.ui.detail.DetailActivity

class EventAdapter : ListAdapter<ListEventsItem, EventAdapter.EventViewHolder>(DIFF_CALLBACK) {

    class EventViewHolder(private val binding: EventCardItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(event: ListEventsItem) {
            binding.tvEventName.text = event.name
            binding.tvDescription.text = event.description
            binding.tvEventDate.text = event.beginTime
            Glide.with(binding.tvEventImage.context)
                .load(event.imageLogo)
                .into(binding.tvEventImage)

            binding.root.setOnClickListener {
                val intent = Intent(binding.root.context, DetailActivity::class.java).apply {
                    putExtra(DetailActivity.EXTRA_NAME, event.name)
                    putExtra(DetailActivity.EXTRA_OWNER, event.ownerName)
                    putExtra(DetailActivity.EXTRA_DESCRIPTION, event.description)
                    putExtra(DetailActivity.EXTRA_BEGIN_TIME, event.beginTime)
                    putExtra(DetailActivity.EXTRA_QUOTA, event.quota)
                    putExtra(DetailActivity.EXTRA_REGISTRANTS, event.registrants)
                    putExtra(DetailActivity.EXTRA_IMAGE, event.imageLogo)
                    putExtra(DetailActivity.EXTRA_LINK, event.link)
                }
                binding.root.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val binding = EventCardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = getItem(position)
        holder.bind(event)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListEventsItem>() {
            override fun areItemsTheSame(oldItem: ListEventsItem, newItem: ListEventsItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ListEventsItem, newItem: ListEventsItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}

package com.dimaz.dicodingeventapp.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dimaz.dicodingeventapp.R
import com.dimaz.dicodingeventapp.data.local.entity.EventEntity
import com.dimaz.dicodingeventapp.databinding.EventCardItemBinding
import com.dimaz.dicodingeventapp.ui.detail.DetailActivity

class EventAdapter(private val isDarkModeActive: Boolean) : ListAdapter<EventEntity, EventAdapter.EventViewHolder>(DIFF_CALLBACK) {

    inner class EventViewHolder(private val binding: EventCardItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: EventEntity) {
            binding.tvEventName.text = item.name
            binding.tvDescription.text = item.description
            binding.tvEventDate.text = item.beginTime
            Glide.with(binding.tvEventImage.context)
                .load(item.mediaCover)
                .into(binding.tvEventImage)

            binding.cardEvent.setCardBackgroundColor(
                if (isDarkModeActive) {
                    ContextCompat.getColor(binding.cardEvent.context, R.color.gray_dark)
                } else {
                    ContextCompat.getColor(binding.cardEvent.context, R.color.white)
                }
            )

            binding.root.setOnClickListener {
                val intent = Intent(binding.root.context, DetailActivity::class.java).apply {
                    putExtra(DetailActivity.EXTRA_NAME, item.name)
                    putExtra(DetailActivity.EXTRA_OWNER, item.eventOwner)
                    putExtra(DetailActivity.EXTRA_DESCRIPTION, item.description)
                    putExtra(DetailActivity.EXTRA_BEGIN_TIME, item.beginTime)
                    putExtra(DetailActivity.EXTRA_IMAGE, item.mediaCover)
                    putExtra(DetailActivity.EXTRA_LINK, item.link)
                    putExtra(DetailActivity.EXTRA_QUOTA, item.quota)
                    putExtra(DetailActivity.EXTRA_REGISTRANTS, item.registrants)
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
        holder.bind(getItem(position))
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<EventEntity>() {
            override fun areItemsTheSame(oldItem: EventEntity, newItem: EventEntity): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: EventEntity, newItem: EventEntity): Boolean {
                return oldItem.name == newItem.name
            }
        }
    }
}

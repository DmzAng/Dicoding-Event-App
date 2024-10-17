package com.dimaz.dicodingeventapp.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.dimaz.dicodingeventapp.R
import com.dimaz.dicodingeventapp.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24)

        val eventName = intent.getStringExtra(EXTRA_NAME)
        val eventOwner = intent.getStringExtra(EXTRA_OWNER)
        val eventDescription = intent.getStringExtra(EXTRA_DESCRIPTION)
        val eventBeginTime = intent.getStringExtra(EXTRA_BEGIN_TIME)
        val eventImage = intent.getStringExtra(EXTRA_IMAGE)
        val eventLink = intent.getStringExtra(EXTRA_LINK)
        val eventQuota = intent.getIntExtra(EXTRA_QUOTA, 0)
        val eventRegistrants = intent.getIntExtra(EXTRA_REGISTRANTS, 0)
        val remainingQuota = eventQuota - eventRegistrants


        supportActionBar?.title = eventName

        binding.tvEventName.text = eventName
        binding.tvEventOwner.text = eventOwner
        binding.tvDescription.text = eventDescription
        binding.tvEventBeginTime.text = eventBeginTime
        binding.tvEventQuota.text = remainingQuota.toString()
        binding.btnRegister.visibility = View.VISIBLE

        Glide.with(this)
            .load(eventImage)
            .into(binding.imgItemPhotoDetail)

        binding.btnRegister.setOnClickListener {
            eventLink?.let {
                openLink(it)
            }
        }
    }

    private fun openLink(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_OWNER = "extra_owner"
        const val EXTRA_DESCRIPTION = "extra_description"
        const val EXTRA_BEGIN_TIME = "extra_begin_time"
        const val EXTRA_QUOTA = "extra_quota"
        const val EXTRA_REGISTRANTS = "extra_registrants"
        const val EXTRA_IMAGE = "extra_image"
        const val EXTRA_LINK = "extra_link"
    }
}

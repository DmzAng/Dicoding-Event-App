package com.dimaz.dicodingeventapp.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dimaz.dicodingeventapp.data.local.entity.EventEntity
import com.dimaz.dicodingeventapp.data.local.room.EventDatabase
import kotlinx.coroutines.launch

class FavoriteViewModel(private val eventDao: EventDatabase) : ViewModel() {

    // Mengambil daftar acara favorit
    val favoriteEvents: LiveData<List<EventEntity>> = eventDao.eventDao().getFavoriteEvents()

    // Contoh untuk menambah logika lain, seperti menghapus event favorit
    fun removeFavorite(event: EventEntity) {
        viewModelScope.launch {
            eventDao.eventDao().deleteEvent(event)
        }
    }
}

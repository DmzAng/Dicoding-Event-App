package com.dimaz.dicodingeventapp.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dimaz.dicodingeventapp.data.local.room.EventDatabase

class FavoriteViewModelFactory(private val database: EventDatabase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FavoriteViewModel(database) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

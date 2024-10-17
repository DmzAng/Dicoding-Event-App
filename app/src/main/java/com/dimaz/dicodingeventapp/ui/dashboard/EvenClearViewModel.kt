package com.dimaz.dicodingeventapp.ui.dashboard

import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dimaz.dicodingeventapp.data.conect.ApiConfig
import com.dimaz.dicodingeventapp.data.response.ListEventsItem
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import android.text.Html

class EvenClearViewModel : ViewModel() {

    private val _eventList = MutableLiveData<List<ListEventsItem>>()
    val eventList: LiveData<List<ListEventsItem>> = _eventList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String>()

    init {
        getCompletedEvents()
    }

    private fun getCompletedEvents() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val apiService = ApiConfig.getApiService()
                val response = apiService.getEvents()
                val completedEvents = response.listEvents.filter { event ->
                    isCompletedEvent(event.beginTime)
                }.map { event ->
                    event.copy(description = extractRelevantDescription(event.description))
                }
                _eventList.value = completedEvents
            } catch (e: Exception) {
                _errorMessage.value = "Error: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun isCompletedEvent(eventBeginTime: String): Boolean {
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val eventDate = format.parse(eventBeginTime)
        val currentDate = Date()

        return eventDate != null && eventDate.before(currentDate)
    }

    private fun extractRelevantDescription(html: String): String {
        val htmlWithoutImages = html.replace(Regex("<img.*?>", RegexOption.IGNORE_CASE), "")

        val plainText = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            Html.fromHtml(htmlWithoutImages, Html.FROM_HTML_MODE_COMPACT).toString()
        } else {
            Html.fromHtml(htmlWithoutImages).toString()
        }

        val paragraphs = plainText.split("\n").map { it.trim() }

        val relevantParagraphs = paragraphs.takeWhile {
            !it.contains("Benefit", ignoreCase = true) &&
                    !it.contains("Rundown", ignoreCase = true) &&
                    !it.contains("FAQ", ignoreCase = true)
        }

        return relevantParagraphs.joinToString("\n\n")
    }
}

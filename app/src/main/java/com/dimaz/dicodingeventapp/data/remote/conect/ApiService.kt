package com.dimaz.dicodingeventapp.data.remote.conect

import com.dimaz.dicodingeventapp.data.remote.response.EventResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("events")
    suspend fun getEvents(): EventResponse

}

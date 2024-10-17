package com.dimaz.dicodingeventapp.data.conect

import com.dimaz.dicodingeventapp.data.response.EventResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("events")
    suspend fun getEvents(): EventResponse

//    @GET("events/{id}")
//    suspend fun getEventDetail(
//        @Path("id") id: Int
//    ): EventResponse
}

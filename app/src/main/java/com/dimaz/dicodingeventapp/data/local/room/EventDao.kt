package com.dimaz.dicodingeventapp.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dimaz.dicodingeventapp.data.local.entity.EventEntity

@Dao
interface EventDao {
    @Query("SELECT * FROM favorite_events ORDER BY beginTime DESC")
    fun getFavoriteEvents(): LiveData<List<EventEntity>>

    @Query("SELECT * FROM favorite_events WHERE name = :eventName LIMIT 1")
    suspend fun getEventByName(eventName: String): EventEntity?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertEvent(event: EventEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertEvents(events: List<EventEntity>)

    @Update
    suspend fun updateEvent(event: EventEntity)

    @Delete
    suspend fun deleteEvent(event: EventEntity)

    @Query("DELETE FROM favorite_events")
    suspend fun deleteAll()

    @Query("SELECT EXISTS(SELECT * FROM favorite_events WHERE name = :eventName)")
    suspend fun isEventFavorite(eventName: String): Boolean
}


package com.dimaz.dicodingeventapp.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_events")
class EventEntity(
    @field:ColumnInfo(name = "name")
    @field:PrimaryKey
    val name: String,

    @field:ColumnInfo(name = "description")
    val description: String,

    @field:ColumnInfo(name = "mediaCover")
    val mediaCover: String,

    @field:ColumnInfo(name = "eventOwner")
    val eventOwner: String,

    @field:ColumnInfo(name = "link")
    val link: String,

    @field:ColumnInfo(name = "quota")
    val quota: Int,

    @field:ColumnInfo(name = "registrants")
    val registrants: Int,

    @field:ColumnInfo(name = "beginTime")
    val beginTime: String,

    @field:ColumnInfo(name = "favorite")
    var isFavorite: Boolean = false
)

package com.example.productivity.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(
    tableName = "note_item"
)
data class NoteItem(
    @PrimaryKey
    val id: Int? = null,
    val title: String,
    val description: String,
    val time : String
)

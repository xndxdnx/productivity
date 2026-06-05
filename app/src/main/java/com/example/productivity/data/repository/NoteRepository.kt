package com.example.productivity.data.repository

import com.example.productivity.data.entities.NoteItem
import kotlinx.coroutines.flow.Flow


interface NoteRepository {
    
    suspend fun insertItem (note: NoteItem)

    suspend fun deleteItem(note: NoteItem)

    fun getAllItems(): Flow<List<NoteItem>>
    
    suspend fun getNoteItemById(id: Int) : NoteItem
    
}
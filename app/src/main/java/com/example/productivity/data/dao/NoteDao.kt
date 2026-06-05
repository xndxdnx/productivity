package com.example.productivity.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.productivity.data.entities.NoteItem
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    
    @Insert(onConflict =  OnConflictStrategy.REPLACE)
    suspend fun insertItem (note: NoteItem)
    
    @Delete
    suspend fun deleteItem(note: NoteItem)
    
    @Query("SELECT * FROM note_item")
    fun getAllItems(): Flow<List<NoteItem>>
    
    @Query("SELECT * FROM note_item WHERE id = :id")
    suspend fun getNoteItemById(id: Int) : NoteItem
    
    
    
}
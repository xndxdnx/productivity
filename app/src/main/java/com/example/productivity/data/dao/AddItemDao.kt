package com.example.productivity.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.productivity.data.entities.AddItem
import com.example.productivity.data.entities.NoteItem
import kotlinx.coroutines.flow.Flow

@Dao
interface AddItemDao {
    @Insert(onConflict =  OnConflictStrategy.REPLACE)
    suspend fun insertItem (item: AddItem)

    @Delete
    suspend fun deleteItem(item: AddItem)
    
    @Query("SELECT * FROM add_item WHERE listId = :listId")
    fun getItemsByListId(listId: Int) : Flow<List<AddItem>>

    @Query("SELECT * FROM add_item WHERE id = :id")
    suspend fun getItemById(id: Int) : AddItem?
    
}
package com.example.productivity.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.productivity.data.entities.AddItem
import com.example.productivity.data.entities.NoteItem
import com.example.productivity.data.entities.ShoppingListItem
import kotlinx.coroutines.flow.Flow


@Dao
interface ShoppingListDao {

    @Insert(onConflict =  OnConflictStrategy.REPLACE)
    suspend fun insertItem (item: ShoppingListItem)

    @Delete
    suspend fun deleteItem(item: ShoppingListItem)

    @Query("SELECT * FROM shopping_list")
    fun getAllItems(): Flow<List<ShoppingListItem>>
    
    @Query("SELECT * FROM shopping_list WHERE id = :listId")
    suspend fun getAllItemsById(listId: Int): ShoppingListItem?
    
}
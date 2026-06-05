package com.example.productivity.data.repository

import com.example.productivity.data.entities.AddItem
import kotlinx.coroutines.flow.Flow

interface AddItemRepository {

    suspend fun insertItem (item: AddItem)
    suspend fun deleteItem(item: AddItem)
    fun getItemsByListId(listId: Int) : Flow<List<AddItem>>
    suspend fun getItemById(id: Int) : AddItem?
}
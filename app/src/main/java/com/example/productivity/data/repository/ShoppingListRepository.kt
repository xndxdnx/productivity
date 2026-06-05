package com.example.productivity.data.repository

import com.example.productivity.data.entities.ShoppingListItem
import kotlinx.coroutines.flow.Flow

interface ShoppingListRepository {

    suspend fun insertItem (item: ShoppingListItem)
    suspend fun deleteItem(item: ShoppingListItem)
    fun getAllItems(): Flow<List<ShoppingListItem>>
    suspend fun getAllItemsById(listId: Int): ShoppingListItem?
}
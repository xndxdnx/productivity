package com.example.productivity.data.repository_impl

import com.example.productivity.data.dao.ShoppingListDao
import com.example.productivity.data.entities.ShoppingListItem
import com.example.productivity.data.repository.ShoppingListRepository
import kotlinx.coroutines.flow.Flow

class ShoppingListRepositoryImpl(
    private val dao : ShoppingListDao
) : ShoppingListRepository{
    override suspend fun insertItem(item: ShoppingListItem) {
       dao.insertItem(item)
    }

    override suspend fun deleteItem(item: ShoppingListItem) {
       dao.deleteItem(item)
    }

    override fun getAllItems(): Flow<List<ShoppingListItem>> {
      return dao.getAllItems()
    }

    override suspend fun getAllItemsById(listId: Int): ShoppingListItem? {
       return dao.getAllItemsById(listId)
    }


}
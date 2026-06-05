package com.example.productivity.data.repository_impl

import com.example.productivity.data.dao.AddItemDao
import com.example.productivity.data.entities.AddItem
import com.example.productivity.data.repository.AddItemRepository
import kotlinx.coroutines.flow.Flow

class AddItemRepositoryImpl(
    private val dao: AddItemDao
) : AddItemRepository {
    override suspend fun insertItem(item: AddItem) {
        dao.insertItem(item)
    }

    override suspend fun deleteItem(item: AddItem) {
       dao.deleteItem(item)
    }

    override fun getItemsByListId(listId: Int): Flow<List<AddItem>> {
        return dao.getItemsByListId(listId)
    }

    override suspend fun getItemById(id: Int): AddItem? {
       return dao.getItemById(id)
    }


}
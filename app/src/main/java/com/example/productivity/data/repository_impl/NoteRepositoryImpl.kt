package com.example.productivity.data.repository_impl

import com.example.productivity.data.dao.NoteDao
import com.example.productivity.data.entities.NoteItem
import com.example.productivity.data.repository.NoteRepository
import kotlinx.coroutines.flow.Flow


class NoteRepositoryImpl(
    private val dao: NoteDao
): NoteRepository {
    override suspend fun insertItem(note: NoteItem) {
       dao.insertItem(note = note)
    }

    override suspend fun deleteItem(note: NoteItem) {
       dao.deleteItem(note = note)
    }

    override fun getAllItems(): Flow<List<NoteItem>> {
       return dao.getAllItems()
    }

    override suspend fun getNoteItemById(id: Int): NoteItem {
        return dao.getNoteItemById(id)
    }


}
package com.example.productivity.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.productivity.data.dao.AddItemDao
import com.example.productivity.data.dao.NoteDao
import com.example.productivity.data.dao.ShoppingListDao
import com.example.productivity.data.entities.AddItem
import com.example.productivity.data.entities.NoteItem
import com.example.productivity.data.entities.ShoppingListItem

@Database(
    entities = [
        AddItem::class,
        NoteItem::class,
        ShoppingListItem::class
    ],
    version = 1
)

abstract class MainDatabase : RoomDatabase() {
    abstract val addItemDao: AddItemDao
    abstract val noteDao: NoteDao
    abstract val shoppingListDao: ShoppingListDao
}
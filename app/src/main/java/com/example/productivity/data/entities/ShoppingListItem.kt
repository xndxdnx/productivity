package com.example.productivity.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "shopping_list"
)
data class ShoppingListItem(
    @PrimaryKey
    val id: Int? = null,
    val name: String,
    val time: String,
    val allItemsCount: Int,
    val allSelectedItemsCount: Int
)

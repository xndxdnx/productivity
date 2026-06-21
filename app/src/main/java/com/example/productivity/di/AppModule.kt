package com.example.productivity.di

import android.app.Application
import androidx.room.Room
import com.example.productivity.data.MainDatabase
import com.example.productivity.data.dao.ShoppingListDao
import com.example.productivity.data.repository.AddItemRepository
import com.example.productivity.data.repository.NoteRepository
import com.example.productivity.data.repository.ShoppingListRepository
import com.example.productivity.data.repository_impl.AddItemRepositoryImpl
import com.example.productivity.data.repository_impl.NoteRepositoryImpl
import com.example.productivity.data.repository_impl.ShoppingListRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    
    @Provides
    @Singleton
    fun provideDatabase(app : Application): MainDatabase {
        return Room.databaseBuilder(
            name = "db",
            klass = MainDatabase::class.java,
            context = app
        ).build()
    }
    
    
    @Provides
    @Singleton
    fun provideShoppingListRepository(
        database: MainDatabase
    ) : ShoppingListRepository {
        return ShoppingListRepositoryImpl(database.shoppingListDao)
    }

    @Provides
    @Singleton
    fun provideNoteRepository(
        database: MainDatabase
    ) : NoteRepository {
        return NoteRepositoryImpl(database.noteDao)
    }

    @Provides
    @Singleton
    fun provideAddItemRepository(
        database: MainDatabase
    ) : AddItemRepository {
        return AddItemRepositoryImpl(database.addItemDao)
    }


}
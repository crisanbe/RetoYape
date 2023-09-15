package com.example.retolistaderecetas.di

import android.app.Application
import androidx.room.Room
import com.example.retolistaderecetas.data.source.remote.local.Cache
import com.example.retolistaderecetas.data.source.remote.local.RecipeDatabase
import com.example.retolistaderecetas.data.source.remote.local.RoomCache
import com.example.retolistaderecetas.data.source.remote.local.daos.RecipesDao
import com.example.retolistaderecetas.util.DB_NAME
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LocalModule {

    @Binds
    abstract fun bindCache(cache: RoomCache): Cache

    companion object {
        @Provides
        @Singleton
        fun provideDatabase(app: Application): RecipeDatabase {
            return Room.databaseBuilder(
                app,
                RecipeDatabase::class.java,
                DB_NAME
            ).build()
        }

        @Provides
        fun provideRecipeDao(db: RecipeDatabase): RecipesDao = db.recipeDao()
    }
}
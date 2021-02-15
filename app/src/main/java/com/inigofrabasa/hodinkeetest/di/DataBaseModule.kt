package com.inigofrabasa.hodinkeetest.di

import androidx.room.Room
import com.inigofrabasa.hodinkeetest.HodinkeeApplication
import com.inigofrabasa.hodinkeetest.database.AppDatabase
import com.inigofrabasa.hodinkeetest.database.PostsDao
import com.inigofrabasa.hodinkeetest.utils.DATABASE_NAME
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule(application: HodinkeeApplication) {

    private var appDatabase : AppDatabase = Room.databaseBuilder(
        application.applicationContext,
        AppDatabase::class.java,
        DATABASE_NAME).
    build()

    @Singleton
    @Provides
    fun providesRoomDatabase(): AppDatabase {
        return appDatabase
    }

    @Singleton
    @Provides
    fun providesProductDao(appDatabase: AppDatabase): PostsDao {
        return appDatabase.postsDao()
    }
}
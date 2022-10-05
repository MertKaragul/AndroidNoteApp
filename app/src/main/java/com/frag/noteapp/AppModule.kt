package com.frag.noteapp

import android.content.Context
import androidx.room.Room
import com.frag.noteapp.Database.AppDatabase
import com.frag.noteapp.Database.DatabaseDAO
import com.frag.noteapp.Database.Usernotemodel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideChannelDao(@ApplicationContext context : Context) = Room.databaseBuilder(context , AppDatabase::class.java, "note").build()

}
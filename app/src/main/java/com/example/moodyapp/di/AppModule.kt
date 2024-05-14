package com.example.moodyapp.di

import android.app.Application
import com.example.moodyapp.data.manger.LocalUserMangerImpl
import com.example.moodyapp.domain.manger.LocalUserManger
import com.example.moodyapp.domain.usecases.app_entry.AppEntryUseCases
import com.example.moodyapp.domain.usecases.app_entry.ReadAppEntry
import com.example.moodyapp.domain.usecases.app_entry.SaveAppEntry
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
    fun provideLocalUserManger(
        application: Application
    ): LocalUserManger = LocalUserMangerImpl(context = application)

    @Provides
    @Singleton
    fun provideAppEntryUseCases(
        localUserManger: LocalUserManger
    ): AppEntryUseCases = AppEntryUseCases(
        readAppEntry = ReadAppEntry(localUserManger),
        saveAppEntry = SaveAppEntry(localUserManger)
    )
}
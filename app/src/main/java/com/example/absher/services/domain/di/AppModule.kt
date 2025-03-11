package com.example.absher.services.domain.di// domain/di/AppModule.kt
import androidx.datastore.core.DataStore
import com.example.absher.services.adapter.MeetingApiAdapter
import com.example.absher.services.data.datasource.RemoteMeetingDataSource
import com.example.absher.services.domain.repository.MeetingRepository
import com.example.absher.services.domain.repository.MeetingRepositoryImpl
import com.example.absher.services.domain.usecases.GetMeetingsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import android.content.Context

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideMeetingApiAdapter(): MeetingApiAdapter = MeetingApiAdapter()

    @Provides
    @Singleton
    fun provideRemoteMeetingDataSource(apiAdapter: MeetingApiAdapter): RemoteMeetingDataSource =
        RemoteMeetingDataSource(apiAdapter)

    @Provides
    @Singleton
    fun provideMeetingRepository(dataSource: RemoteMeetingDataSource): MeetingRepository =
        MeetingRepositoryImpl(dataSource)

    @Provides
    @Singleton
    fun provideGetMeetingsUseCase(repository: MeetingRepository): GetMeetingsUseCase =
        GetMeetingsUseCase(repository)

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> =
        context.dataStore
}
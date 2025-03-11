package com.example.absher.services.domain.di// domain/di/AppModule.kt


import android.content.Context
import android.content.SharedPreferences
import com.example.absher.services.adapter.AuthInterceptor
import com.example.absher.services.adapter.MeetingApiAdapter
import com.example.absher.services.adapter.TokenManager
import com.example.absher.services.data.datasource.RemoteMeetingDataSource
import com.example.absher.services.domain.repository.MeetingRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideTokenManager(sharedPreferences: SharedPreferences): TokenManager {
        return TokenManager(sharedPreferences)
    }

    @Provides
    @Singleton
    fun provideAuthInterceptor(tokenManager: TokenManager): AuthInterceptor {
        return AuthInterceptor(tokenManager)
    }

    @Provides
    @Singleton
    fun provideMeetingApiAdapter(authInterceptor: AuthInterceptor): MeetingApiAdapter {
        return MeetingApiAdapter(authInterceptor)
    }

    @Provides
    @Singleton
    fun provideRemoteMeetingDataSource(apiAdapter: MeetingApiAdapter): RemoteMeetingDataSource {
        return RemoteMeetingDataSource(apiAdapter)
    }

    @Provides
    @Singleton
    fun provideMeetingRepository(dataSource: RemoteMeetingDataSource): MeetingRepository {
        return MeetingRepository(dataSource)
    }
}
package com.example.absher.services.domain.di// domain/di/AppModule.kt


import android.content.Context
import android.content.SharedPreferences
import com.example.absher.services.adapter.AuthInterceptor
import com.example.absher.services.adapter.MeetingApiAdapter
import com.example.absher.services.adapter.TokenManager
import com.example.absher.services.data.datasource.RemoteMeetingDataSource
import com.example.absher.services.domain.repository.MeetingRepository
import com.example.absher.services.domain.usecases.GetMeetingsUseCase
import com.example.absher.services.adapter.TokenApiAdapter
import com.example.absher.services.adapter.HttpExceptionHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
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
    fun provideTokenApiAdapter(tokenManager: TokenManager): TokenApiAdapter {
        return TokenApiAdapter(tokenManager)
    }

    @Provides
    @Singleton
    fun provideHttpExceptionHandler(tokenApiAdapter: TokenApiAdapter): HttpExceptionHandler {
        return HttpExceptionHandler(tokenApiAdapter)
    }

    @Provides
    @Singleton
    fun provideMeetingApiAdapter(
        httpExceptionHandler: HttpExceptionHandler,
        okHttpClient: OkHttpClient
    ): MeetingApiAdapter {
        return MeetingApiAdapter(httpExceptionHandler, okHttpClient)
    }

    @Provides
    @Singleton
    fun provideRemoteMeetingDataSource(meetingApiAdapter: MeetingApiAdapter): RemoteMeetingDataSource {
        return RemoteMeetingDataSource(meetingApiAdapter)
    }

    @Provides
    @Singleton
    fun provideMeetingRepository(remoteDataSource: RemoteMeetingDataSource): MeetingRepository {
        return MeetingRepository(remoteDataSource)
    }

    @Provides
    @Singleton
    fun provideGetMeetingsUseCase(meetingRepository: MeetingRepository): GetMeetingsUseCase {
        return GetMeetingsUseCase(meetingRepository)
    }
}
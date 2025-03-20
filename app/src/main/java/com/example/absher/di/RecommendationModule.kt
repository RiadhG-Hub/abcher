package com.example.absher.di

import com.example.absher.services.adapter.RecommendationApiAdapter
import com.example.absher.services.data.datasource.RemoteRecommendationMeetingDataSource
import com.example.absher.services.domain.repository.RecommendationRepository
import com.example.absher.services.domain.usecases.GetRecommendationUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RecommendationModule {

    @Provides
    @Singleton
    fun provideRecommendationRepository(): RecommendationRepository {
        return RecommendationRepository(
            RemoteRecommendationMeetingDataSource(
                RecommendationApiAdapter()
            )
        )
    }

    @Provides
    @Singleton
    fun provideGetRecommendationUseCase(repository: RecommendationRepository): GetRecommendationUseCase {
        return GetRecommendationUseCase(repository)
    }
} 
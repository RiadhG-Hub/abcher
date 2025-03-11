package com.example.absher.services.domain.repository// domain/repository/MeetingRepository.kt
import com.example.absher.services.data.datasource.RemoteMeetingDataSource
import com.example.absher.services.data.models.Meeting
import javax.inject.Inject

interface MeetingRepository {
    suspend fun getMeetings(): List<Meeting>
}

class MeetingRepositoryImpl @Inject constructor(
    private val dataSource: RemoteMeetingDataSource
) : MeetingRepository {
    override suspend fun getMeetings(): List<Meeting> {
        return dataSource.getMeetings()
    }
}
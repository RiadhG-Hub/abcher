package com.example.absher.services.domain.repository// domain/repository/MeetingRepository.kt
import com.example.absher.services.data.datasource.RemoteMeetingDataSource
import com.example.absher.services.data.models.Meeting
import javax.inject.Inject

class MeetingRepository @Inject constructor(
    private val remoteDataSource: RemoteMeetingDataSource
) {
    suspend fun getMeetings(from : Int = 0,    to : Int = 10): List<Meeting>? {
        return remoteDataSource.getMeetings(from = from, to = to)
    }
}
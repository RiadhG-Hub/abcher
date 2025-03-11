package com.example.absher.services.domain.usecases// domain/usecases/GetMeetingsUseCase.kt
import com.example.absher.services.data.models.Meeting
import com.example.absher.services.domain.repository.MeetingRepository
import javax.inject.Inject

class GetMeetingsUseCase @Inject constructor(
    private val meetingRepository: MeetingRepository
) {
    suspend fun execute(from : Int = 0,    to : Int = 10): List<Meeting>? {
        return meetingRepository.getMeetings(from = from, to = to)
    }
}
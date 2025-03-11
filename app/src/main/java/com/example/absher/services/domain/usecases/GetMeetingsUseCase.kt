package com.example.absher.services.domain.usecases// domain/usecases/GetMeetingsUseCase.kt
import com.example.absher.services.data.models.Meeting
import com.example.absher.services.domain.repository.MeetingRepository
import javax.inject.Inject

class GetMeetingsUseCase @Inject constructor(private val repository: MeetingRepository) {
    suspend operator fun invoke(): List<Meeting> {
        return repository.getMeetings()
    }
}
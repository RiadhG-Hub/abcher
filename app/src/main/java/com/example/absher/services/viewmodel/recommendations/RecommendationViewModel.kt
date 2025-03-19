package com.example.absher.services.viewmodel.recommendations// viewmodel/RecommendationViewModel.kt

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.absher.services.data.models.meetings.MeetingRequestBody
import com.example.absher.services.data.models.recommendations.FetchRecommendationInfoData
import com.example.absher.services.data.models.recommendations.FetchRecommendationInfoResponse
import com.example.absher.services.data.models.recommendations.Recommendation
import com.example.absher.services.data.models.recommendations.RecommendationResponse
import com.example.absher.services.domain.usecases.GetRecommendationUseCase
import kotlinx.coroutines.launch

class RecommendationViewModel(
    private val getRecommendationsUseCase: GetRecommendationUseCase
) : ViewModel() {
    private val _fetchRecommendationState = MutableLiveData<FetchRecommendationState>()
    val fetchRecommendationState: LiveData<FetchRecommendationState> = _fetchRecommendationState


    private val _fetchRecommendationInfoState = MutableLiveData<FetchRecommendationInfoState>()
    val fetchRecommendationInfoState: LiveData<FetchRecommendationInfoState> = _fetchRecommendationInfoState



    private var currentPage = 1
    private val pageSize = 10
    private var isLoading = false
    private var hasMoreData = true
    private val allRecommendations = mutableListOf<Recommendation>()

    init {
        fetchRecommendations()
    }

    fun fetchRecommendations(requestBody : MeetingRequestBody = MeetingRequestBody(
        meetingReferenceNo = null,
        fromDate = null,
        toDate = null,
        title = null
    )) {
        if (isLoading || !hasMoreData) return

        isLoading = true
        _fetchRecommendationState.value = FetchRecommendationStateLoading()

        viewModelScope.launch {
            try {
                val requestResponse : RecommendationResponse? = getRecommendationsUseCase.fetchRecommendations(
                    from = currentPage,
                    to = pageSize,

                    requestBody = requestBody

                )

                if(requestResponse == null){
                    _fetchRecommendationState.postValue(
                        FetchRecommendationStateError( "Null Response")
                    )

                }else {
                    val  response = requestResponse.data

                    val newRecommendations   = response?.data ?: emptyList<Recommendation>()

                    allRecommendations.addAll(newRecommendations)
                    hasMoreData = newRecommendations.size == pageSize
                    currentPage++

                    _fetchRecommendationState.postValue(
                        FetchRecommendationStateSuccess(allRecommendations.toList())
                    )
                }


            } catch (e: Exception) {
                _fetchRecommendationState.postValue(
                    FetchRecommendationStateError(e.message ?: "Unknown error")
                )
            } finally {
                isLoading = false
            }
        }
    }


    fun fetchRecommendationInfo(recommendationID : Int ) {



        _fetchRecommendationInfoState.value = FetchRecommendationInfoStateLoading()

        viewModelScope.launch {
            try {
                val requestResponse : FetchRecommendationInfoResponse? = getRecommendationsUseCase.fetchRecommendationInfo(recommendationID )

                if(requestResponse == null){
                    _fetchRecommendationInfoState.postValue(
                        FetchRecommendationInfoStateError( "Null Response")
                    )

                }else {
                    val  response : FetchRecommendationInfoData = requestResponse.data





                    _fetchRecommendationInfoState.postValue(
                        FetchRecommendationInfoStateSuccess(response)
                    )
                }


            } catch (e: Exception) {
                _fetchRecommendationInfoState.postValue(
                    FetchRecommendationInfoStateError(e.message ?: "Unknown error")
                )
            } finally {
                isLoading = false
            }
        }
    }


    fun resetAndFetch() {
        currentPage = 1
        allRecommendations.clear()
        hasMoreData = true
        fetchRecommendations()
    }
}

// Define states
sealed class FetchRecommendationState
class FetchRecommendationStateInit : FetchRecommendationState()
class FetchRecommendationStateLoading : FetchRecommendationState()
data class FetchRecommendationStateSuccess(val recommendations: List<Recommendation>?) : FetchRecommendationState()
data class FetchRecommendationStateError(val error: String) : FetchRecommendationState()



sealed class FetchRecommendationInfoState
class FetchRecommendationInfoStateInit : FetchRecommendationInfoState()
class FetchRecommendationInfoStateLoading : FetchRecommendationInfoState()
data class FetchRecommendationInfoStateSuccess(val recommendation: FetchRecommendationInfoData) : FetchRecommendationInfoState()
data class FetchRecommendationInfoStateError(val error: String) : FetchRecommendationInfoState()
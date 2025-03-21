
import android.content.SharedPreferences
import com.example.absher.services.adapter.HttpExceptionHandler
import com.example.absher.services.adapter.RecommendationApiAdapter
import com.example.absher.services.adapter.TokenApiAdapter
import com.example.absher.services.adapter.TokenManager
import com.example.absher.services.data.models.recommendations.FetchRecommendationInfoResponse
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class RecommendationApiAdapterTest {
    private lateinit var meetingApiAdapter: RecommendationApiAdapter
    private lateinit var httpExceptionHandler: HttpExceptionHandler
    private lateinit var tokenManager: TokenManager
    private lateinit var tokenApiAdapter: TokenApiAdapter

    @Mock
    private lateinit var mockSharedPreferences: SharedPreferences

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)

        // Create a real TokenManager with mocked SharedPreferences
        tokenManager = TokenManager(mockSharedPreferences)

        // Mock the SharedPreferences behavior
        Mockito.`when`(mockSharedPreferences.getString("token", null))
            .thenReturn("Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhY2NvdW50IjoiZ0U1YUR0dDR1NHd4dkIzelR6ZG05QVgvaDlrd01QcnBzbWdQYldiTmc0QT0iLCJkZXBhcnRtZW50IjoieVI3bWZMNzNLSGhPWXh1Q1pJRm1uK2htbVdFcUtYZlVvS1BXRitnNEd3Zz0iLCJuYW1lIjoiVGtmMVpoWVJUdkxOeTdiVHBCWGdZemZsdVVya2MwTEVQa2hIUUVGUXZ6RjVPZnBQRHcrTFkwcTIwRG5tTjZSZCIsImh0dHA6Ly9zY2hlbWFzLnhtbHNvYXAub3JnL3dzLzIwMDUvMDUvaWRlbnRpdHkvY2xhaW1zL25hbWVpZGVudGlmaWVyIjoiMSIsImxhbmciOiJhciIsImV4cCI6MTc0MjQ4NTI1NiwiaXNzIjoiSW50YWxpbyIsImF1ZCI6IkludGFsaW8ifQ.xjKVkEPKlsqs57cgbnZ0TC49VePsWVyWYJwG_OoMfKU") // Replace with your actual test token

        tokenApiAdapter = TokenApiAdapter(tokenManager)
        httpExceptionHandler = HttpExceptionHandler(tokenApiAdapter)

        // Create OkHttpClient with real configuration
        val okHttpClient = OkHttpClient.Builder()
            .build()

        meetingApiAdapter = RecommendationApiAdapter(httpExceptionHandler, okHttpClient)
    }

    @Test
    fun `fetchMeetings should return real MeetingResponse`() = runBlocking {
        // Make the actual request to real server
        val result = meetingApiAdapter. fetchRecommendations(1, 10)


        assert(result?.success == true)
        println("Fetched Meetings: $result")
    }

    @Test
    fun `fetchMeetingAttendees should return real MeetingResponse`() = runBlocking {
        // Make the actual request to real server
        val result : FetchRecommendationInfoResponse? = meetingApiAdapter. fetchRecommendationInfo(5059, )

        // Verify the response
        assert(result != null)
        assert(result?.success == true)
        println("Fetched Attendees: $result")
    }

    @Test
    fun `fetchMeetingAgenda should return real MeetingResponse`() = runBlocking {
        // Make the actual request to real server
        val result = meetingApiAdapter.fetchRecommendationStatuses()

        // Verify the response
        assert(result != null)
        assert(result?.success == true)
        println("Fetched Agendas: $result")
    }


}

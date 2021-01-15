package com.pcsbambang.app.plesbol.presenter

import com.google.gson.Gson
import com.pcsbambang.app.plesbol.api.ApiRepository
import com.pcsbambang.app.plesbol.model.EventDetails
import com.pcsbambang.app.plesbol.model.EventDetailsResponse
import com.pcsbambang.app.plesbol.model.TeamDetails
import com.pcsbambang.app.plesbol.model.TeamDetailsResponse
import com.pcsbambang.app.plesbol.util.CoroutineContextProviderTest
import com.pcsbambang.app.plesbol.view.EventDetailsView
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class EventDetailsPresenterTest {

    @Mock
    private lateinit var view: EventDetailsView
    @Mock
    private lateinit var gson: Gson
    @Mock
    private lateinit var apiRepository: ApiRepository
    @Mock
    private lateinit var apiResponse: Deferred<String>

    private lateinit var presenter: EventDetailsPresenter

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        presenter = EventDetailsPresenter(view, apiRepository, gson, CoroutineContextProviderTest())
    }

    @Test
    fun getEventDetails() {
        val eventDetail: MutableList<EventDetails> = mutableListOf()
        val homeTeam: MutableList<TeamDetails> = mutableListOf()
        val awayTeam: MutableList<TeamDetails> = mutableListOf()
        val responseEventDetails = EventDetailsResponse(eventDetail)
        val responseHomeTeam = TeamDetailsResponse(homeTeam)
        val responseAwayTeam = TeamDetailsResponse(awayTeam)
        val eventId = "670825"
        val homeTeamId = "133970"
        val awayTeamId = "133602"

        runBlocking {
            Mockito.`when`(apiRepository.doRequest(ArgumentMatchers.anyString()))
                .thenReturn(apiResponse)

            Mockito.`when`(apiResponse.await()).thenReturn("")

            Mockito.`when`(
                gson.fromJson(
                    "",
                    EventDetailsResponse::class.java
                )
            ).thenReturn(responseEventDetails)

            Mockito.`when`(
                gson.fromJson(
                    "",
                    TeamDetailsResponse::class.java
                )
            ).thenReturn(responseHomeTeam)

            Mockito.`when`(
                gson.fromJson(
                    "",
                    TeamDetailsResponse::class.java
                )
            ).thenReturn(responseAwayTeam)

            presenter.getEventDetails(eventId, homeTeamId, awayTeamId)

            Mockito.verify(view).showLoading()
            Mockito.verify(view).showEVentDetails(eventDetail, homeTeam, awayTeam)
            Mockito.verify(view).hideLoading()
        }
    }
}
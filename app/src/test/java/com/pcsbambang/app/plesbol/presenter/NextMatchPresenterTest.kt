package com.pcsbambang.app.plesbol.presenter

import com.google.gson.Gson
import com.pcsbambang.app.plesbol.api.ApiRepository
import com.pcsbambang.app.plesbol.model.Events
import com.pcsbambang.app.plesbol.model.EventsResponse
import com.pcsbambang.app.plesbol.util.CoroutineContextProviderTest
import com.pcsbambang.app.plesbol.view.NextMatchlView
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class NextMatchPresenterTest {

    @Mock
    private lateinit var view: NextMatchlView
    @Mock
    private lateinit var gson: Gson
    @Mock
    private lateinit var apiRepository: ApiRepository
    @Mock
    private lateinit var apiResponse: Deferred<String>

    private lateinit var presenter: NextMatchPresenter

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        presenter = NextMatchPresenter(view, apiRepository, gson, CoroutineContextProviderTest())
    }

    @Test
    fun getNextMatch() {
        val nextMatch: MutableList<Events> = mutableListOf()
        val response = EventsResponse(nextMatch)
        val leagueId = "4328"

        runBlocking {
            Mockito.`when`(apiRepository.doRequest(ArgumentMatchers.anyString()))
                .thenReturn(apiResponse)

            Mockito.`when`(apiResponse.await()).thenReturn("")

            Mockito.`when`(
                gson.fromJson(
                    "",
                    EventsResponse::class.java
                )
            ).thenReturn(response)

            presenter.getNextMatch(leagueId)

            Mockito.verify(view).showNextMatch(nextMatch)
        }
    }
}
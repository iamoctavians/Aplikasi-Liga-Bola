package com.pcsbambang.app.plesbol.presenter

import com.google.gson.Gson
import com.pcsbambang.app.plesbol.api.ApiRepository
import com.pcsbambang.app.plesbol.model.Events
import com.pcsbambang.app.plesbol.model.EventsResponse
import com.pcsbambang.app.plesbol.util.CoroutineContextProviderTest
import com.pcsbambang.app.plesbol.view.PreviousMatchlView
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class PreviousMatchPresenterTest {

    @Mock
    private lateinit var view: PreviousMatchlView
    @Mock
    private lateinit var gson: Gson
    @Mock
    private lateinit var apiRepository: ApiRepository
    @Mock
    private lateinit var apiResponse: Deferred<String>

    private lateinit var presenter: PreviousMatchPresenter

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        presenter =
            PreviousMatchPresenter(view, apiRepository, gson, CoroutineContextProviderTest())
    }

    @Test
    fun getPrevMatch() {
        val prevMatch: MutableList<Events> = mutableListOf()
        val response = EventsResponse(prevMatch)
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

            presenter.getPrevMatch(leagueId)

            Mockito.verify(view).showPrevMatch(prevMatch)
        }
    }
}
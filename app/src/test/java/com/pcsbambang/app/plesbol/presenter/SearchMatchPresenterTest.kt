package com.pcsbambang.app.plesbol.presenter

import com.google.gson.Gson
import com.pcsbambang.app.plesbol.api.ApiRepository
import com.pcsbambang.app.plesbol.model.Events
import com.pcsbambang.app.plesbol.model.SearchResponse
import com.pcsbambang.app.plesbol.util.CoroutineContextProviderTest
import com.pcsbambang.app.plesbol.view.SearchMatchlView
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class SearchMatchPresenterTest {

    @Mock
    private lateinit var view: SearchMatchlView
    @Mock
    private lateinit var gson: Gson
    @Mock
    private lateinit var apiRepository: ApiRepository
    @Mock
    private lateinit var apiResponse: Deferred<String>

    private lateinit var presenter: SearchMatchPresenter

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        presenter = SearchMatchPresenter(view, apiRepository, gson, CoroutineContextProviderTest())
    }

    @Test
    fun getSearchEvents() {
        val searchMatch: MutableList<Events> = mutableListOf()
        val response = SearchResponse(searchMatch)
        val query = "Liverpool"

        runBlocking {
            Mockito.`when`(apiRepository.doRequest(ArgumentMatchers.anyString()))
                .thenReturn(apiResponse)

            Mockito.`when`(apiResponse.await()).thenReturn("")

            Mockito.`when`(
                gson.fromJson(
                    "",
                    SearchResponse::class.java
                )
            ).thenReturn(response)

            presenter.getSearchEvents(query)

            Mockito.verify(view).showSearchMatch(searchMatch)
        }
    }
}
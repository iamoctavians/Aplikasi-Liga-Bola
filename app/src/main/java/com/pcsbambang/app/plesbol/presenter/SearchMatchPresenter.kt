package com.pcsbambang.app.plesbol.presenter

import android.util.Log
import com.google.gson.Gson
import com.pcsbambang.app.plesbol.api.ApiRepository
import com.pcsbambang.app.plesbol.api.TheSportDBApi
import com.pcsbambang.app.plesbol.model.SearchResponse
import com.pcsbambang.app.plesbol.util.CoroutineContextProvider
import com.pcsbambang.app.plesbol.view.SearchMatchlView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SearchMatchPresenter(
    private val view: SearchMatchlView,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) {
    fun getSearchEvents(query: String?) {
        GlobalScope.launch(context.main) {
            val data = gson.fromJson(
                apiRepository
                    .doRequest(TheSportDBApi.getSearchEvents(query)).await(),
                SearchResponse::class.java
            )

            try {
                view.showSearchMatch(data.event.filter { it.sport == "Soccer" })
                Log.d("tag", "responsennya ${data.event.filter { it.sport == "Soccer" }}")
            } catch (e: NullPointerException) {
                Log.d("tag", "null")
            }
        }
    }
}
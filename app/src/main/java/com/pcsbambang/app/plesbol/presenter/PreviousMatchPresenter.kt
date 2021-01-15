package com.pcsbambang.app.plesbol.presenter

import android.util.Log
import com.google.gson.Gson
import com.pcsbambang.app.plesbol.api.ApiRepository
import com.pcsbambang.app.plesbol.api.TheSportDBApi
import com.pcsbambang.app.plesbol.model.EventsResponse
import com.pcsbambang.app.plesbol.util.CoroutineContextProvider
import com.pcsbambang.app.plesbol.view.PreviousMatchlView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PreviousMatchPresenter(
    private val view: PreviousMatchlView,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) {
    fun getPrevMatch(leagueId: String?) {
        GlobalScope.launch(context.main) {
            val data = gson.fromJson(
                apiRepository
                    .doRequest(TheSportDBApi.getPrevEvents(leagueId)).await(),
                EventsResponse::class.java
            )

            if (data.events != null) {
                view.showPrevMatch(data.events)
                Log.d("tag", "responsennya ${data.events}")
            } else {
                Log.d("tag", "responsennya ${data.events}")
            }
        }
    }
}
package com.pcsbambang.app.plesbol.presenter

import android.util.Log
import com.google.gson.Gson
import com.pcsbambang.app.plesbol.api.ApiRepository
import com.pcsbambang.app.plesbol.api.TheSportDBApi
import com.pcsbambang.app.plesbol.model.EventsResponse
import com.pcsbambang.app.plesbol.util.CoroutineContextProvider
import com.pcsbambang.app.plesbol.view.NextMatchlView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class NextMatchPresenter(
    private val view: NextMatchlView,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) {
    fun getNextMatch(leagueId: String?) {
        GlobalScope.launch(context.main) {
            val data = gson.fromJson(
                apiRepository
                    .doRequest(TheSportDBApi.getNextEvents(leagueId)).await(),
                EventsResponse::class.java
            )

            if (data.events != null) {
                view.showNextMatch(data.events)
                Log.d("tag", "responsennya ${data.events}")
            } else {
                Log.d("tag", "responsennya ${data.events}")
            }
        }
    }
}
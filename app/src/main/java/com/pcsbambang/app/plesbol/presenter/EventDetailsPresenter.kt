package com.pcsbambang.app.plesbol.presenter

import android.util.Log
import com.google.gson.Gson
import com.pcsbambang.app.plesbol.api.ApiRepository
import com.pcsbambang.app.plesbol.api.TheSportDBApi
import com.pcsbambang.app.plesbol.model.EventDetailsResponse
import com.pcsbambang.app.plesbol.model.TeamDetailsResponse
import com.pcsbambang.app.plesbol.util.CoroutineContextProvider
import com.pcsbambang.app.plesbol.view.EventDetailsView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class EventDetailsPresenter(
    private val view: EventDetailsView,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) {
    fun getEventDetails(eventId: String?, homeTeamId: String?, awayTeamId: String?) {
        view.showLoading()
        GlobalScope.launch(context.main) {
            val data = gson.fromJson(
                apiRepository
                    .doRequest(TheSportDBApi.getEventDetails(eventId)).await(),
                EventDetailsResponse::class.java
            )

            val home = gson.fromJson(
                apiRepository
                    .doRequest(TheSportDBApi.getTeamDetails(homeTeamId)).await(),
                TeamDetailsResponse::class.java
            )

            val away = gson.fromJson(
                apiRepository
                    .doRequest(TheSportDBApi.getTeamDetails(awayTeamId)).await(),
                TeamDetailsResponse::class.java
            )

            view.hideLoading()
            view.showEVentDetails(data.events, home.teams, away.teams)
            Log.d(
                "tag",
                "responsennya ${data.events}/n home ${home.teams}/n home ${away.teams}"
            )
        }
    }
}
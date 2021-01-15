package com.pcsbambang.app.plesbol.presenter

import android.util.Log
import com.google.gson.Gson
import com.pcsbambang.app.plesbol.api.ApiRepository
import com.pcsbambang.app.plesbol.api.TheSportDBApi
import com.pcsbambang.app.plesbol.model.StandingsResponse
import com.pcsbambang.app.plesbol.util.CoroutineContextProvider
import com.pcsbambang.app.plesbol.view.StandingsView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class StandingsPresenter(
    private val view: StandingsView,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) {

    fun getStandingList(leagueId: String?) {
        view.showLoading()

        GlobalScope.launch(context.main) {
            val data = gson.fromJson(
                apiRepository
                    .doRequest(TheSportDBApi.getStandings(leagueId)).await(),
                StandingsResponse::class.java
            )

            if (data.table != null) {
                view.hideLoading()
                view.showStandings(data.table)
                Log.d("tag", "responsennya ${data.table}")
            } else {
                view.hideLoading()
                Log.d("tag", "responsennya ${data.table}")
            }

        }
    }

}
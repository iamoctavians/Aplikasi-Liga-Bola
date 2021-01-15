package com.pcsbambang.app.plesbol.presenter

import android.util.Log
import com.google.gson.Gson
import com.pcsbambang.app.plesbol.api.ApiRepository
import com.pcsbambang.app.plesbol.api.TheSportDBApi
import com.pcsbambang.app.plesbol.model.LeagueDetailResponse
import com.pcsbambang.app.plesbol.util.CoroutineContextProvider
import com.pcsbambang.app.plesbol.view.LeagueDetailView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LeagueDetailPresenter(
    private val view: LeagueDetailView,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) {
    fun getLeagueDetail(leagueId: String?) {
        view.showLoading()

        GlobalScope.launch(context.main) {
            val data = gson.fromJson(
                apiRepository
                    .doRequest(TheSportDBApi.getLeagueDetails(leagueId)).await(),
                LeagueDetailResponse::class.java
            )

            view.hideLoading()
            view.showLeagueDetail(data.leagues)
            Log.d("tag", "responsennya ${data.leagues}")
        }
    }
}
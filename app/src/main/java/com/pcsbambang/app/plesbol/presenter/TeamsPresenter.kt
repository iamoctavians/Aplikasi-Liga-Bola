package com.pcsbambang.app.plesbol.presenter

import android.util.Log
import com.dicoding.picodiploma.myfootballclub.TeamResponse
import com.google.gson.Gson
import com.pcsbambang.app.plesbol.api.ApiRepository
import com.pcsbambang.app.plesbol.api.TheSportDBApi
import com.pcsbambang.app.plesbol.util.CoroutineContextProvider
import com.pcsbambang.app.plesbol.view.TeamsView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TeamsPresenter(
    private val view: TeamsView,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) {

    fun getTeamList(leagueId: String?) {
        view.showLoading()

        GlobalScope.launch(context.main) {
            val data = gson.fromJson(
                apiRepository
                    .doRequest(TheSportDBApi.getTeams(leagueId)).await(),
                TeamResponse::class.java
            )

            if (data.teams != null) {
                view.hideLoading()
                view.showTeamList(data.teams)
                Log.d("tag", "responsennya ${data.teams}")
            } else {
                view.hideLoading()
                Log.d("tag", "responsennya ${data.teams}")
            }

        }
    }

    fun getSearchTeams(query: String?) {
        view.showLoading()

        GlobalScope.launch(context.main) {
            val data = gson.fromJson(
                apiRepository
                    .doRequest(TheSportDBApi.getSearchTeams(query)).await(),
                TeamResponse::class.java
            )

            try {
                view.showTeamList(data.teams.filter { it.sport == "Soccer" })
                Log.d("tag", "responsennya ${data.teams.filter { it.sport == "Soccer" }}")
            } catch (e: NullPointerException) {
                Log.d("tag", "null")
            }
        }
    }
}
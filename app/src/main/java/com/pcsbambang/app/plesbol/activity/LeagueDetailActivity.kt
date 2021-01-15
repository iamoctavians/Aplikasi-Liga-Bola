package com.pcsbambang.app.plesbol.activity

import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.pcsbambang.app.plesbol.R
import com.pcsbambang.app.plesbol.adapter.SectionsPagerAdapter
import com.pcsbambang.app.plesbol.api.ApiRepository
import com.pcsbambang.app.plesbol.model.LeagueDetail
import com.pcsbambang.app.plesbol.preference.MyPreference
import com.pcsbambang.app.plesbol.presenter.LeagueDetailPresenter
import com.pcsbambang.app.plesbol.util.invisible
import com.pcsbambang.app.plesbol.util.visible
import com.pcsbambang.app.plesbol.view.LeagueDetailView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_league_detail.*

class LeagueDetailActivity : AppCompatActivity(), LeagueDetailView {

    companion object {
        const val EXTRA_LEAGUE_ID = "extra_league_id"
    }

    private lateinit var presenter: LeagueDetailPresenter
    private lateinit var progressBar: ProgressBar
    private lateinit var preference: MyPreference

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_league_detail)

        supportActionBar?.title = "League Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val leagueId = intent.getStringExtra(EXTRA_LEAGUE_ID)
        Log.d("tag", "id liga = ${leagueId}")

        progressBar = findViewById(R.id.progress_bar)

        val request = ApiRepository()
        val gson = Gson()
        presenter = LeagueDetailPresenter(this, request, gson)
        presenter.getLeagueDetail(leagueId)

        preference = MyPreference(this)
        preference.setLeagueId(leagueId)

        val sectionPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        view_pager.adapter = sectionPagerAdapter
        tabs.setupWithViewPager(view_pager)

        supportActionBar?.elevation = 0f
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showLeagueDetail(leagueDetail: List<LeagueDetail>) {
        Picasso.get().load(leagueDetail[0].leagueBadge).into(league_badge)
        Picasso.get().load(leagueDetail[0].leagueFanart).into(league_fanart)
        league_name.text = leagueDetail[0].leagueName
        league_est.text = "Est. ${leagueDetail[0].leagueFormed}"
        league_description.text = leagueDetail[0].leagueDescription
    }

}

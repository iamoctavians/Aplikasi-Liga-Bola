package com.pcsbambang.app.plesbol.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.pcsbambang.app.plesbol.R
import com.pcsbambang.app.plesbol.activity.EventDetailsActivity
import com.pcsbambang.app.plesbol.adapter.MatchAdapter
import com.pcsbambang.app.plesbol.api.ApiRepository
import com.pcsbambang.app.plesbol.model.Events
import com.pcsbambang.app.plesbol.preference.MyPreference
import com.pcsbambang.app.plesbol.presenter.NextMatchPresenter
import com.pcsbambang.app.plesbol.util.invisible
import com.pcsbambang.app.plesbol.util.visible
import com.pcsbambang.app.plesbol.view.NextMatchlView
import org.jetbrains.anko.support.v4.startActivity

class NextMatchFragment : Fragment(), NextMatchlView {

    private var nextMatch: MutableList<Events> = mutableListOf()
    private lateinit var presenter: NextMatchPresenter
    private lateinit var adapter: MatchAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var rvNextMatch: RecyclerView
    private lateinit var preference: MyPreference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_next_match, container, false)

        progressBar = view.findViewById(R.id.progress_bar_next)

        preference = MyPreference(requireActivity())
        rvNextMatch = view.findViewById(R.id.rv_next_match)

        adapter = MatchAdapter(nextMatch) {
            startActivity<EventDetailsActivity>(
                EventDetailsActivity.EXTRA_EVENT_ID to it.eventId,
                EventDetailsActivity.EXTRA_HOME_TEAM_ID to it.homeTeamId,
                EventDetailsActivity.EXTRA_AWAY_TEAM_ID to it.awayTeamId
            )
        }
        rvNextMatch.adapter = adapter
        rvNextMatch.setHasFixedSize(true)
        rvNextMatch.layoutManager = LinearLayoutManager(this.activity)

        val request = ApiRepository()
        val gson = Gson()
        presenter = NextMatchPresenter(this, request, gson)
        presenter.getNextMatch(preference.getLeagueId())

        return view
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showNextMatch(data: List<Events>) {
        nextMatch.clear()
        nextMatch.addAll(data)
        adapter.notifyDataSetChanged()
    }

}

package com.pcsbambang.app.plesbol.fragment

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.pcsbambang.app.plesbol.R
import com.pcsbambang.app.plesbol.activity.EventDetailsActivity
import com.pcsbambang.app.plesbol.activity.LeagueDetailActivity
import com.pcsbambang.app.plesbol.adapter.LeagueAdapter
import com.pcsbambang.app.plesbol.adapter.MatchAdapter
import com.pcsbambang.app.plesbol.api.ApiRepository
import com.pcsbambang.app.plesbol.model.Events
import com.pcsbambang.app.plesbol.model.LeagueItems
import com.pcsbambang.app.plesbol.presenter.SearchMatchPresenter
import com.pcsbambang.app.plesbol.view.SearchMatchlView
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.startActivity

class LeaguesFragment : Fragment(), AnkoComponent<Context>, SearchMatchlView {

    private var leagueItems: MutableList<LeagueItems> = mutableListOf()
    private var searchMatch: MutableList<Events> = mutableListOf()
    private lateinit var presenter: SearchMatchPresenter
    private lateinit var listLeague: RecyclerView

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setHasOptionsMenu(true)
        showLeague()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return createView(AnkoContext.Companion.create(requireContext()))
    }

    override fun createView(ui: AnkoContext<Context>): View = with(ui) {
        linearLayout {
            lparams(width = matchParent, height = wrapContent)
            orientation = LinearLayout.VERTICAL
            padding = dip(8)

            relativeLayout {
                lparams(width = matchParent, height = wrapContent)

                listLeague = recyclerView {
                    layoutManager = LinearLayoutManager(context)
                    adapter =
                        LeagueAdapter(leagueItems) {
                            startActivity<LeagueDetailActivity>(
                                LeagueDetailActivity.EXTRA_LEAGUE_ID to it.id
                            )
                        }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.options_menu, menu)

        val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.search)?.actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                listLeague = requireActivity().recyclerView {
                    layoutManager = LinearLayoutManager(context)
                    adapter =
                        MatchAdapter(searchMatch) {
                            startActivity<EventDetailsActivity>(
                                EventDetailsActivity.EXTRA_EVENT_ID to it.eventId,
                                EventDetailsActivity.EXTRA_HOME_TEAM_ID to it.homeTeamId,
                                EventDetailsActivity.EXTRA_AWAY_TEAM_ID to it.awayTeamId
                            )
                        }
                }

                val request = ApiRepository()
                val gson = Gson()
                presenter = SearchMatchPresenter(this@LeaguesFragment, request, gson)
                presenter.getSearchEvents(query)

                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
    }

    private fun showLeague() {
        val id = resources.getStringArray(R.array.league_id)
        val name = resources.getStringArray(R.array.league_name)
        val description = resources.getStringArray(R.array.description)
        val badge = resources.obtainTypedArray(R.array.league_badge)
        leagueItems.clear()
        for (i in name.indices) {
            leagueItems.add(
                LeagueItems(
                    id[i],
                    name[i],
                    description[i],
                    badge.getResourceId(i, 0)
                )
            )
        }

        badge.recycle()
    }

    override fun showSearchMatch(data: List<Events>) {
        searchMatch.clear()
        searchMatch.addAll(data)
    }

}

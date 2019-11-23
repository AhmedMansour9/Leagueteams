package com.leagueteams.Activites

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.leagueteams.Adapter.Favourit_Adapter
import com.leagueteams.Adapter.Teams_Adapter
import com.leagueteams.Database.App_Database
import com.leagueteams.Model.Teams_Response
import com.leagueteams.R
import com.leagueteams.View.Favourit_View
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.activityUiThread
import org.jetbrains.anko.doAsync
import com.leagueteams.Events
import com.leagueteams.GlobalBus


class FavouritList : AppCompatActivity() , Favourit_View {
    internal var db: App_Database? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourit_list)
        initialize()
        fetchDataoffline()
    }

    private fun initialize(){
        db = App_Database.getInstance(applicationContext)

    }

    fun fetchDataoffline(){
        doAsync {
            val teams = db?.TeamsDao()?.getAllAvouritTeams()
            activityUiThread {
                refreshUIWith(teams!!)
            }
        }
    }

    private fun refreshUIWith(teamss: List<Teams_Response.Team>){
        val listAdapter =
            Favourit_Adapter(applicationContext, teamss)
        Recycle_Teams.layoutManager = LinearLayoutManager(
            this.applicationContext,
            LinearLayoutManager.VERTICAL,
            false
        )
        listAdapter.onClick(this)
        Recycle_Teams.adapter=listAdapter
    }

    override fun like(teamid:Int,like:Int) {
        doAsync {
            db!!.TeamsDao().update(teamid, like)
            val teams = db?.TeamsDao()?.getAllAvouritTeams()
            activityUiThread {
                refreshUIWith(teams!!)
            }
            val activityMessageEvent =
                Events.ActivityActivityMessage("true")

            GlobalBus.getBus()!!.post(activityMessageEvent)

        }

    }

}

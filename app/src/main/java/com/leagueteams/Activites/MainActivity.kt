package com.leagueteams.Activites

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.leagueteams.Adapter.Teams_Adapter
import com.leagueteams.Database.App_Database
import com.leagueteams.Model.Teams_Response
import com.leagueteams.NetworkState
import com.leagueteams.R
import com.leagueteams.View.Favourit_View
import com.leagueteams.ViewModel.Teams_ViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.activityUiThread
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.longToast
import com.leagueteams.GlobalBus
import android.widget.Toast
import android.widget.TextView
import org.greenrobot.eventbus.Subscribe
import com.leagueteams.Events
import org.greenrobot.eventbus.EventBus




class MainActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener , Favourit_View {

    internal var db:App_Database? = null
     var CheckNetwork= NetworkState()
    var checkid:Boolean=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialize()
        SwipRefresh()
        openFavouritList()


    }
    fun openFavouritList(){
        T_Favourit.setOnClickListener(){
            val intent = Intent(this, FavouritList::class.java)
            startActivity(intent)
        }

    }

    fun fetchDataoffline(){
        doAsync {
            val teams = db?.TeamsDao()?.getAllTeams()
            activityUiThread {
                refreshUIWith(teams!!)
            }
        }
    }

    fun SwipRefresh(){
        SwipTeams.setOnRefreshListener(this)
        SwipTeams.isRefreshing=true
        SwipTeams.setColorSchemeResources(
            R.color.colorPrimary,
            android.R.color.holo_green_dark,
            android.R.color.holo_orange_dark,
            android.R.color.holo_blue_dark
        )
        SwipTeams.post(Runnable {
            if(CheckNetwork.verifyAvailableNetwork(applicationContext )){
                getAllOTeams()
            }else {
                fetchDataoffline()
            }


        })
    }

    fun getAllOTeams(){
        val allteams = ViewModelProvider.NewInstanceFactory().create(Teams_ViewModel::class.java)
        this.applicationContext?.let {
            allteams.getAllTeams(it).observe(this, Observer<Teams_Response> { teamsmodel ->
                SwipTeams.isRefreshing=false
                if(teamsmodel!=null) {
                    saveDataTeams(teamsmodel.teams)
                }

            })
        }
    }

    private fun refreshUIWith(teamss: List<Teams_Response.Team>){
        SwipTeams.isRefreshing=false
        val listAdapter =
            Teams_Adapter(applicationContext, teamss)
            Recycle_Teams.layoutManager = LinearLayoutManager(
            this.applicationContext,
            LinearLayoutManager.VERTICAL,
            false
        )
        listAdapter.onClick(this)
        Recycle_Teams.adapter=listAdapter
    }

    override fun onRefresh() {
        if(CheckNetwork.verifyAvailableNetwork(applicationContext )){
            getAllOTeams()
        }else {
            fetchDataoffline()
        }
    }

    private fun initialize(){
        db = App_Database.getInstance(applicationContext)

    }

    private fun saveDataTeams(Teams: List<Teams_Response.Team>) {

        doAsync {
            var items = ArrayList<Teams_Response.Team>()

            for (team in Teams) {
               var checked:Boolean= Checkid_exist(team.id.toString())
                if(checked) {
                    var item = Teams_Response.Team()
                    item.clubColors = team.clubColors
                    item.id = team.id
                    item.name = team.name
                    item.shortName = team.shortName
                    item.venue = team.venue
                    item.website = team.website
                    items.add(item)
                }
            }
            db?.TeamsDao()?.insert(items)

            val teams = db?.TeamsDao()?.getAllTeams()
            activityUiThread {
                refreshUIWith(teams!!)
            }
        }

    }

    fun Checkid_exist(id:String):Boolean {
        db!!.runInTransaction {
            val id = db!!.TeamsDao().getItemId(id.toInt())
         if(id==0){
             checkid=true
         }else {
             checkid=false
         }
        }
        return checkid
    }

    override fun like(teamid:Int,like:Int) {
        doAsync {
            db!!.TeamsDao().update(teamid, like)
        }

    }


    override fun onStart() {
        super.onStart()

        if (!EventBus.getDefault().isRegistered(this)) { EventBus.getDefault().register(this); }
    }


    @Subscribe
    fun getMessage(fragmentActivityMessage: Events.ActivityActivityMessage) {
       if(fragmentActivityMessage.message.equals("true")){
           runOnUiThread {
               // Stuff that updates the UI
               SwipRefresh()
           }
       }

    }


}

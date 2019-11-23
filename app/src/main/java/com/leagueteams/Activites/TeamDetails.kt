package com.leagueteams.Activites

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.leagueteams.Adapter.TeamsDetails_Adapter
import com.leagueteams.Database.App_Database
import com.leagueteams.Model.TeamDetails_Response
import com.leagueteams.Model.Teams_Response
import com.leagueteams.NetworkState
import com.leagueteams.R
import com.leagueteams.ViewModel.TeamDetails_ViewModel
import kotlinx.android.synthetic.main.activity_team_details.*
import org.jetbrains.anko.activityUiThread
import org.jetbrains.anko.doAsync

class TeamDetails : AppCompatActivity() , SwipeRefreshLayout.OnRefreshListener{
    var checkid:Boolean=false
    internal var db: App_Database? = null
    lateinit var detailsTeam:Teams_Response.Team
    lateinit var id:String
    var CheckNetwork= NetworkState()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_details)
        initialize()
        getData()
        SwipRefresh()


    }
    private fun initialize(){
        db = App_Database.getInstance(applicationContext)
    }

    private fun getData() {
        detailsTeam=intent.getParcelableExtra("details")
        id=detailsTeam.id.toString()
        T_Title.text=detailsTeam.name
    }


    fun SwipRefresh(){
        SwipDetailsTeams.setOnRefreshListener(this)
        SwipDetailsTeams.isRefreshing=true
        SwipDetailsTeams.setColorSchemeResources(
            R.color.colorPrimary,
            android.R.color.holo_green_dark,
            android.R.color.holo_orange_dark,
            android.R.color.holo_blue_dark
        )
        SwipDetailsTeams.post(Runnable {
            if(CheckNetwork.verifyAvailableNetwork(applicationContext)){
                getDetailsTeams()
            }else {
                fetchDataoffline()
            }

        })
    }

    fun getDetailsTeams(){
        val allteams = ViewModelProvider.NewInstanceFactory().create(TeamDetails_ViewModel::class.java)
        this.applicationContext?.let {
            allteams.getDetailsTeams(id,it).observe(this, Observer<TeamDetails_Response> { teamsmodel ->
                SwipDetailsTeams.isRefreshing=false
                if(teamsmodel!=null) {
                  saveData(teamsmodel.squad)
                }

            })
        }
    }

    override fun onRefresh() {
        getDetailsTeams()
    }

    private fun saveData(Teams: List<TeamDetails_Response.Squad>){

        doAsync {
            var items = ArrayList<TeamDetails_Response.Squad>()

                for (team in Teams){
                 var checked:Boolean= Checkid_exist(team.id.toString())
                 if(checked) {
                 var item =TeamDetails_Response.Squad()
                item.forginid = id.toInt()
                item.id = team.id
                item.name  = team.name
                item.nationality    = team.nationality
                item.position = team.position
                item.shirtNumber = team.shirtNumber
                items.add(item)
                }
            }
            db!!.TeamDetailsDao()!!.insert(items)

            val teams = db!!.TeamDetailsDao()!!.getAllTeams(id)
            activityUiThread {
                refreshUIWith(teams!!)
            }
        }

    }

    private fun refreshUIWith(teamss: List<TeamDetails_Response.Squad>){
        SwipDetailsTeams.isRefreshing=false
        val listAdapter =
            TeamsDetails_Adapter(applicationContext, teamss)
        Recycle_DetailsTeams.layoutManager = LinearLayoutManager(this.applicationContext,
            LinearLayoutManager.VERTICAL, false
        )
        Recycle_DetailsTeams.setAdapter(listAdapter)
    }

    fun fetchDataoffline(){

        doAsync {
            val teamsr = db!!.TeamDetailsDao()?.getAllTeams(id)
            activityUiThread {
                refreshUIWith(teamsr!!)
            }
        }
    }

    fun Checkid_exist(id:String):Boolean {
        db!!.runInTransaction {
            val id = db!!.TeamDetailsDao().getItemId(id.toInt())
            if(id==0){
                checkid=true
            }else {
                checkid=false
            }
        }
        return checkid
    }

}

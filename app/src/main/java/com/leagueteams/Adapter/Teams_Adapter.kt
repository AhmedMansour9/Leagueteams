package com.leagueteams.Adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.leagueteams.Model.Teams_Response
import com.leagueteams.R
import com.leagueteams.Activites.TeamDetails
import com.leagueteams.View.DetailsTeam_View
import kotlinx.android.synthetic.main.row_teams.view.*

class Teams_Adapter (context: Context, val ListTeams: List<Teams_Response.Team>)
    : RecyclerView.Adapter<Teams_Adapter.ViewHolder>() {
    lateinit var TeamDetails: DetailsTeam_View
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Teams_Adapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.row_teams, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: Teams_Adapter.ViewHolder, position: Int) {
        holder.bindItems(ListTeams.get(position))

    }
    fun onClick(teamDetails: DetailsTeam_View){
        this.TeamDetails=teamDetails
    }

    override fun getItemCount(): Int {
        return ListTeams.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val context: Context = itemView.context

        fun bindItems(dataModel: Teams_Response.Team) {
            itemView.T_Name.text= String.format(context.getString(R.string.teamname)+" "+ dataModel.name);
            itemView.T_Website.text=String.format(context.getString(R.string.teawebsite)+" "+  dataModel.website);
            itemView.T_colors.text=String.format(context.getString(R.string.clubcolors)+" "+  dataModel.clubColors);
            itemView.T_Venue.text=String.format(context.getString(R.string.venue)+" "+  dataModel.venue);
            onClickLink(dataModel.website!!)
            openDetailsTeam(dataModel)
        }

        fun onClickLink(Link:String){
            itemView.T_Website.setOnClickListener(){
                val openURL = Intent(Intent.ACTION_VIEW)
                openURL.data = Uri.parse(Link)
                context.startActivity(openURL)

            }
        }

        fun openDetailsTeam(dataModel: Teams_Response.Team){
            itemView.setOnClickListener(){
                val intent = Intent(context, TeamDetails::class.java)
                intent.putExtra("details",dataModel)
                context.startActivity(intent)
            }

        }


    }

    }

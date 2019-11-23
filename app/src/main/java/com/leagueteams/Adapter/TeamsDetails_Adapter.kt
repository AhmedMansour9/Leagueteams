package com.leagueteams.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.leagueteams.Model.TeamDetails_Response
import com.leagueteams.R
import com.leagueteams.View.DetailsTeam_View
import kotlinx.android.synthetic.main.row_detailsteam.view.*
import kotlinx.android.synthetic.main.row_teams.view.T_Name

class TeamsDetails_Adapter (context: Context, val ListTeams: List<TeamDetails_Response.Squad>)
    : RecyclerView.Adapter<TeamsDetails_Adapter.ViewHolder>() {
    lateinit var TeamDetails: DetailsTeam_View
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamsDetails_Adapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.row_detailsteam, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: TeamsDetails_Adapter.ViewHolder, position: Int) {
        holder.bindItems(ListTeams.get(position))

    }


    override fun getItemCount(): Int {
        return ListTeams.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val context: Context = itemView.context

        fun bindItems(dataModel: TeamDetails_Response.Squad) {
            itemView.T_Name.text= String.format(context.getString(R.string.playername)+" "+ dataModel.name);
            itemView.T_postion.text=String.format(context.getString(R.string.postion)+" "+  dataModel.position);
            itemView.T_nationality.text=String.format(context.getString(R.string.nationality)+" "+  dataModel.nationality);
            itemView.T_shirtNumber.text=String.format(context.getString(R.string.shirtNumber)+" "+  dataModel.shirtNumber);
        }



    }

}

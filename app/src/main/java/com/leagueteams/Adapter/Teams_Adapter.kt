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
import com.leagueteams.View.Favourit_View
import kotlinx.android.synthetic.main.row_teams.view.*

class Teams_Adapter (context: Context, val ListTeams: List<Teams_Response.Team>)
    : RecyclerView.Adapter<Teams_Adapter.ViewHolder>() {
    lateinit var Teamid: Favourit_View
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Teams_Adapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.row_teams, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: Teams_Adapter.ViewHolder, position: Int) {
        holder.bindItems(ListTeams.get(position))

        AddToFavourit(holder,ListTeams,position)

    }
    fun onClick(Teamids: Favourit_View){
        this.Teamid=Teamids
    }

    override fun getItemCount(): Int {
        return ListTeams.size
    }

    fun AddToFavourit(holder: ViewHolder,ListTeams: List<Teams_Response.Team>,position:Int){
        holder.itemView.img_favourit.setOnClickListener(){

            if(ListTeams.get(position).islike==0){
                this.Teamid.like(ListTeams.get(position).id,1)
                holder.itemView.img_favourit.setImageResource(R.drawable.icon_favourit)
            }else {
                this.Teamid.like(ListTeams.get(position).id,0)
                holder.itemView.img_favourit.setImageResource(R.drawable.icon_emptyfavourit)
            }

        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val context: Context = itemView.context

        fun bindItems(dataModel: Teams_Response.Team) {
            itemView.T_Name.text= String.format(context.getString(R.string.teamname)+" "+ dataModel.name);
            itemView.T_Website.text=String.format(context.getString(R.string.teawebsite)+" "+  dataModel.website);
            itemView.T_colors.text=String.format(context.getString(R.string.clubcolors)+" "+  dataModel.clubColors);
            itemView.T_Venue.text=String.format(context.getString(R.string.venue)+" "+  dataModel.venue);
            if(dataModel.islike==0){
                itemView.img_favourit.setImageResource(R.drawable.icon_emptyfavourit)
            }else {
                itemView.img_favourit.setImageResource(R.drawable.icon_favourit)
            }
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

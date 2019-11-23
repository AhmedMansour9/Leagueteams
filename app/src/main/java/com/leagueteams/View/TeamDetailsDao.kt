package com.leagueteams.View

import androidx.room.*
import com.leagueteams.Model.TeamDetails_Response
import com.leagueteams.Model.Teams_Response

@Dao
interface TeamDetailsDao {

    @Insert
    fun insert(team: ArrayList<TeamDetails_Response.Squad>)

    @Update
    fun update(team: TeamDetails_Response.Squad)

    @Delete
    fun delete(team: TeamDetails_Response.Squad)

    @Query("DELETE FROM Team_Details")
    fun deleteAllTeams()

    @Query("SELECT * FROM Team_Details WHERE forginid  = :id")
    fun getAllTeams(id:String): List<TeamDetails_Response.Squad>

    @Query("SELECT id FROM Team_Details WHERE id = :id LIMIT 1")
    fun getItemId(id: Int): Int


}
package com.leagueteams.View

import androidx.lifecycle.LiveData
import androidx.room.*
import com.leagueteams.Model.Teams_Response

@Dao
interface TeamsDao {


    @Insert
    fun insert(team: ArrayList<Teams_Response.Team>)

    @Query ("UPDATE Team  SET is_liked = :id WHERE id = :Teamid")
    fun update(Teamid:Int,id:Int)

    @Delete
    fun delete(team: Teams_Response.Team)

    @Query("DELETE FROM Team")
    fun deleteAllTeams()

    @Query("SELECT * FROM Team")
    fun getAllTeams(): List<Teams_Response.Team>

    @Query("SELECT * FROM Team WHERE is_liked=1")
    fun getAllAvouritTeams(): List<Teams_Response.Team>


    @Query("SELECT id FROM Team WHERE id = :id LIMIT 1")
    fun getItemId(id: Int): Int

}
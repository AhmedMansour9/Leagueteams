package com.leagueteams.View

import androidx.lifecycle.LiveData
import androidx.room.*
import com.leagueteams.Model.Teams_Response

@Dao
interface TeamsDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(team: ArrayList<Teams_Response.Team>)

    @Update
    fun update(team: Teams_Response.Team)

    @Delete
    fun delete(team: Teams_Response.Team)

    @Query("DELETE FROM Team")
    fun deleteAllTeams()

    @Query("SELECT * FROM Team")
    fun getAllTeams(): List<Teams_Response.Team>

    @Query("SELECT id FROM Team WHERE id = :id LIMIT 1")
    fun getItemId(id: Int): Int

}
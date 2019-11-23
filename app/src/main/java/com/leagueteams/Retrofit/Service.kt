package com.leagueteams.Retrofit

import com.leagueteams.Model.TeamDetails_Response
import com.leagueteams.Model.Teams_Response
import retrofit2.Call
import retrofit2.http.*

interface Service {
    var auth:String

    @Headers("Content-Type: application/json;charset=UTF-8")
    @GET("v2/competitions/{id}/teams")
    fun getTeams(@Path(value = "id", encoded = true)id:String,@Header("X-Auth-Token")auth:String): Call<Teams_Response>


    @Headers("Content-Type: application/json;charset=UTF-8")
    @GET("v2/teams/{id}")
    fun getDetailsTeams(@Path(value = "id", encoded = true)id:String,@Header("X-Auth-Token")auth:String): Call<TeamDetails_Response>


}
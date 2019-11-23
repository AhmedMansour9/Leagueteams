package com.leagueteams.ViewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.leagueteams.Model.TeamDetails_Response
import com.leagueteams.Model.Teams_Response
import com.leagueteams.Retrofit.ApiClient
import com.leagueteams.Retrofit.Service
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class TeamDetails_ViewModel : ViewModel() {


    var listTeamDetailsMutableLiveData: MutableLiveData<TeamDetails_Response>? = null
    private lateinit var context: Context


    fun getDetailsTeams( id:String,context: Context): LiveData<TeamDetails_Response> {
            listTeamDetailsMutableLiveData = MutableLiveData<TeamDetails_Response>()
            this.context = context
            getDataValues(id)
        return listTeamDetailsMutableLiveData as MutableLiveData<TeamDetails_Response>
    }


    private fun getDataValues(id:String) {
        var service = ApiClient.getClient()?.create(Service::class.java)
        val call = service?.getDetailsTeams(id,"eaad84c508514676b71ab90b1f7b1d34")
        call?.enqueue(object : Callback, retrofit2.Callback<TeamDetails_Response> {
            override fun onResponse(call: Call<TeamDetails_Response>, response: Response<TeamDetails_Response>) {

                if (response.code() == 200) {
                    listTeamDetailsMutableLiveData?.setValue(response.body()!!)

                } else  {
                    listTeamDetailsMutableLiveData?.setValue(null)
                }
            }

            override fun onFailure(call: Call<TeamDetails_Response>, t: Throwable) {
                listTeamDetailsMutableLiveData?.setValue(null)

            }
        })
    }


}
package com.leagueteams.ViewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.leagueteams.Model.Teams_Response
import com.leagueteams.Retrofit.ApiClient
import com.leagueteams.Retrofit.Service
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class Teams_ViewModel : ViewModel() {


    var listTeamssMutableLiveData: MutableLiveData<Teams_Response>? = null
    private lateinit var context: Context


     fun getAllTeams( context: Context): LiveData<Teams_Response> {
        if(listTeamssMutableLiveData==null) {
            listTeamssMutableLiveData = MutableLiveData<Teams_Response>()
            this.context = context
            getDataValues()
        }
        return listTeamssMutableLiveData as MutableLiveData<Teams_Response>
    }


    private fun getDataValues() {
        var service = ApiClient.getClient()?.create(Service::class.java)
        val call = service?.getTeams("2019","eaad84c508514676b71ab90b1f7b1d34")
        call?.enqueue(object : Callback, retrofit2.Callback<Teams_Response> {
            override fun onResponse(call: Call<Teams_Response>, response: Response<Teams_Response>) {

                if (response.code() == 200) {
                    listTeamssMutableLiveData?.setValue(response.body()!!)

                } else  {
                    listTeamssMutableLiveData?.setValue(null)
                }
            }

            override fun onFailure(call: Call<Teams_Response>, t: Throwable) {
                listTeamssMutableLiveData?.setValue(null)

            }
        })
    }


}
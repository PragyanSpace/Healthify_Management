package com.pragyanSpace.pathcare_management.management.home.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.pragyanSpace.pathcare_management.RetrofitUtilClass
import com.pragyanSpace.pathcare_management.appointmentDetail.model.AppointmentResponseData2
import com.pragyanSpace.pathcare_management.appointmentDetail.model.ResposeReq
import com.pragyanSpace.pathcare_management.management.home.model.AppointmentResponseData
import com.pragyanSpace.pathcare_management.management.home.network.HomeNetwork
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeRepository {
    val showProgress = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()
    val appointmentResponseModel = MutableLiveData<AppointmentResponseData>()

    fun getAppointments(token: String, hosId: String) {

        val client = RetrofitUtilClass.getRetrofit()?.create(HomeNetwork::class.java)

        val url="api/v1/hospital/all-appointment/$hosId"
        val call = client?.callAppointmentApi(token, url)

        call?.enqueue(object : Callback<AppointmentResponseData?> {
            override fun onResponse(
                call: Call<AppointmentResponseData?>,
                response: Response<AppointmentResponseData?>
            ) {
                showProgress.postValue(false)
                val body = response.body()
                if (response.isSuccessful) {
                    body?.let {
                        appointmentResponseModel.postValue(it)
                    }
                }
            }

            override fun onFailure(call: Call<AppointmentResponseData?>, t: Throwable) {
                showProgress.postValue(false)
                errorMessage.postValue("Server error please try after sometime")
                Log.i("ErrorCheck", "onFailure: ${t.message} ")
            }
        })


    }
}
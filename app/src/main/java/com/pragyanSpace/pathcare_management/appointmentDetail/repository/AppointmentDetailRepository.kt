package com.pragyanSpace.pathcare_management.appointmentDetail.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.pragyanSpace.pathcare_management.RetrofitUtilClass
import com.pragyanSpace.pathcare_management.appointmentDetail.model.AppointmentResponseData2
import com.pragyanSpace.pathcare_management.appointmentDetail.model.ResposeReq
import com.pragyanSpace.pathcare_management.appointmentDetail.network.AppointmentDetailService
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AppointmentDetailRepository {

    val showProgress = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()
    val appointmentResponseModel2 = MutableLiveData<AppointmentResponseData2>()


    fun postResponse(token: String,id:String, body: ResposeReq) {

        val client = RetrofitUtilClass.getRetrofit()?.create(AppointmentDetailService::class.java)

        val url="api/v1/hospital/resApt/$id"
        val call = client?.callResponse(token, url,body)

        call?.enqueue(object : Callback<AppointmentResponseData2> {
            override fun onResponse(
                call: Call<AppointmentResponseData2?>,
                response: Response<AppointmentResponseData2>
            ) {
                showProgress.postValue(false)
                val body = response.body()
                if (response.isSuccessful) {
                    body?.let {
                        appointmentResponseModel2.postValue(it)
                    }
                } else {
                    val jObjError = JSONObject(response.errorBody()?.string())
                    errorMessage.postValue(jObjError.getString("message"))
                }
            }

            override fun onFailure(call: Call<AppointmentResponseData2>, t: Throwable) {
                showProgress.postValue(false)
                errorMessage.postValue("Server error please try after sometime")
                Log.i("ErrorCheck", "onFailure: ${t.message} ")
            }
        })


    }
}
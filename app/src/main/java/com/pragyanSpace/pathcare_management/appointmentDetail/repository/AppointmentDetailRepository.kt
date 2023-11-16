package com.pragyanSpace.pathcare_management.appointmentDetail.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.pragyanSpace.pathcare_management.RetrofitUtilClass
import com.pragyanSpace.pathcare_management.appointmentDetail.model.AppointmentDetailModel
import com.pragyanSpace.pathcare_management.appointmentDetail.model.AppointmentResponseData2
import com.pragyanSpace.pathcare_management.appointmentDetail.model.CreateAppointmentReqModel
import com.pragyanSpace.pathcare_management.appointmentDetail.model.CreateAppointmentResModel
import com.pragyanSpace.pathcare_management.appointmentDetail.model.DoctorsResponseModel
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
    val doctorResponse = MutableLiveData<DoctorsResponseModel>()
    val appointmentDetailResponse = MutableLiveData<AppointmentDetailModel>()
    val bookAppointmentResponse = MutableLiveData<CreateAppointmentResModel>()


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

    fun getDoctors(token: String) {

        val client = RetrofitUtilClass.getRetrofit()?.create(AppointmentDetailService::class.java)

        val call = client?.callDoctorsApi(token)

        call?.enqueue(object : Callback<DoctorsResponseModel> {
            override fun onResponse(
                call: Call<DoctorsResponseModel?>,
                response: Response<DoctorsResponseModel>
            ) {
                showProgress.postValue(false)
                val body = response.body()
                if (response.isSuccessful) {
                    body?.let {
                        doctorResponse.postValue(it)
                    }
                } else {
                    val jObjError = JSONObject(response.errorBody()?.string())
                    errorMessage.postValue(jObjError.getString("message"))
                }
            }

            override fun onFailure(call: Call<DoctorsResponseModel>, t: Throwable) {
                showProgress.postValue(false)
                errorMessage.postValue("Server error please try after sometime")
                Log.i("ErrorCheck", "onFailure: ${t.message} ")
            }
        })


    }


    fun getAppointmentDetail(token: String,aptId:String) {

        val client = RetrofitUtilClass.getRetrofit()?.create(AppointmentDetailService::class.java)

        val url="api/v1/hospital/aptDetail/$aptId"
        val call = client?.callAppointmentDetailApi(token,url)

        call?.enqueue(object : Callback<AppointmentDetailModel> {
            override fun onResponse(
                call: Call<AppointmentDetailModel?>,
                response: Response<AppointmentDetailModel>
            ) {
                showProgress.postValue(false)
                val body = response.body()
                if (response.isSuccessful) {
                    body?.let {
                        appointmentDetailResponse.postValue(it)
                    }
                } else {
                    val jObjError = JSONObject(response.errorBody()?.string())
                    errorMessage.postValue(jObjError.getString("message"))
                }
            }

            override fun onFailure(call: Call<AppointmentDetailModel>, t: Throwable) {
                showProgress.postValue(false)
                errorMessage.postValue("Server error please try after sometime")
                Log.i("ErrorCheck", "onFailure: ${t.message} ")
            }
        })


    }


    fun createAppointment(token: String, request: CreateAppointmentReqModel) {

        val client = RetrofitUtilClass.getRetrofit()?.create(AppointmentDetailService::class.java)

        val call = client?.callBookAppointment(token,request)

        call?.enqueue(object : Callback<CreateAppointmentResModel> {
            override fun onResponse(
                call: Call<CreateAppointmentResModel?>,
                response: Response<CreateAppointmentResModel>
            ) {
                showProgress.postValue(false)
                val body = response.body()
                if (response.isSuccessful) {
                    body?.let {
                        bookAppointmentResponse.postValue(it)
                    }
                } else {
                    val jObjError = JSONObject(response.errorBody().toString())
                    errorMessage.postValue(jObjError.getString("message"))
                }
            }

            override fun onFailure(call: Call<CreateAppointmentResModel>, t: Throwable) {
                showProgress.postValue(false)
                errorMessage.postValue("Server error please try after sometime")
                Log.i("ErrorCheck", "onFailure: ${t.message} ")
            }
        })



    }

}
package com.pragyanSpace.pathcare_management.appointmentDetail.network

import com.pragyanSpace.pathcare_management.appointmentDetail.model.AppointmentResponseData2
import com.pragyanSpace.pathcare_management.appointmentDetail.model.ResposeReq
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Url

interface AppointmentDetailService {
    @POST
    fun callResponse(
        @Header("Authorization") Authorization: String?,
        @Url url:String,
        @Body responseReq: ResposeReq
    ): Call<AppointmentResponseData2>
}
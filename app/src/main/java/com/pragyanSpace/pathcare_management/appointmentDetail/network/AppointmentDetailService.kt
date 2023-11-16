package com.pragyanSpace.pathcare_management.appointmentDetail.network

import com.pragyanSpace.pathcare_management.appointmentDetail.model.AppointmentDetailModel
import com.pragyanSpace.pathcare_management.appointmentDetail.model.AppointmentResponseData2
import com.pragyanSpace.pathcare_management.appointmentDetail.model.CreateAppointmentReqModel
import com.pragyanSpace.pathcare_management.appointmentDetail.model.CreateAppointmentResModel
import com.pragyanSpace.pathcare_management.appointmentDetail.model.DoctorsResponseModel
import com.pragyanSpace.pathcare_management.appointmentDetail.model.ResposeReq
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.Url

interface AppointmentDetailService {
    @POST
    fun callResponse(
        @Header("Authorization") Authorization: String?,
        @Url url:String,
        @Body responseReq: ResposeReq
    ): Call<AppointmentResponseData2>

    @GET("api/v1/hospital/doctors")
    fun callDoctorsApi(
        @Header("Authorization") Authorization: String?
    ):Call<DoctorsResponseModel>

    @GET
    fun callAppointmentDetailApi(
        @Header("Authorization") Authorization: String?,
        @Url url:String
    ):Call<AppointmentDetailModel>

    @POST("api/v1/hospital/bookApt")
    fun callBookAppointment(
        @Header("Authorization") Authorization: String?,
        @Body request: CreateAppointmentReqModel
    ):Call<CreateAppointmentResModel>
}
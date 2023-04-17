package com.pragyanSpace.pathcare_management.management.home.network

import com.pragyanSpace.pathcare_management.management.home.model.AppointmentResponseData
import retrofit2.Call
import retrofit2.http.*

interface HomeNetwork  {

    @GET
    fun callAppointmentApi(
        @Header("Authorization") Authorization: String?,
        @Url url:String
    ): Call<AppointmentResponseData>

}
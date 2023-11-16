package com.pragyanSpace.pathcare_management.management.appointments.network

import com.pragyanSpace.pathcare_management.management.appointments.model.MyAppointmentsModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface MyAppointmentsNetwork {

        @GET("/api/v1/hospital/acceptedApt")
        fun callMyAppointmentsApi(
            @Header("Authorization") Authorization: String?,
        ): Call<MyAppointmentsModel>
}
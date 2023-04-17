package com.pragyanSpace.pathcare_management.management.qrScanner.network

import com.pragyanSpace.pathcare_management.management.qrScanner.model.UserDataResponseModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Url

interface QRNetwork  {

    @GET
    fun callUserDataApi(
        @Header("Authorization") Authorization: String?,
        @Url url:String
    ): Call<UserDataResponseModel>
}
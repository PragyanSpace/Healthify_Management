package com.pragyanSpace.pathcare_management.register.network

import com.pragyanSpace.pathcare_management.register.model.RegisterRequestModel
import com.pragyanSpace.pathcare_management.register.model.RegisterResponseModel
import retrofit2.Call
import retrofit2.http.*

interface RegisterService
{
    @POST
    fun registerApiCall(
        @Url url: String,
        @Header("Authorization") Authorization: String?,
        @Body registerRequestModel: RegisterRequestModel
    ): Call<RegisterResponseModel>
}

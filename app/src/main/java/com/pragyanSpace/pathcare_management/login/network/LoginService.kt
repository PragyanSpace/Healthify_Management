package com.pragyanSpace.pathcare_management.login.network

import com.pragyanSpace.pathcare_management.login.model.LoginRequestModel
import com.pragyanSpace.pathcare_management.login.model.LoginResponseModel
import retrofit2.Call
import retrofit2.http.*

interface LoginService  {
    @POST
    fun loginApiCall(
        @Url url: String,
        @Header("Authorization") Authorization: String?,
        @Body loginRequestModel: LoginRequestModel
    ): Call<LoginResponseModel>

}

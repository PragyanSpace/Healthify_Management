package com.pragyanSpace.pathcare_management.management.qrScanner.repository

import androidx.lifecycle.MutableLiveData
import com.pragyanSpace.pathcare_management.RetrofitUtilClass
import com.pragyanSpace.pathcare_management.management.qrScanner.model.UserDataResponseModel
import com.pragyanSpace.pathcare_management.management.qrScanner.network.QRNetwork
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class QRRepository() {

    val showProgress = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()
    val userResponseMutableLiveData = MutableLiveData<UserDataResponseModel>()

    fun userDetailApiCall(token: String?, id: String?) {
        showProgress.value = true
        val client = RetrofitUtilClass.getRetrofit()?.create(QRNetwork::class.java)
        val url="api/v1/user/details/$id"
        var call = client?.callUserDataApi(token,url)
        call?.enqueue(object : Callback<UserDataResponseModel?> {
            override fun onResponse(
                call: Call<UserDataResponseModel?>,
                response: Response<UserDataResponseModel?>
            ) {
                showProgress.postValue(false)
                val body = response.body()

                if (response.isSuccessful) {
                    body?.let {
                        userResponseMutableLiveData.postValue(body)
                    }
                } else {

                }
            }

            override fun onFailure(call: Call<UserDataResponseModel?>, t: Throwable) {
                showProgress.postValue(false)
                errorMessage.postValue("Server error please try after sometime")
            }
        })
    }

}
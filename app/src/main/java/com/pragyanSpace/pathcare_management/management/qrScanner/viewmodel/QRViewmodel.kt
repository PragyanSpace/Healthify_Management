package com.pragyanSpace.pathcare_management.management.qrScanner.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.pragyanSpace.pathcare_management.management.qrScanner.model.UserDataResponseModel
import com.pragyanSpace.pathcare_management.management.qrScanner.repository.QRRepository

class QRViewmodel(): ViewModel() {

    private val repository = QRRepository()
    val showProgress: LiveData<Boolean>
    val errorMessage: LiveData<String>
    val userResponseMutableLiveData: LiveData<UserDataResponseModel>


    init {
        this.showProgress = repository.showProgress
        this.errorMessage = repository.errorMessage
        this.userResponseMutableLiveData = repository.userResponseMutableLiveData
    }

    fun callUserApi(token: String?, id: String?) {
        repository.userDetailApiCall(token, id)
    }


}
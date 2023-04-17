package com.pragyanSpace.pathcare_management.register.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.pragyanSpace.pathcare_management.register.model.RegisterRequestModel
import com.pragyanSpace.pathcare_management.register.model.RegisterResponseModel
import com.pragyanSpace.pathcare_management.register.repository.RegisterRepository

class RegisterActivityViewModel(): ViewModel(){

    private val repository= RegisterRepository()
     val showProgress:LiveData<Boolean>
    val errorMessage: LiveData<String>
    val registerResponseMutableLiveData: LiveData<RegisterResponseModel>


    init {
        this.showProgress = repository.showProgress
        this.errorMessage = repository.errorMessage
        this.registerResponseMutableLiveData = repository.registerResponseMutableLiveData
    }

    fun callRegisterApi(token: String?, registerRequestModel: RegisterRequestModel) {
        repository.registerApiCall(token, registerRequestModel)
    }
}
package com.pragyanSpace.pathcare_management.login.ui

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.storage.FirebaseStorage
import com.pragyanSpace.pathcare_management.R
import com.pragyanSpace.pathcare_management.databinding.ActivitySigninBinding
import com.pragyanSpace.pathcare_management.login.model.LoginRequestModel
import com.pragyanSpace.pathcare_management.login.viewmodel.LoginActivityViewModel
import com.pragyanSpace.pathcare_management.management.ManagementActivity
import com.pragyanSpace.pathcare_management.register.AskUserDetailAfterRegister
import com.pragyanSpace.pathcare_management.register.ui.RegisterActivity
import com.pragyanSpace.pathcare_management.utility.AppUrls
import com.pragyanSpace.pathcare_management.utility.BaseUtil
import com.pragyanSpace.pathcare_management.utility.PrefUtil

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivitySigninBinding
    private var viewModel: LoginActivityViewModel? = null
    var textWatchers: TextWatcher? = null
    var id:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signin)
        viewModel = ViewModelProvider(this).get(LoginActivityViewModel::class.java)

        if (PrefUtil(this).sharedPreferences?.getBoolean(PrefUtil.IS_LOGIN, false) == true) {
//            checkIfDpUploaded()
            val intent = Intent(this@LoginActivity, ManagementActivity::class.java)
            startActivity(intent)
            finish()
        }
        observeShowProgress()
        observeErrorMessage()
        observeApiResponse()
        initListener()

    }

    private fun checkIfDpUploaded() {
        val storageRef = FirebaseStorage.getInstance().getReference()
        id=PrefUtil(this).sharedPreferences?.getString("ID","void")
        val fileRef = storageRef.child("hospitals/${id}.jpg")

        fileRef.metadata.addOnSuccessListener {
            val intent = Intent(this@LoginActivity, ManagementActivity::class.java)
            startActivity(intent)
            finish()
        }.addOnFailureListener {
            val intent = Intent(this@LoginActivity, AskUserDetailAfterRegister::class.java)
            intent.putExtra("ID",id)
            startActivity(intent)
            finish()
        }

    }


    private fun initListener() {
        binding?.txtRegister?.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }
        initTextWatcher()
        binding?.loginBtn?.setOnClickListener {
            hideMyKeyboard()
            loginBtnClickFunction()
        }
        binding?.email?.addTextChangedListener(textWatchers)
        binding?.password?.addTextChangedListener(textWatchers)
    }


    private fun initTextWatcher() {
        textWatchers = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(editable: Editable?) {
                if (editable == binding?.email?.editableText)
                    binding?.emailLayout?.error = null
                if (editable == binding?.password?.editableText)
                    binding?.passwordLayout?.error = null
            }

        }
    }

    private fun loginBtnClickFunction() {
        val email = binding?.email?.text?.toString()?.trim()
        val password = binding?.password?.text?.toString()?.trim()
        if (email.isNullOrBlank() || !BaseUtil.isValidEmail(email))
            binding?.emailLayout?.error = "Please Enter Valid Email"
        else if (password.isNullOrBlank())
            binding?.passwordLayout?.error = "Please Enter Password"
        else if (password.length < 6 || password.length > 255)
            binding?.passwordLayout?.error = "Password length must be greater then 6 characters"
        else
        {
            val loginRequestModel = LoginRequestModel(email, password)
            callLoginApi(loginRequestModel)
        }
    }

    private fun callLoginApi(loginRequestModel: LoginRequestModel) {
        val token =
            PrefUtil(this@LoginActivity).sharedPreferences?.getString(PrefUtil.TOKEN, "")
        viewModel?.callLoginApi(token, loginRequestModel)
    }

    private fun observeApiResponse() {
        viewModel?.loginResponseMutableLiveData?.observe(this, Observer {
            AppUrls.TOKEN = "Bearer " + it.token.toString()
            PrefUtil(this@LoginActivity).sharedPreferences?.edit()
                ?.putBoolean(PrefUtil.IS_LOGIN, true)?.apply()
            PrefUtil(this@LoginActivity).sharedPreferences?.edit()
                ?.putString(PrefUtil.TOKEN, "Bearer " + it.token)?.apply()
            PrefUtil(this@LoginActivity).sharedPreferences?.edit()
                ?.putString(PrefUtil.ID, it?.id)?.apply()
//            PrefUtil(this@LoginActivity).sharedPreferences?.edit()
//                ?.putString(PrefUtil.USERNAME, it.user?.name)?.apply()
            if(it.success==true)
            {
                checkIfDpUploaded()
            }
            else
            {
                val snack = Snackbar.make(binding.root, "${it}", Snackbar.LENGTH_SHORT)
                snack.setBackgroundTint(resources.getColor(R.color.color_5658DD))
                snack.setTextColor(resources.getColor(R.color.white))
                snack.show()
            }
        })
    }
    

    private fun observeErrorMessage() {
        viewModel?.errorMessage?.observe(this, Observer {
                val snack = Snackbar.make(binding.root, "${it}", Snackbar.LENGTH_SHORT)
                snack.setBackgroundTint(resources.getColor(R.color.color_5658DD))
                snack.setTextColor(resources.getColor(R.color.white))
                snack.show()
            })
    }

    private fun observeShowProgress() {
        viewModel?.showProgress?.observe(this, Observer {
            buttonEnableAndDisable(it)
        })

    }

    private fun buttonEnableAndDisable(value: Boolean) {
        if (value) {
            binding.loginBtn.isEnabled = false
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.loginBtn.isEnabled = true
            binding.progressBar.visibility = View.INVISIBLE
        }
    }

    private fun hideMyKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val hideMe = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            hideMe.hideSoftInputFromWindow(view.windowToken, 0)
        }
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)

    }
}
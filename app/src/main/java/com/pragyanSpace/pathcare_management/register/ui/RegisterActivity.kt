package com.pragyanSpace.pathcare_management.register.ui

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.LocationManager
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.pragyanSpace.pathcare_management.MainActivity
import com.pragyanSpace.pathcare_management.R
import com.pragyanSpace.pathcare_management.databinding.ActivityRegisterBinding
import com.pragyanSpace.pathcare_management.login.ui.LoginActivity
import com.pragyanSpace.pathcare_management.register.model.RegisterRequestModel
import com.pragyanSpace.pathcare_management.register.viewmodel.RegisterActivityViewModel
import com.pragyanSpace.pathcare_management.utility.AppUrls
import com.pragyanSpace.pathcare_management.utility.BaseUtil
import java.util.*

class RegisterActivity : AppCompatActivity() {
    lateinit var binding: ActivityRegisterBinding
    private var viewModel: RegisterActivityViewModel? = null
    var textWatchers: TextWatcher? = null
    lateinit var sp: SharedPreferences

    private val REQUEST_FINE_LOCATION = 123

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)
        viewModel = ViewModelProvider(this).get(RegisterActivityViewModel::class.java)
        sp = getSharedPreferences("login", MODE_PRIVATE);
        if (sp.getBoolean("logged", false)) {
            val intent = Intent(this@RegisterActivity, MainActivity::class.java)
            startActivity(intent)
        }
        observeShowProgress()
        observeErrorMessage()
        observeApiResponse()
        initListener()
    }

    private fun initListener() {
        initTextWatcher()
        binding.txtRegister.setOnClickListener {
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
        binding?.registerButton?.setOnClickListener {
            registerBtnClickFunction()
            hideMyKeyboard()
        }
        binding.name.addTextChangedListener(textWatchers)
        binding.password.addTextChangedListener(textWatchers)
        binding.gstNo.addTextChangedListener(textWatchers)
        binding.email.addTextChangedListener(textWatchers)
    }

    private fun hideMyKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val hideMe = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            hideMe.hideSoftInputFromWindow(view.windowToken, 0)
        }
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)

    }

    private fun initTextWatcher() {
        textWatchers = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(editable: Editable?) {
                if (editable == binding?.gstNo?.editableText)
                    binding?.gstNoLayout?.error = null
                if (editable == binding?.password?.editableText)
                    binding?.passwordLayout?.error = null
                if (editable == binding?.name?.editableText)
                    binding?.nameLayout?.error = null
                if (editable == binding?.email?.editableText)
                    binding?.emailLayout?.error = null

            }

        }
    }

    private fun registerBtnClickFunction() {
        val gst = binding.gstNo.text?.trim().toString()
        val password = binding.password.text?.trim()?.toString()
        val name = binding.name.text?.trim()?.toString()
        val email = binding.email.text?.trim()?.toString()
        if (name.isNullOrBlank() || name.length > 255)
            binding?.nameLayout?.error = "Please Enter Name"
        else if (password.isNullOrBlank())
            binding?.passwordLayout?.error = "Please Enter Password"
        else if (password.length < 6 || password.length > 255) {
            binding.passwordLayout.error = "Please Enter Password"
            showSnackBar("Password length must be greater then 6 characters")
        } else if (gst.isNullOrBlank()) {
            binding.gstNoLayout.error = "Please Enter Valid Phone Number"
        }
        else if (email.isNullOrBlank() || !BaseUtil.isValidEmail(email)) {
            binding.emailLayout.error = "Please Enter Valid Email"
        } else {
            val registerRequestModel =
                RegisterRequestModel(email,name, password,gst,getCity())
            callRegisterApi(registerRequestModel)


        }
    }

    private fun showSnackBar(text: String) {
        val snack = Snackbar.make(
            binding.root,
            text,
            Snackbar.LENGTH_SHORT
        )
        snack.setBackgroundTint(resources.getColor(R.color.color_5658DD))
        snack.setTextColor(resources.getColor(R.color.white))
        snack.show()
    }

    private fun callRegisterApi(registerRequestModel: RegisterRequestModel) {
        val token=""
        viewModel?.callRegisterApi(token, registerRequestModel)
    }

    private fun observeApiResponse() {
        viewModel?.registerResponseMutableLiveData?.observe(this, Observer {
            Toast.makeText(
                applicationContext,
                "Registration Successful",
                // "Success:  Token-> ${it.data?.token}",
                Toast.LENGTH_SHORT
            ).show()
            AppUrls.TOKEN = "Bearer " + it.token
            if(it.success==true)
            {
                val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        })
    }

    private fun observeErrorMessage() {
    }

    private fun observeShowProgress() {

    }

    fun getCity(): String? {
        // Check if the app has permission to access the device's location

        if (ActivityCompat.checkSelfPermission(
                this,
                ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {

            // Get the location manager
            val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

            // Get the last known location of the device
            val location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)

            // Use Geocoder to get the city name from the latitude and longitude
            val geocoder = Geocoder(this, Locale.getDefault())
            val addresses = geocoder.getFromLocation(location!!.latitude, location!!.longitude, 1)

            // Get the city name from the address
            return addresses?.get(0)?.locality
        } else {
            requestPermissions(
                arrayOf(ACCESS_FINE_LOCATION),
                REQUEST_FINE_LOCATION
            )
        }
        return ""
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_FINE_LOCATION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {
                Toast.makeText(
                    this,
                    "Location permission not granted.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

}
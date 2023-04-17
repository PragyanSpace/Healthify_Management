package com.pragyanSpace.pathcare_management.management.qrScanner.ui

import android.Manifest.permission.CAMERA
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.CodeScannerView
import com.budiyev.android.codescanner.DecodeCallback
import com.pragyanSpace.pathcare_management.management.qrScanner.viewmodel.QRViewmodel
import com.pragyanSpace.pathcare_management.utility.PrefUtil
import com.pragyanSpace.pathcare_management.R
import com.pragyanSpace.pathcare_management.databinding.FragmentQrScannerBinding

class QrScannerFragment : Fragment() {
    private lateinit var codeScanner: CodeScanner
    private lateinit var binding: FragmentQrScannerBinding
    private val MY_CAMERA_REQUEST_CODE = 100
    lateinit var viewmodel: QRViewmodel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentQrScannerBinding.inflate(layoutInflater, container, false)

        viewmodel = ViewModelProvider(this).get(QRViewmodel::class.java)
        observeUserApiCall()
        return binding.root
    }

    private fun callUserDetailApi(id : String) {
        val token=PrefUtil(requireContext()).sharedPreferences?.getString(PrefUtil.TOKEN, "")
        viewmodel.callUserApi(token,id)
    }

    private fun observeUserApiCall() {
        viewmodel.userResponseMutableLiveData.observe(viewLifecycleOwner, Observer {
            var intent = Intent(requireContext(), ManagementPatientDetailActivity::class.java)
            intent.putExtra("name", it.user?.name)
            intent.putExtra("email", it.user?.email)
            intent.putExtra("dob", it.user?.dob)
            intent.putExtra("blood", it.user?.bloodGroup)
            startActivity(intent)
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val scannerView = view.findViewById<CodeScannerView>(R.id.scanner_view)
        val activity = requireActivity()
        codeScanner = CodeScanner(activity, scannerView)

        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {

        }
        else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(android.Manifest.permission.CAMERA),
                MY_CAMERA_REQUEST_CODE
            )
        }

        codeScanner.decodeCallback = DecodeCallback {
            activity.runOnUiThread {
                callUserDetailApi(it.toString())
            }
        }
        scannerView.setOnClickListener {
            codeScanner.startPreview()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == MY_CAMERA_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(
                    requireContext(),
                    "Camera permission granted.",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                Toast.makeText(
                    requireContext(),
                    "Camera permission not granted.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }
}
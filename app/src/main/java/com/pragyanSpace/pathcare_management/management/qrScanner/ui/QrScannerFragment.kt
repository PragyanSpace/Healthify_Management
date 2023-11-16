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
import com.pragyanSpace.pathcare_management.appointmentDetail.model.CreateAppointmentReqModel
import com.pragyanSpace.pathcare_management.appointmentDetail.ui.AssignDoctorBottomSheet
import com.pragyanSpace.pathcare_management.appointmentDetail.viewmodel.AppointmentDetailViewmodel
import com.pragyanSpace.pathcare_management.databinding.FragmentQrScannerBinding

class QrScannerFragment : Fragment() {
    private lateinit var codeScanner: CodeScanner
    private lateinit var binding: FragmentQrScannerBinding
    private val MY_CAMERA_REQUEST_CODE = 100
    lateinit var viewmodel: QRViewmodel
    var token:String?=null
    var userId:String?=null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentQrScannerBinding.inflate(layoutInflater, container, false)
        viewmodel = ViewModelProvider(this).get(QRViewmodel::class.java)
        token=PrefUtil(requireContext()).sharedPreferences?.getString(PrefUtil.TOKEN, "")

        observeCheckAppointmentApiCall()
        return binding.root
    }


    private fun callCheckAppointmentApi(id : String) {
        val token=PrefUtil(requireContext()).sharedPreferences?.getString(PrefUtil.TOKEN, "")
        viewmodel.callCheckAppointmentApi(token,id)
    }


    private fun observeCheckAppointmentApiCall() {
        viewmodel.userAppointmentResponseMutableLiveData.observe(viewLifecycleOwner, Observer {
            if(it.success==true)
            {
                Toast.makeText(requireContext(),"Appointment available",Toast.LENGTH_LONG).show()
                var intent = Intent(requireContext(), ManagementPatientDetailActivity::class.java)
                intent.putExtra("name", it.appointment?.userId?.name)
                intent.putExtra("email", it.appointment?.userId?.phoneNumber)
                intent.putExtra("dob", it.appointment?.userId?.dob)
                intent.putExtra("blood", it.appointment?.userId?.bloodGroup)
                intent.putExtra("date", it.appointment?.appointmentDate)
                intent.putExtra("description", it.appointment?.description)
                intent.putExtra("docName", it.appointment?.doctorId?.name)
                startActivity(intent)
            }
            else
            {
                Toast.makeText(requireContext(),"Appointment not available. Create appointment",Toast.LENGTH_LONG).show()
                val bottomSheetDialog = AssignDoctorBottomSheet(token,userId,1)
                bottomSheetDialog.isCancelable=true
                bottomSheetDialog.show(activity?.supportFragmentManager!!, "CustomBottomSheetDialogFragment")

            }
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
//                callUserDetailApi(it.toString())
                userId=it.toString()
                callCheckAppointmentApi(it.toString())
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
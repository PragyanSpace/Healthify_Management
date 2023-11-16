package com.pragyanSpace.pathcare_management.appointmentDetail.ui

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.pragyanSpace.pathcare_management.appointmentDetail.model.CreateAppointmentReqModel
import com.pragyanSpace.pathcare_management.appointmentDetail.model.Doctors
import com.pragyanSpace.pathcare_management.appointmentDetail.viewmodel.AppointmentDetailViewmodel
import com.pragyanSpace.pathcare_management.databinding.AssignDoctorBottomSheetRvItemBinding
import com.pragyanSpace.pathcare_management.management.ManagementActivity

class DoctorsRecyclerViewAdapter(
    var context: Context,
    var doctors: ArrayList<Doctors>?,
    var token: String?,
    var id: String?,
    var viewmodel: AppointmentDetailViewmodel,
    var from: Int?
) : RecyclerView.Adapter<DoctorsRecyclerViewAdapter.ViewHolder>() {


    inner class ViewHolder(val binding: AssignDoctorBottomSheetRvItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindView(doctor: Doctors?, context: Context, position: Int) {

            binding.docName = doctor?.name
            binding.deptName = doctor?.specialist



            binding.root.setOnClickListener {
                if (from == 1) {
                    viewmodel.createAppointment(
                        token.toString(),
                        CreateAppointmentReqModel(id, doctor?.Id, "Cardiology")
                    )
                    Toast.makeText(context, "Appointment created", Toast.LENGTH_LONG).show()
                } else {
                    viewmodel.resApiCall(
                        token.toString(),
                        id.toString(),
                        "A",
                        doctor?.Id.toString(),
                        doctor?.specialist.toString()
                    )
                    Toast.makeText(context, "Doctor assigned", Toast.LENGTH_LONG).show()
                }
                val intent = Intent(context, ManagementActivity::class.java)
                startActivity(context, intent, null)
            }

        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = AssignDoctorBottomSheetRvItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        doctors?.get(position).let {
            holder.bindView(it, context, position)
        }
    }

    override fun getItemCount(): Int {
        return doctors?.size ?: 0;
    }
}

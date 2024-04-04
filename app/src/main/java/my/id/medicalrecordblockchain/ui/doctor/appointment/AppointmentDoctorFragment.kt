package my.id.medicalrecordblockchain.ui.doctor.appointment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import my.id.medicalrecordblockchain.databinding.FragmentAppointmentDoctorBinding

class AppointmentDoctorFragment: Fragment() {
    private var _binding: FragmentAppointmentDoctorBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAppointmentDoctorBinding.inflate(inflater, container, false)
        return binding.root
    }
}
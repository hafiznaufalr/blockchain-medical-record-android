package my.id.medicalrecordblockchain.ui.patient.appointment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import my.id.medicalrecordblockchain.databinding.FragmentAppointmentPatientBinding

class AppointmentPatientFragment: Fragment() {
    private var _binding: FragmentAppointmentPatientBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAppointmentPatientBinding.inflate(inflater, container, false)
        return binding.root
    }
}
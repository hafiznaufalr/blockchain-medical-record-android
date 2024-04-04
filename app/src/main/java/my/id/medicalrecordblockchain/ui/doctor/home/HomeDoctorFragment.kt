package my.id.medicalrecordblockchain.ui.doctor.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import my.id.medicalrecordblockchain.databinding.FragmentHomeDoctorBinding

class HomeDoctorFragment : Fragment() {
    private var _binding: FragmentHomeDoctorBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeDoctorBinding.inflate(inflater, container, false)
        return binding.root
    }
}
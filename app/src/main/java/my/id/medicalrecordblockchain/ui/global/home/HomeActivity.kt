package my.id.medicalrecordblockchain.ui.global.home

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.get
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import my.id.medicalrecordblockchain.R
import my.id.medicalrecordblockchain.databinding.ActivityHomeBinding
import my.id.medicalrecordblockchain.ui.doctor.appointment.AppointmentDoctorFragment
import my.id.medicalrecordblockchain.ui.doctor.home.HomeDoctorFragment
import my.id.medicalrecordblockchain.ui.global.account.AccountFragment
import my.id.medicalrecordblockchain.ui.patient.appointment.AppointmentPatientFragment
import my.id.medicalrecordblockchain.ui.patient.home.HomePatientFragment
import my.id.medicalrecordblockchain.utils.decideActionByFlavor

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private val homePatientFragment by lazy { HomePatientFragment() }
    private val homeDoctorFragment by lazy { HomeDoctorFragment() }
    private val appointmentPatientFragment by lazy { AppointmentPatientFragment() }
    private val appointmentDoctorFragment by lazy { AppointmentDoctorFragment() }
    private val accountFragment by lazy { AccountFragment() }
    private val fragmentManager = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
        initListener()
    }

    private fun init() {
        decideActionByFlavor(
            patientAction = {
                fragmentManager
                    .beginTransaction()
                    .replace(R.id.fl_container, homePatientFragment)
                    .commit()
            },
            doctorAction = {
                fragmentManager
                    .beginTransaction()
                    .replace(R.id.fl_container, homeDoctorFragment)
                    .commit()
            }
        )
    }

    private fun initListener() {
        binding.navigation.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    decideActionByFlavor(
                        patientAction = {
                            fragmentManager
                                .beginTransaction()
                                .replace(R.id.fl_container, homePatientFragment)
                                .commit()
                        },
                        doctorAction = {
                            fragmentManager
                                .beginTransaction()
                                .replace(R.id.fl_container, homeDoctorFragment)
                                .commit()
                        }
                    )
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.nav_appointments -> {
                    decideActionByFlavor(
                        patientAction = {
                            fragmentManager
                                .beginTransaction()
                                .replace(R.id.fl_container, appointmentPatientFragment)
                                .commit()
                        },
                        doctorAction = {
                            fragmentManager
                                .beginTransaction()
                                .replace(R.id.fl_container, appointmentDoctorFragment)
                                .commit()
                        }
                    )
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.nav_account -> {
                    fragmentManager
                        .beginTransaction()
                        .replace(R.id.fl_container, accountFragment)
                        .commit()
                    return@setOnNavigationItemSelectedListener true
                }
            }
            return@setOnNavigationItemSelectedListener false
        }
    }

    companion object {
        fun launch(context: Context) {
            context.startActivity(
                Intent(
                    context,
                    HomeActivity::class.java
                )
            )
        }
    }
}
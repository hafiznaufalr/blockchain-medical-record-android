package my.id.medicalrecordblockchain.ui.global.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import my.id.medicalrecordblockchain.R
import my.id.medicalrecordblockchain.databinding.ActivityHomeBinding
import my.id.medicalrecordblockchain.ui.doctor.appointment.AppointmentDoctorFragment
import my.id.medicalrecordblockchain.ui.doctor.home.HomeDoctorFragment
import my.id.medicalrecordblockchain.ui.global.account.AccountFragment
import my.id.medicalrecordblockchain.ui.patient.appointment.AppointmentPatientFragment
import my.id.medicalrecordblockchain.ui.patient.home.HomePatientFragment
import my.id.medicalrecordblockchain.utils.decideActionByFlavor

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
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
                renderFragment(HomePatientFragment())
            },
            doctorAction = {
                renderFragment(HomeDoctorFragment())
            }
        )
    }

    private fun initListener() {
        binding.navigation.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    decideActionByFlavor(
                        patientAction = {
                            renderFragment(HomePatientFragment())
                        },
                        doctorAction = {
                            renderFragment(HomeDoctorFragment())
                        }
                    )
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.nav_appointments -> {
                    decideActionByFlavor(
                        patientAction = {
                            renderFragment(AppointmentPatientFragment())
                        },
                        doctorAction = {
                            renderFragment(AppointmentDoctorFragment())
                        }
                    )
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.nav_account -> {
                    renderFragment(AccountFragment())
                    return@setOnNavigationItemSelectedListener true
                }
            }
            return@setOnNavigationItemSelectedListener false
        }
    }

    private fun renderFragment(fragment: Fragment) {
        fragmentManager
            .beginTransaction()
            .replace(R.id.fl_container, fragment)
            .commit()
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
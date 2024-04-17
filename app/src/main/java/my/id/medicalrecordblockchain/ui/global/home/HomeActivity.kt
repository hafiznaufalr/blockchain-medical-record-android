package my.id.medicalrecordblockchain.ui.global.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import my.id.medicalrecordblockchain.R
import my.id.medicalrecordblockchain.databinding.ActivityHomeBinding
import my.id.medicalrecordblockchain.ui.doctor.appointment.AppointmentDoctorFragment
import my.id.medicalrecordblockchain.ui.doctor.home.HomeDoctorFragment
import my.id.medicalrecordblockchain.ui.global.account.AccountFragment
import my.id.medicalrecordblockchain.ui.patient.appointment.list.AppointmentPatientFragment
import my.id.medicalrecordblockchain.ui.patient.home.HomePatientFragment
import my.id.medicalrecordblockchain.utils.decideActionByFlavor

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private val homePatientFragment by lazy { HomePatientFragment() }
    private val homeDoctorFragment by lazy { HomeDoctorFragment() }
    private val appointmentPatientFragment by lazy { AppointmentPatientFragment() }
    private val appointmentDoctorFragment by lazy { AppointmentDoctorFragment() }
    private val accountFragment by lazy { AccountFragment() }
    private val fragmentManager = supportFragmentManager
    private var indexMenu = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
        initListener()
    }

    private fun init() {
        indexMenu = intent.getIntExtra(INDEX_MENU, 0)

        decideActionByFlavor(
            patientAction = {
                renderFragment(homePatientFragment)
            },
            doctorAction = {
                renderFragment(homeDoctorFragment)
            }
        )
    }

    private fun initListener() {
        binding.navigation.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    renderScreenByMenu()
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.nav_appointments -> {
                    renderScreenByMenu()
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.nav_account -> {
                    renderScreenByMenu()
                    return@setOnNavigationItemSelectedListener true
                }
            }
            return@setOnNavigationItemSelectedListener false
        }

        binding.navigation.menu[indexMenu].isChecked = true
        renderScreenByMenu()
    }

    private fun renderScreenByMenu() {
        when (indexMenu) {
            0 -> {
                decideActionByFlavor(
                    patientAction = {
                        renderFragment(homePatientFragment)
                    },
                    doctorAction = {
                        renderFragment(homeDoctorFragment)
                    }
                )
            }

            1 -> {
                decideActionByFlavor(
                    patientAction = {
                        renderFragment(appointmentPatientFragment)
                    },
                    doctorAction = {
                        renderFragment(appointmentDoctorFragment)
                    }
                )
            }

            2 -> {
                renderFragment(accountFragment)
            }
        }
    }

    private fun renderFragment(fragment: Fragment) {
        fragmentManager
            .beginTransaction()
            .replace(R.id.fl_container, fragment)
            .commit()
    }

    fun showProgressLinear(isVisible: Boolean) {
        // binding.progressLinear.isVisible = isVisible
    }

    companion object {
        private const val INDEX_MENU = "INDEX_MENU"
        fun launch(context: Context, indexMenu: Int = 0) {
            context.startActivity(
                Intent(
                    context,
                    HomeActivity::class.java
                ).apply {
                    putExtra(INDEX_MENU, indexMenu)
                }
            )
        }
    }
}
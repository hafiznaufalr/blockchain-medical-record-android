package my.id.medicalrecordblockchain

import android.app.Application
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatDelegate

class App: Application() {

    companion object {
        private lateinit var appContext: App

        fun getAppContext(): Context {
            return appContext.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        appContext = this

        val preferences =
            applicationContext.getSharedPreferences(applicationContext.packageName, MODE_PRIVATE)

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) AppCompatDelegate.setDefaultNightMode(
            AppCompatDelegate.MODE_NIGHT_NO
        )
    }
}
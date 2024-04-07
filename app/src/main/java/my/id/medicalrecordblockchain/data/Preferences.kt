package my.id.medicalrecordblockchain.data

import android.app.Application
import my.id.medicalrecordblockchain.App

object Preferences {
    private val preferences = App.getAppContext().getSharedPreferences(App.getAppContext().packageName,
        Application.MODE_PRIVATE
    )

    private const val SESSION_TOKEN = "SESSION_TOKEN"

    fun getToken(): String {
        return preferences.getString(SESSION_TOKEN, "").orEmpty()
    }

    fun storeToken(token: String) {
        val editor = preferences.edit()
        editor.putString(SESSION_TOKEN, token)
        editor.apply()
    }
}
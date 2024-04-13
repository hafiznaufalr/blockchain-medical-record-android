package my.id.medicalrecordblockchain.data

import android.app.Application
import com.google.gson.Gson
import my.id.medicalrecordblockchain.App
import my.id.medicalrecordblockchain.data.response.AccountData

object Preferences {
    private val preferences = App.getAppContext().getSharedPreferences(
        App.getAppContext().packageName,
        Application.MODE_PRIVATE
    )

    private const val SESSION_TOKEN = "SESSION_TOKEN"
    private const val SESSION_ACCOUNT = "SESSION_ACCOUNT"

    fun getToken(): String {
        return preferences.getString(SESSION_TOKEN, "").orEmpty()
    }

    fun storeToken(token: String) {
        val editor = preferences.edit()
        editor.putString(SESSION_TOKEN, token)
        editor.apply()
    }

    fun getAccount(): AccountData? {
        return try {
            val json = preferences.getString(SESSION_ACCOUNT, "").orEmpty()
            Gson().fromJson(json, AccountData::class.java)
        } catch (exception: Exception) {
            null
        }
    }

    fun storeAccount(data: AccountData?) {
        if (data == null) return
        val editor = preferences.edit()
        editor.putString(SESSION_ACCOUNT, Gson().toJson(data))
        editor.apply()
    }
}
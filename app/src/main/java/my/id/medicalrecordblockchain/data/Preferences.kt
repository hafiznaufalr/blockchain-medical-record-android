package my.id.medicalrecordblockchain.data

import android.app.Application
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import my.id.medicalrecordblockchain.App
import my.id.medicalrecordblockchain.data.response.AccountData
import my.id.medicalrecordblockchain.data.response.ServicesData

object Preferences {
    private val preferences = App.getAppContext().getSharedPreferences(
        App.getAppContext().packageName,
        Application.MODE_PRIVATE
    )

    private const val SESSION_TOKEN = "SESSION_TOKEN"
    private const val SESSION_ACCOUNT = "SESSION_ACCOUNT"
    private const val SESSION_SERVICES = "SESSION_SERVICES"

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

    fun storeServices(data: List<ServicesData>) {
        val editor = preferences.edit()
        editor.putString(SESSION_SERVICES, Gson().toJson(data))
        editor.apply()
    }

    fun getServices(): List<ServicesData> {
        return try {
            Gson().fromJson(
                preferences.getString(SESSION_SERVICES, ""),
                object :
                    TypeToken<ArrayList<ServicesData>>() {
                }.type
            )
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun clear() {
        preferences.edit().clear().apply()
    }
}
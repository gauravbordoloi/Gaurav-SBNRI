package `in`.gauravbordoloi.gauravsbnri.utils

import `in`.gauravbordoloi.gauravsbnri.BuildConfig
import android.content.Context
import android.content.res.Configuration

class SharedPref(val context: Context) {

    private val DEFAULT_USERNAME = "octokit"
    private val DEFAULT_TYPE = "Organization"

    private val USERNAME = "account_username"
    private val TYPE = "account_type"
    private val NIGHT_MODE = "app_night_mode"
    private val DEFAULT_MODE: Boolean

    private val sharedPref =
        context.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE)
    private val editor = sharedPref.edit()

    init {
        val mode = context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        DEFAULT_MODE = when (mode) {
            Configuration.UI_MODE_NIGHT_YES -> true
            else -> false
        }
    }

    fun clear() {
        editor.clear()
        editor.apply()
    }

    fun isNightMode(): Boolean {
        return sharedPref.getBoolean(NIGHT_MODE, DEFAULT_MODE)
    }

    fun setNightMode(value: Boolean) {
        editor.putBoolean(NIGHT_MODE, value)
        editor.commit()
    }

    fun getUsername(): String {
        return sharedPref.getString(USERNAME, DEFAULT_USERNAME)!!
    }

    fun setUsername(username: String) {
        editor.putString(USERNAME, username)
        editor.commit()
    }

    fun getType(): String {
        return when (val type = sharedPref.getString(TYPE, DEFAULT_TYPE)!!) {
            DEFAULT_TYPE -> {
                "orgs"
            }
            "Individual" -> {
                "users"
            }
            else -> {
                type
            }
        }
    }

    fun setType(type: String) {
        editor.putString(TYPE, type)
        editor.commit()
    }

}
package `in`.gauravbordoloi.gauravsbnri.data

import `in`.gauravbordoloi.gauravsbnri.AppController
import `in`.gauravbordoloi.gauravsbnri.data.model.Account
import `in`.gauravbordoloi.gauravsbnri.data.network.api.ApiClient
import `in`.gauravbordoloi.gauravsbnri.data.network.api.ApiService
import `in`.gauravbordoloi.gauravsbnri.data.storage.AppDatabase
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.orhanobut.logger.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.ConnectException
import java.net.UnknownHostException

class AccountRepository(val context: Context) {

    private val database = AppDatabase.getInstance(context.applicationContext).accountDao()
    private val accountLiveData = MutableLiveData<Account?>()
    private val apiService = ApiClient.getClient().create(ApiService::class.java)
    private val pref = AppController.sharedPref

    companion object {

        private var instance: AccountRepository? = null

        fun getInstance(context: Context): AccountRepository {
            if (instance == null) {
                instance = AccountRepository(context)
            }
            return instance!!
        }

    }

    init {
        getAccount()
    }

    private fun getAccount() {
        GlobalScope.launch(Dispatchers.IO) {
            val account = database.getAccount(pref.getUsername())
            if (!account.isNullOrEmpty()) {
                withContext(Dispatchers.Main) {
                    accountLiveData.postValue(account[0])
                }
            }
        }
        val call = apiService.getAccount(pref.getType(), pref.getUsername())
        call.enqueue(object : Callback<Account?> {
            override fun onFailure(call: Call<Account?>, t: Throwable) {
                Logger.e(t, "loadInitial")
                if (t is ConnectException || t is UnknownHostException) {
                    Toast.makeText(context, "There is no internet. Loading from database.", Toast.LENGTH_LONG).show()
                }
            }

            override fun onResponse(call: Call<Account?>, response: Response<Account?>) {
                val rate = response.headers().get("X-Ratelimit-Remaining")
                val time = response.headers().get("X-Ratelimit-Reset")
                if (!rate.isNullOrEmpty() && !time.isNullOrEmpty()) {
                    AppController.REMAINING_HIT.postValue(rate.toInt())
                    AppController.RESET_TIME = time.toLong()
                }
                if (response.isSuccessful) {
                    accountLiveData.postValue(response.body())
                    GlobalScope.launch(Dispatchers.IO) {
                        database.insert(response.body())
                    }
                } else {
                    Logger.e(response.message())
                    accountLiveData.postValue(null)
                }
            }
        })
    }

    fun getAccountData() = accountLiveData

    fun clear() {
        instance = null
    }

}
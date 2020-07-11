package `in`.gauravbordoloi.gauravsbnri

import `in`.gauravbordoloi.gauravsbnri.utils.SharedPref
import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger

class AppController: Application() {

    companion object {

        lateinit var sharedPref: SharedPref

        var RATE_LIMIT = 60
        var REMAINING_HIT = MutableLiveData<Int>()
        var RESET_TIME = (-1).toLong()

    }

    override fun onCreate() {
        super.onCreate()

        sharedPref = SharedPref(applicationContext)
        Logger.addLogAdapter(AndroidLogAdapter())

    }

}
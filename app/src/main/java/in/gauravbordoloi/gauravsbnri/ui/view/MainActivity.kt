package `in`.gauravbordoloi.gauravsbnri.ui.view

import `in`.gauravbordoloi.gauravsbnri.AppController
import `in`.gauravbordoloi.gauravsbnri.R
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (AppController.sharedPref.isNightMode()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        AppController.REMAINING_HIT.observe(this, Observer {
            if (it == null) {
                return@Observer
            }
            if (AppController.RATE_LIMIT == it) {
                val difference = (AppController.RESET_TIME - (System.currentTimeMillis()/1000)).toInt()
                Snackbar.make(
                    root,
                    "REST call rate limit exceeded. Please try again after $difference seconds",
                    Snackbar.LENGTH_INDEFINITE
                ).setAction("RESTART") {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }.show()
            }
        })

    }

}
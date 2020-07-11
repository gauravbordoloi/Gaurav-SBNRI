package `in`.gauravbordoloi.gauravsbnri.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.TypedValue
import com.orhanobut.logger.Logger
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

object Helper {

    @SuppressLint("ConstantLocale")
    private val sdf = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'", Locale.getDefault())
    @SuppressLint("ConstantLocale")
    private val SDF = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())

    fun formatDate(date: String): String {
        val d = sdf.parse(date)
        return if (d != null) {
            SDF.format(d)
        } else {
            ""
        }
    }

    fun openLink(context: Context?, url: String): Boolean {
        return try {
            val i = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            context?.startActivity(i)
            true
        } catch (e: Exception) {
            Logger.e(e, "openLink")
            false
        }
    }

    fun dpToPx(context: Context?, dp: Int): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp.toFloat(),
            context?.resources?.displayMetrics
        ).toInt()
    }

}
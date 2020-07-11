package `in`.gauravbordoloi.gauravsbnri.data.model

import androidx.room.TypeConverter
import com.google.gson.Gson

class Converter {

    companion object {

        private val gson = Gson()

        @TypeConverter
        @JvmStatic
        fun fromLicense(value: License?): String? {
            return gson.toJson(value)
        }

        @TypeConverter
        @JvmStatic
        fun toLicense(value: String?): License? {
            return gson.fromJson<License>(value, License::class.java)
        }

        @TypeConverter
        @JvmStatic
        fun fromPermissions(value: Permissions?): String? {
            return gson.toJson(value)
        }

        @TypeConverter
        @JvmStatic
        fun toPermissions(value: String?): Permissions? {
            return gson.fromJson<Permissions>(value, Permissions::class.java)
        }

    }

}
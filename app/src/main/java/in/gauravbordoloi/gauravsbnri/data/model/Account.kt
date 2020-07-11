package `in`.gauravbordoloi.gauravsbnri.data.model

import androidx.annotation.Keep
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Keep
@Entity
data class Account(
    @PrimaryKey
    @NonNull
    val id: Long,
    val login: String,
    val name: String,
    val description: String?, //for Organization
    @SerializedName("avatar_url")
    val image: String,
    val bio: String?, //for User
    @SerializedName("html_url")
    val url: String,
    val type: String,
    @SerializedName("public_repos")
    val reposCount: Int,
    @SerializedName("public_gists")
    val gistsCount: Int,
    val message: String?
)
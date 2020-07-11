package `in`.gauravbordoloi.gauravsbnri.data.model

import androidx.annotation.Keep
import androidx.annotation.NonNull
import androidx.recyclerview.widget.DiffUtil
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName

@Keep
@Entity
@TypeConverters(Converter::class)
data class Repo(
    @PrimaryKey
    @NonNull
    val id: Long,
    val name: String,
    @SerializedName("html_url")
    val url: String,
    val description: String?,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("open_issues_count")
    val openIssuesCount: Int,
    val license: License?,
    val permissions: Permissions? //not available for user account type
) {

    companion object {

        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Repo>() {
            override fun areItemsTheSame(oldItem: Repo, newItem: Repo): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Repo, newItem: Repo): Boolean {
                return oldItem.id == newItem.id
            }
        }

    }

}
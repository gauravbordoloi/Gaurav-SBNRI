package `in`.gauravbordoloi.gauravsbnri.data.model

import androidx.annotation.Keep

@Keep
data class Permissions(
    val admin: Boolean,
    val push: Boolean,
    val pull: Boolean
)
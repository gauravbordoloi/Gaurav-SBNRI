package `in`.gauravbordoloi.gauravsbnri.utils

data class NetworkState(
    val status: Status,
    val message: String
) {

    companion object {

        val LOADING = NetworkState(Status.LOADING, "Loading")

        val LOADED = NetworkState(Status.SUCCESS, "Loaded")

    }

    enum class Status {
        LOADING,
        SUCCESS,
        ERROR
    }

}
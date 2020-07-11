package `in`.gauravbordoloi.gauravsbnri.data.network.paging

import `in`.gauravbordoloi.gauravsbnri.AppController
import `in`.gauravbordoloi.gauravsbnri.data.model.Repo
import `in`.gauravbordoloi.gauravsbnri.data.network.api.ApiClient
import `in`.gauravbordoloi.gauravsbnri.data.network.api.ApiService
import `in`.gauravbordoloi.gauravsbnri.utils.NetworkState
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.orhanobut.logger.Logger
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.ConnectException
import java.net.UnknownHostException

class ReposPageKeyedDataSource : PageKeyedDataSource<Int, Repo>() {

    private val PER_PAGE = 10
    private val apiService = ApiClient.getClient().create(ApiService::class.java)
    private val networkState = MutableLiveData<NetworkState>()
    private val reposObservable = MutableLiveData<List<Repo>>()
    private val pref = AppController.sharedPref

    fun getNetworkState() = networkState

    fun getRepos() = reposObservable

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Repo>
    ) {
        networkState.postValue(NetworkState.LOADING)
        val call = apiService.getRepos(pref.getType(), pref.getUsername(), PER_PAGE, 1)
        call.enqueue(object : Callback<List<Repo>?> {
            override fun onFailure(call: Call<List<Repo>?>, t: Throwable) {
                Logger.e(t, "loadInitial")
                var message = t.message.toString()
                if (t is ConnectException || t is UnknownHostException) {
                    message = "No Internet"
                }
                networkState.postValue(
                    NetworkState(
                        NetworkState.Status.ERROR,
                        message
                    )
                )
                callback.onResult(emptyList(), 1, 2)
            }

            override fun onResponse(call: Call<List<Repo>?>, response: Response<List<Repo>?>) {
                val rate = response.headers().get("X-Ratelimit-Remaining")
                val time = response.headers().get("X-Ratelimit-Reset")
                if (!rate.isNullOrEmpty() && !time.isNullOrEmpty()) {
                    AppController.REMAINING_HIT.postValue(rate.toInt())
                    AppController.RESET_TIME = time.toLong()
                }
                if (response.isSuccessful) {
                    callback.onResult(response.body()!!, 1, 2)
                    networkState.postValue(NetworkState.LOADED)
                    reposObservable.postValue(response.body()!!)
                } else {
                    Logger.e(response.message())
                    networkState.postValue(
                        NetworkState(
                            NetworkState.Status.ERROR,
                            response.message()
                        )
                    )
                }
            }
        })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Repo>) {
        networkState.postValue(NetworkState.LOADING)
        val call = apiService.getRepos(pref.getType(), pref.getUsername(), PER_PAGE, params.key)
        call.enqueue(object : Callback<List<Repo>?> {
            override fun onFailure(call: Call<List<Repo>?>, t: Throwable) {
                Logger.e(t, "loadInitial")
                var message = t.message.toString()
                if (t is ConnectException || t is UnknownHostException) {
                    message = "No Internet"
                }
                networkState.postValue(
                    NetworkState(
                        NetworkState.Status.ERROR,
                        message
                    )
                )
                callback.onResult(emptyList(), params.key)
            }

            override fun onResponse(call: Call<List<Repo>?>, response: Response<List<Repo>?>) {
                val rate = response.headers().get("X-Ratelimit-Remaining")
                val time = response.headers().get("X-Ratelimit-Reset")
                if (!rate.isNullOrEmpty() && !time.isNullOrEmpty()) {
                    AppController.REMAINING_HIT.postValue(rate.toInt())
                    AppController.RESET_TIME = time.toLong()
                }
                if (response.isSuccessful) {
                    callback.onResult(response.body()!!, params.key + 1)
                    networkState.postValue(NetworkState.LOADED)
                    reposObservable.postValue(response.body()!!)
                } else {
                    Logger.e(response.message())
                    networkState.postValue(
                        NetworkState(
                            NetworkState.Status.ERROR,
                            response.message()
                        )
                    )
                }
            }
        })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Repo>) {

    }


}
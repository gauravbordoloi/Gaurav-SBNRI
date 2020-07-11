package `in`.gauravbordoloi.gauravsbnri.data

import `in`.gauravbordoloi.gauravsbnri.data.model.Repo
import `in`.gauravbordoloi.gauravsbnri.data.network.ReposNetwork
import `in`.gauravbordoloi.gauravsbnri.data.network.paging.ReposDataSourceFactory
import `in`.gauravbordoloi.gauravsbnri.data.storage.AppDatabase
import `in`.gauravbordoloi.gauravsbnri.utils.NetworkState
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.paging.PagedList
import androidx.paging.PagedList.BoundaryCallback
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ReposRepository(context: Context) {

    private val dataSourceFactory = ReposDataSourceFactory()
    private val network = ReposNetwork(dataSourceFactory, object : BoundaryCallback<Repo>() {
        override fun onZeroItemsLoaded() {
            super.onZeroItemsLoaded()
            liveDataMerger.addSource<PagedList<Repo>>(
                database.getRepos()
            ) { value ->
                liveDataMerger.value = value
                liveDataMerger.removeSource(database.getRepos())
            }
        }
    })
    private val database = AppDatabase.getInstance(context.applicationContext)
    private var liveDataMerger = MediatorLiveData<PagedList<Repo>>()

    companion object {

        private var instance: ReposRepository? = null

        fun getInstance(context: Context): ReposRepository {
            if (instance == null) {
                instance = ReposRepository(context)
            }
            return instance!!
        }

    }

    init {
        liveDataMerger.addSource(
            network.getPagedRepos()
        ) { value ->
            liveDataMerger.setValue(value)
        }
        dataSourceFactory.getRepos().observeForever {
            GlobalScope.launch(Dispatchers.IO) {
                database.repoDao().insertRepos(it)
            }
        }
    }

    fun getRepos(): LiveData<PagedList<Repo>> {
        return liveDataMerger
    }

    fun getNetworkState(): LiveData<NetworkState> {
        return network.getNetworkState()
    }

    fun clear() {
        instance = null
    }

}
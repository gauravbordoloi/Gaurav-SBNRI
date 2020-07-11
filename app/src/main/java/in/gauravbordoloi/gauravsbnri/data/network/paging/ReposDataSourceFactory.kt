package `in`.gauravbordoloi.gauravsbnri.data.network.paging

import `in`.gauravbordoloi.gauravsbnri.data.model.Repo
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource

class ReposDataSourceFactory: DataSource.Factory<Int, Repo>() {

    private val networkStatus = MutableLiveData<ReposPageKeyedDataSource>()
    private val pageKeyedDataSource = ReposPageKeyedDataSource()

    override fun create(): DataSource<Int, Repo> {
        networkStatus.postValue(pageKeyedDataSource)
        return pageKeyedDataSource
    }

    fun getNetworkStatus(): MutableLiveData<ReposPageKeyedDataSource> = networkStatus

    fun getRepos() = pageKeyedDataSource.getRepos()

}
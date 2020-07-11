package `in`.gauravbordoloi.gauravsbnri.data.network

import `in`.gauravbordoloi.gauravsbnri.data.model.Repo
import `in`.gauravbordoloi.gauravsbnri.data.network.paging.ReposDataSourceFactory
import `in`.gauravbordoloi.gauravsbnri.data.network.paging.ReposPageKeyedDataSource
import `in`.gauravbordoloi.gauravsbnri.utils.NetworkState
import androidx.arch.core.util.Function
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList

class ReposNetwork(
    dataSourceFactory: ReposDataSourceFactory,
    boundaryCallback: PagedList.BoundaryCallback<Repo>
) {

    private val PER_PAGE = 10

    private val pagedListConfig = PagedList.Config.Builder().setEnablePlaceholders(false)
        .setInitialLoadSizeHint(PER_PAGE)
        .setPageSize(PER_PAGE)
        .build()
    private var reposPaged: LiveData<PagedList<Repo>>
    private var networkState = Transformations.switchMap<ReposPageKeyedDataSource, NetworkState>(
        dataSourceFactory.getNetworkStatus(),
        ReposPageKeyedDataSource::getNetworkState
    )

    init {
        val livePagedListBuilder =
            LivePagedListBuilder<Int, Repo>(dataSourceFactory, pagedListConfig)
        reposPaged = livePagedListBuilder.setBoundaryCallback(boundaryCallback).build()
    }

    fun getPagedRepos() = reposPaged

    fun getNetworkState(): LiveData<NetworkState> = networkState

}
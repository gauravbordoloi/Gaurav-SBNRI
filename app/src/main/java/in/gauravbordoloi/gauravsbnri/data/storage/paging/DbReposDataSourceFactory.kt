package `in`.gauravbordoloi.gauravsbnri.data.storage.paging

import `in`.gauravbordoloi.gauravsbnri.data.model.Repo
import `in`.gauravbordoloi.gauravsbnri.data.storage.RepoDao
import androidx.paging.DataSource

class DbReposDataSourceFactory(private val dao: RepoDao) : DataSource.Factory<Int, Repo>() {

    override fun create(): DataSource<Int, Repo> = DbReposPageKeyedDataSource(dao)

}
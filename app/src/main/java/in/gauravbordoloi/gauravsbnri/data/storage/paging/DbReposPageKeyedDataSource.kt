package `in`.gauravbordoloi.gauravsbnri.data.storage.paging

import `in`.gauravbordoloi.gauravsbnri.data.model.Repo
import `in`.gauravbordoloi.gauravsbnri.data.storage.RepoDao
import androidx.paging.PageKeyedDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DbReposPageKeyedDataSource(private val dao: RepoDao) : PageKeyedDataSource<Int, Repo>() {

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Repo>
    ) {
        GlobalScope.launch(Dispatchers.IO) {
            val repos = dao.getRepos()
            if (!repos.isNullOrEmpty()) {
                withContext(Dispatchers.Main) {
                    callback.onResult(repos, null, null)
                }
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Repo>) {

    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Repo>) {

    }

}
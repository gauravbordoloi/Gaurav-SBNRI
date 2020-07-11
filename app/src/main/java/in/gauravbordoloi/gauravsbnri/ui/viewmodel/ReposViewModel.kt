package `in`.gauravbordoloi.gauravsbnri.ui.viewmodel

import `in`.gauravbordoloi.gauravsbnri.data.ReposRepository
import `in`.gauravbordoloi.gauravsbnri.data.model.Repo
import `in`.gauravbordoloi.gauravsbnri.utils.NetworkState
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.paging.PagedList


class ReposViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ReposRepository.getInstance(application)

    fun getRepos(): LiveData<PagedList<Repo>> {
        return repository.getRepos()
    }

    fun getNetworkState(): LiveData<NetworkState> {
        return repository.getNetworkState()
    }

    fun clear() {
        repository.clear()
    }

}
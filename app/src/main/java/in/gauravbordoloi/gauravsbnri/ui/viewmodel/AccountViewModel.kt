package `in`.gauravbordoloi.gauravsbnri.ui.viewmodel

import `in`.gauravbordoloi.gauravsbnri.data.AccountRepository
import `in`.gauravbordoloi.gauravsbnri.data.model.Account
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class AccountViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = AccountRepository.getInstance(application)

    fun getAccount(): MutableLiveData<Account?> {
        return repository.getAccountData()
    }

    fun clear() {
        repository.clear()
    }

}
package `in`.gauravbordoloi.gauravsbnri.data.storage

import `in`.gauravbordoloi.gauravsbnri.data.model.Account
import `in`.gauravbordoloi.gauravsbnri.data.model.Repo
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AccountDao {

    @Query("SELECT * FROM account WHERE login = :username LIMIT 1")
    suspend fun getAccount(username: String): List<Account>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(account: Account?)

    @Query("DELETE FROM account")
    abstract fun deleteAll()

}
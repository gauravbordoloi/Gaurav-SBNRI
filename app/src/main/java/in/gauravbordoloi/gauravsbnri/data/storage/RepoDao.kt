package `in`.gauravbordoloi.gauravsbnri.data.storage

import `in`.gauravbordoloi.gauravsbnri.data.model.Repo
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RepoDao {

    @Query("SELECT * FROM repo")
    suspend fun getRepos(): List<Repo>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRepo(repo: Repo?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRepos(repos: List<Repo>?)

    @Query("DELETE FROM repo")
    abstract fun deleteAll()

}
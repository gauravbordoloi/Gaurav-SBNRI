package `in`.gauravbordoloi.gauravsbnri.data.storage

import `in`.gauravbordoloi.gauravsbnri.data.model.Account
import `in`.gauravbordoloi.gauravsbnri.data.model.Converter
import `in`.gauravbordoloi.gauravsbnri.data.model.Repo
import `in`.gauravbordoloi.gauravsbnri.data.storage.paging.DbReposDataSourceFactory
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Database(entities = [Repo::class, Account::class], version = 2)
@TypeConverters(Converter::class)
abstract class AppDatabase : RoomDatabase() {

    private lateinit var reposPaged: LiveData<PagedList<Repo>>

    companion object {

        private const val DB_NAME = "gauravsbnri.db"

        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase = instance ?: synchronized(this) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            instance = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, DB_NAME)
                .fallbackToDestructiveMigration().build()
            instance!!.init()
            return instance!!
        }

    }

    private fun init() {
        val pagedListConfig = PagedList.Config.Builder().setEnablePlaceholders(false)
            .setInitialLoadSizeHint(Int.MAX_VALUE).setPageSize(Int.MAX_VALUE).build()
        val dataSourceFactory = DbReposDataSourceFactory(repoDao())
        val livePagedListBuilder = LivePagedListBuilder(dataSourceFactory, pagedListConfig)
        GlobalScope.launch(Dispatchers.IO) {
            reposPaged = livePagedListBuilder.build()
        }
    }

    abstract fun repoDao(): RepoDao

    abstract fun accountDao(): AccountDao

    fun getRepos(): LiveData<PagedList<Repo>> = reposPaged

}
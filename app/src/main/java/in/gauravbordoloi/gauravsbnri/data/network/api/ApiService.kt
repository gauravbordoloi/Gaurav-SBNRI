package `in`.gauravbordoloi.gauravsbnri.data.network.api

import `in`.gauravbordoloi.gauravsbnri.data.model.Account
import `in`.gauravbordoloi.gauravsbnri.data.model.Repo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("{type}/{username}")
    fun getAccount(@Path("type") type: String, @Path("username") username: String): Call<Account>

    @GET("{type}/{username}/repos")
    fun getRepos(
        @Path("type") type: String,
        @Path("username") username: String,
        @Query("per_page") perPage: Int,
        @Query("page") page: Int
    ): Call<List<Repo>>

}
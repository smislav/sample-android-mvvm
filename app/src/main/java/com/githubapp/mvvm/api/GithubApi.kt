package com.githubapp.mvvm.api

import com.githubapp.data.models.Repo
import com.githubapp.data.models.User
import com.githubapp.mvvm.api.models.Authorization
import com.githubapp.mvvm.api.models.AuthorizationRequest
import com.githubapp.mvvm.api.models.GetReposResponse
import io.reactivex.Observable
import retrofit2.http.*

interface GithubApi {
    companion object {
        const val API_URL = "https://api.github.com/"
    }

    @POST("authorizations")
    fun login(@Header("Authorization") authorization: String,
              @Body request: AuthorizationRequest): Observable<Authorization>

    @GET("user")
    fun getUserByToken(@Query("access_token") token: String): Observable<User>

    @GET("search/repositories")
    fun getRepos(
            @Query("page") page: Int,
            @Query("q") query: String,
            @Query("sort") sort: String,
            @Query("access_token") token: String,
            @Query("per_page") perPage: Int = 30): Observable<GetReposResponse>

    @GET("repos/{user}/{repo}")
    fun getRepo(@Path("user")username: String,
                @Path("repo")repoName: String,
                @Query("access_token") token: String): Observable<Repo>

    @GET("users/{username}")
    fun getUser(@Path("username")username: String,
                @Query("access_token") token: String): Observable<User>



}
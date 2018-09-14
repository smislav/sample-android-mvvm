package com.githubapp.mvvm.data

import com.githubapp.data.models.Repo
import com.githubapp.data.models.User
import io.reactivex.Observable

interface IDataSource {
    fun getRepos(page: Int, query: String, sort: String): Observable<List<Repo>>
    fun getRepo(username: String, repoName: String): Observable<Repo>
    fun getUser(username: String): Observable<User>
}
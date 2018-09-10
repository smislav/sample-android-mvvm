package com.githubapp.data.source

import com.githubapp.data.models.Repo
import com.githubapp.data.models.User
import com.githubapp.mvvm.data.remote.RemoteGithubRepository
import io.reactivex.Observable
import javax.inject.Inject

class GithubRepository : IGithubRepository{
    private var remoteDataSource: RemoteGithubRepository

    @Inject
    constructor(remoteDataSource: RemoteGithubRepository){
        this.remoteDataSource = remoteDataSource
    }

    override fun getRepos(page: Int, query: String, sort: String): Observable<List<Repo>> {
        return remoteDataSource.getRepos(page, query, sort)
    }

    override fun getRepo(username: String, repoName: String): Observable<Repo> {
        return remoteDataSource.getRepo(username, repoName)
    }

    override fun getUser(username: String): Observable<User> {
        return remoteDataSource.getUser(username)
    }
}
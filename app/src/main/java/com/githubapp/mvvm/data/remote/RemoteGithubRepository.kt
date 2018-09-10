package com.githubapp.mvvm.data.remote

import com.githubapp.api.GithubApi
import com.githubapp.data.models.Repo
import com.githubapp.data.models.User
import com.githubapp.data.source.IGithubRepository
import com.githubapp.mvvm.ui.login.LoginManager
import io.reactivex.Observable
import javax.inject.Inject

class RemoteGithubRepository : IGithubRepository{
    var mGithubService : GithubApi
    var mLoginManager : LoginManager

    @Inject
    constructor(githubService: GithubApi, loginManager: LoginManager){
        mGithubService = githubService
        mLoginManager = loginManager

    }

    override fun getRepos(page: Int, query: String, sort: String): Observable<List<Repo>> {
        return mGithubService.getRepos(page, query, sort, "d07f61ff5d4533dc0eefcf6bd87a87e43defac5a")
                .map { it -> it.items }
    }

    override fun getRepo(username: String, repoName: String): Observable<Repo> {
        return mGithubService.getRepo(username, repoName, "d07f61ff5d4533dc0eefcf6bd87a87e43defac5a")
    }

    override fun getUser(username: String): Observable<User> {
        return mGithubService.getUser(username, "d07f61ff5d4533dc0eefcf6bd87a87e43defac5a")
    }
}
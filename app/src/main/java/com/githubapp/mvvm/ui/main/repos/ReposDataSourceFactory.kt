package com.githubapp.mvvm.ui.main.repos

import androidx.lifecycle.MutableLiveData
import com.githubapp.data.models.Repo
import com.githubapp.mvvm.data.DataSource
import com.githubapp.mvvm.enums.Sort
import javax.inject.Inject

class ReposDataSourceFactory : androidx.paging.DataSource.Factory<Int, Repo> {
    companion object {
        const val DEFAULT_QUERY = "android"
        val DEFAULT_SORT = Sort.FORKS
    }

    var reposSource: ReposDataSource
    var liveReposSource: MutableLiveData<ReposDataSource>

    var dataSource: DataSource
    var query: String
    var sort: Sort

    @Inject
    constructor(dataSource: DataSource){
        this.dataSource = dataSource
        this.query = DEFAULT_QUERY
        this.sort = DEFAULT_SORT

        this.reposSource = ReposDataSource(dataSource, query, sort)
        this.liveReposSource = MutableLiveData()
    }

    override fun create(): androidx.paging.DataSource<Int, Repo> {
        reposSource = ReposDataSource(dataSource, query, sort)

        liveReposSource.postValue(reposSource)

        return reposSource
    }
}
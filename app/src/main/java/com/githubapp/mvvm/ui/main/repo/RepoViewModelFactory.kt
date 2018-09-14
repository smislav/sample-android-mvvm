package com.githubapp.mvvm.ui.main.repo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.githubapp.mvvm.data.DataSource
import javax.inject.Inject

class RepoViewModelFactory: ViewModelProvider.Factory{
    private var dataSource: DataSource

    @Inject
    constructor(dataSource: DataSource){
        this.dataSource = dataSource
    }
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(RepoViewModel::class.java)){
            return RepoViewModel(dataSource) as T
        }
        throw RuntimeException("ViewModel not found")
    }
}
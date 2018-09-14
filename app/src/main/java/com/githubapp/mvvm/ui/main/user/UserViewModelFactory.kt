package com.githubapp.mvvm.ui.main.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.githubapp.mvvm.data.DataSource
import javax.inject.Inject

class UserViewModelFactory: ViewModelProvider.Factory{
    private var dataSource: DataSource

    @Inject
    constructor(dataSource: DataSource){
        this.dataSource = dataSource
    }
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(UserViewModel::class.java)){
            return UserViewModel(dataSource) as T
        }
        throw RuntimeException("ViewModel not found")
    }
}
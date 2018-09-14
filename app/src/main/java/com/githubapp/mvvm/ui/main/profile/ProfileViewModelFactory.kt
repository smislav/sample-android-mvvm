package com.githubapp.mvvm.ui.main.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.githubapp.mvvm.data.DataSource
import com.githubapp.mvvm.ui.login.LoginManager
import javax.inject.Inject

class ProfileViewModelFactory: ViewModelProvider.Factory{
    private var loginManager: LoginManager

    @Inject
    constructor(loginManager: LoginManager){
        this.loginManager = loginManager
    }
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ProfileViewModel::class.java)){
            return ProfileViewModel(loginManager) as T
        }
        throw RuntimeException("ViewModel not found")
    }
}
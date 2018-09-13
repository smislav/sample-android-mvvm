package com.githubapp.mvvm.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.githubapp.mvvm.ui.login.LoginManager
import com.githubapp.mvvm.ui.main.repos.ReposViewModel
import javax.inject.Inject

class SplashViewModelFactory: ViewModelProvider.Factory {
    private var loginManager: LoginManager

    @Inject
    constructor(loginManager: LoginManager){
        this.loginManager = loginManager
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SplashViewModel::class.java)){
            return SplashViewModel(loginManager) as T
        }
        throw RuntimeException("ViewModel not found")
    }
}
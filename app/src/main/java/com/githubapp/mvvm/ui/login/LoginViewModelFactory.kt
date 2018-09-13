package com.githubapp.mvvm.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class LoginViewModelFactory: ViewModelProvider.Factory {
    private var loginManager: LoginManager

    @Inject
    constructor(loginManager: LoginManager){
        this.loginManager = loginManager
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(LoginViewModel::class.java)){
            return LoginViewModel(loginManager) as T
        }
        throw RuntimeException("ViewModel not found")
    }
}
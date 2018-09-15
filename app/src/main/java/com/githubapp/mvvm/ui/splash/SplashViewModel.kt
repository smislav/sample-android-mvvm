package com.githubapp.mvvm.ui.splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.githubapp.mvvm.ui.login.LoginManager
import javax.inject.Inject

class SplashViewModel : ViewModel{
    var logged: MutableLiveData<Boolean>

    private var loginManager: LoginManager

    @Inject
    constructor(loginManager: LoginManager){
        this.logged = MutableLiveData()
        this.loginManager = loginManager
    }

    fun checkLogin(){
        logged.postValue(
                loginManager.isLogged()
        )
    }
}
package com.githubapp.mvvm.ui.splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.githubapp.mvvm.ui.login.LoginManager
import javax.inject.Inject

class SplashViewModel : ViewModel{
    var loginManager: LoginManager
    var logged: MutableLiveData<Boolean>

    @Inject
    constructor(loginManager: LoginManager){
        this.loginManager = loginManager
        logged = MutableLiveData()
    }

    fun checkLogin(){
        logged.postValue(
                loginManager.isLogged()
        )
    }
}
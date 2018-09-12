package com.githubapp.mvvm.ui.splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SplashViewModel : ViewModel{
    companion object {
        const val LOGGED: String = "LOGGED"
        const val NOT_LOGGED: String = "NOT_LOGGED"
    }

    var state: MutableLiveData<String>

    constructor(){
        state = MutableLiveData()
    }

    fun startSpash(){

    }

    fun checkLogin(){
        state.postValue(LOGGED)
    }
}
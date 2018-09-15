package com.githubapp.mvvm.ui.main.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.githubapp.data.models.User
import com.githubapp.helpers.SimpleCallbackWrapper
import com.githubapp.mvvm.enums.LoadingState
import com.githubapp.mvvm.ui.login.LoginManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ProfileViewModel : ViewModel {
    var user: MutableLiveData<User>
    var state: MutableLiveData<LoadingState>

    private var loginManager: LoginManager

    @Inject
    constructor(loginManager: LoginManager){
        this.user = MutableLiveData()
        this.state = MutableLiveData()
        this.loginManager = loginManager
    }

    fun getCurrentUser(){
        state.postValue(LoadingState.LOADING)
        loginManager.user()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SimpleCallbackWrapper<User>() {
                    override fun onNext(t: User) {
                        user.postValue(t)
                        state.postValue(LoadingState.LOADED)
                    }

                    override fun onNetworkError() {
                        state.postValue(LoadingState.NETWORK_ERROR)
                    }

                    override fun onAccessError() {
                        state.postValue(LoadingState.ACCESS_ERROR)
                    }

                    override fun onUnknownError() {
                        state.postValue(LoadingState.UNKNOWN_ERROR)
                    }
                })
    }

    fun logoutCurrentUser(){
        loginManager.logout()
        user.postValue(null)
    }
}
package com.githubapp.mvvm.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.githubapp.mvvm.api.models.Authorization
import com.githubapp.helpers.SimpleCallbackWrapper
import com.githubapp.mvvm.enums.LoadingState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LoginViewModel : ViewModel{
    var loadingState: MutableLiveData<LoadingState>

    private var loginManager: LoginManager

    @Inject
    constructor(loginManager: LoginManager){
        this.loadingState = MutableLiveData()
        this.loginManager = loginManager
    }

    fun login(username: String, password: String){
        loadingState.postValue(LoadingState.LOADING)
        loginManager.login(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SimpleCallbackWrapper<Authorization>(){
                    override fun onNext(t: Authorization) {
                        loadingState.postValue(LoadingState.LOADED)
                    }

                    override fun onNetworkError() {
                        loadingState.postValue(LoadingState.NETWORK_ERROR)
                    }

                    override fun onAccessError() {
                        loadingState.postValue(LoadingState.ACCESS_ERROR)
                    }

                    override fun onUnknownError() {
                        loadingState.postValue(LoadingState.UNKNOWN_ERROR)
                    }
                })
    }
}
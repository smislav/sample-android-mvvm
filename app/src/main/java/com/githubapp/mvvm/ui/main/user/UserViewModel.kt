package com.githubapp.mvvm.ui.main.user

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.githubapp.data.models.User
import com.githubapp.mvvm.data.DataSource
import com.githubapp.helpers.SimpleCallbackWrapper
import com.githubapp.mvvm.enums.LoadingState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class UserViewModel : ViewModel {
    var data: DataSource
    var user: MutableLiveData<User>
    var state: MutableLiveData<LoadingState>

    @Inject
    constructor(data: DataSource){
        this.data = data
        this.user = MutableLiveData()
        this.state = MutableLiveData()
    }

    fun getUser(username: String){
        state.postValue(LoadingState.IN_PROGRESS)
        data.getUser(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SimpleCallbackWrapper<User>() {
                    override fun onNext(t: User) {
                        user.postValue(t)
                        state.postValue(LoadingState.DONE)
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
}
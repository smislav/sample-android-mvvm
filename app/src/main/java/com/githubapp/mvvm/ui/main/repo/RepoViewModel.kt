package com.githubapp.mvvm.ui.main.repo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.githubapp.data.models.Repo
import com.githubapp.helpers.SimpleCallbackWrapper
import com.githubapp.mvvm.data.DataSource
import com.githubapp.mvvm.enums.LoadingState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RepoViewModel : ViewModel {
    var data: DataSource
    var repo: MutableLiveData<Repo>
    var state: MutableLiveData<LoadingState>

    @Inject
    constructor(data: DataSource){
        this.data = data
        this.repo = MutableLiveData()
        this.state = MutableLiveData()
    }

    fun getRepo(username: String, repoName: String){
        state.postValue(LoadingState.LOADING)
        data.getRepo(username, repoName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SimpleCallbackWrapper<Repo>() {
                    override fun onNext(t: Repo) {
                        repo.postValue(t)
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
}
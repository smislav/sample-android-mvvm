package com.githubapp.mvvm.ui.main.repos

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.githubapp.data.models.Repo
import com.githubapp.data.source.GithubRepository
import com.githubapp.helpers.SimpleCallbackWrapper
import com.githubapp.mvvm.enums.LoadingState
import com.githubapp.mvvm.enums.Sort
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ReposDataSource: PageKeyedDataSource<Int, Repo> {
    private var githubRepository: GithubRepository;
    private var query: String
    private var sort: Sort

    var loadingState: MutableLiveData<LoadingState>

    @Inject
    constructor(githubRepository: GithubRepository, query: String, sort: Sort){
        this.githubRepository = githubRepository;
        this.query = query
        this.sort = sort

        this.loadingState = MutableLiveData()
    }

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Repo>) {
        loadingState.postValue(LoadingState.IN_PROGRESS)
        githubRepository
                .getRepos(0, query, sort.value)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object: SimpleCallbackWrapper<List<Repo>>(){
                    override fun onNext(t: List<Repo>) {
                        callback.onResult(t, null, 1)
                        loadingState.postValue(LoadingState.DONE)
                    }

                    override fun onAccessError() {
                        loadingState.postValue(LoadingState.ACCESS_ERROR)
                    }

                    override fun onNetworkError() {
                        loadingState.postValue(LoadingState.NETWORK_ERROR)
                    }

                    override fun onUnknownError() {
                        loadingState.postValue(LoadingState.UNKNOWN_ERROR)
                    }
                })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Repo>) {
        loadingState.postValue(LoadingState.IN_PROGRESS)
        githubRepository
                .getRepos(params.key, query, sort.value)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object: SimpleCallbackWrapper<List<Repo>>(){
                    override fun onNext(t: List<Repo>) {
                        callback.onResult(t, params.key + 1)
                        loadingState.postValue(LoadingState.DONE)
                    }

                    override fun onAccessError() {
                        loadingState.postValue(LoadingState.ACCESS_ERROR)
                    }

                    override fun onNetworkError() {
                        loadingState.postValue(LoadingState.NETWORK_ERROR)
                    }

                    override fun onUnknownError() {
                        loadingState.postValue(LoadingState.UNKNOWN_ERROR)
                    }
                })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Repo>) {
    }
}
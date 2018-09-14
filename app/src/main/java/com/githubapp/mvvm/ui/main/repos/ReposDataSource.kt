package com.githubapp.mvvm.ui.main.repos

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.githubapp.data.models.Repo
import com.githubapp.mvvm.data.DataSource
import com.githubapp.helpers.SimpleCallbackWrapper
import com.githubapp.mvvm.enums.LoadingState
import com.githubapp.mvvm.enums.Sort
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ReposDataSource: PageKeyedDataSource<Int, Repo> {
    private var dataSource: DataSource;
    private var query: String
    private var sort: Sort

    var loadingState: MutableLiveData<LoadingState>

    @Inject
    constructor(dataSource: DataSource, query: String, sort: Sort){
        this.dataSource = dataSource;
        this.query = query
        this.sort = sort

        this.loadingState = MutableLiveData()
    }

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Repo>) {
        loadingState.postValue(LoadingState.LOADING)
        dataSource
                .getRepos(0, query, sort.value)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object: SimpleCallbackWrapper<List<Repo>>(){
                    override fun onNext(t: List<Repo>) {
                        callback.onResult(t, null, 1)
                        loadingState.postValue(LoadingState.LOADED)
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
        loadingState.postValue(LoadingState.LOADING)
        dataSource
                .getRepos(params.key, query, sort.value)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object: SimpleCallbackWrapper<List<Repo>>(){
                    override fun onNext(t: List<Repo>) {
                        callback.onResult(t, params.key + 1)
                        loadingState.postValue(LoadingState.LOADED)
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
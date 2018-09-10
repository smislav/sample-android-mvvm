package com.githubapp.mvvm.di.module

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.githubapp.data.models.Repo
import com.githubapp.data.source.GithubRepository
import com.githubapp.di.scope.FragmentScope
import com.githubapp.mvvm.ui.base.ReposViewModelFactory
import com.githubapp.mvvm.ui.main.repos.ReposDataSourceFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ReposModule {
    @Provides
    @FragmentScope
    fun provideDataSourceFactory(githubRepository: GithubRepository): ReposDataSourceFactory {
        return ReposDataSourceFactory(githubRepository)
    }

    @Provides
    @FragmentScope
    fun provideLivePagedListBuilder(dataSourceFactory: ReposDataSourceFactory): LivePagedListBuilder<Int, Repo>{
        return LivePagedListBuilder(dataSourceFactory, 30)
    }

    @Provides
    @FragmentScope
    fun provideLiveData(listBuilder: LivePagedListBuilder<Int, Repo>): LiveData<PagedList<Repo>>{
        return listBuilder.build()
    }

    @Provides
    @FragmentScope
    fun provideViewModelFactory(dataSourceFactory: ReposDataSourceFactory, repos: LiveData<PagedList<Repo>>): ReposViewModelFactory {
        return ReposViewModelFactory(dataSourceFactory,repos);
    }
}
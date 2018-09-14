package com.githubapp.mvvm.di.module

import com.githubapp.di.scope.FragmentScope
import com.githubapp.mvvm.data.DataSource
import com.githubapp.mvvm.ui.main.repo.RepoViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class RepoModule {
    @Provides
    @FragmentScope
    fun provideViewModelFactory(dataSource: DataSource): RepoViewModelFactory {
        return RepoViewModelFactory(dataSource)
    }
}
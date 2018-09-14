package com.githubapp.mvvm.di.module

import com.githubapp.mvvm.data.DataSource
import com.githubapp.di.scope.FragmentScope
import com.githubapp.mvvm.ui.main.user.UserViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class UserModule {
    @Provides
    @FragmentScope
    fun provideViewModelFactory(dataSource: DataSource): UserViewModelFactory {
        return UserViewModelFactory(dataSource)
    }
}
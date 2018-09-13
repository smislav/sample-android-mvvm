package com.githubapp.mvvm.di.module

import com.githubapp.di.scope.ActivityScope
import com.githubapp.mvvm.ui.login.LoginManager
import com.githubapp.mvvm.ui.login.LoginViewModelFactory
import com.githubapp.mvvm.ui.main.repos.ReposViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class LoginModule {
    @Provides
    @ActivityScope
    fun provideViewModelFactory(loginManager: LoginManager): LoginViewModelFactory {
        return LoginViewModelFactory(loginManager)
    }
}
package com.githubapp.mvvm.di.module

import com.githubapp.di.scope.ActivityScope
import com.githubapp.mvvm.ui.login.LoginManager
import com.githubapp.mvvm.ui.splash.SplashViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class SplashModule {
    @Provides
    @ActivityScope
    fun provideViewModelFactory(loginManager: LoginManager): SplashViewModelFactory {
        return SplashViewModelFactory(loginManager)
    }
}
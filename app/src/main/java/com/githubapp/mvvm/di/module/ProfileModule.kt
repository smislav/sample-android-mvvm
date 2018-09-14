package com.githubapp.mvvm.di.module

import com.githubapp.di.scope.FragmentScope
import com.githubapp.mvvm.ui.login.LoginManager
import com.githubapp.mvvm.ui.main.profile.ProfileViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class ProfileModule {
    @Provides
    @FragmentScope
    fun provideViewModelFactory(loginManager: LoginManager): ProfileViewModelFactory {
        return ProfileViewModelFactory(loginManager)
    }
}
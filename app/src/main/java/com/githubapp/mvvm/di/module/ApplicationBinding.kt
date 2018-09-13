package com.githubapp.mvvm.di.module

import com.githubapp.di.scope.ActivityScope
import com.githubapp.mvvm.ui.login.LoginActivity
import com.githubapp.mvvm.ui.main.MainActivity
import com.githubapp.mvvm.ui.splash.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ApplicationBinding {
    @ContributesAndroidInjector(modules = [(MainModule::class), (MainBinding::class)])
    @ActivityScope
    abstract fun mainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [(LoginModule::class)])
    @ActivityScope
    abstract fun loginActivity(): LoginActivity

    @ContributesAndroidInjector(modules = [(SplashModule::class)])
    @ActivityScope
    abstract fun splashActivity(): SplashActivity
}
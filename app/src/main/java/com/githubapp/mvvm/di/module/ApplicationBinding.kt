package com.githubapp.mvvm.di.module

import com.githubapp.di.scope.ActivityScope
import com.githubapp.mvvm.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ApplicationBinding {
    @ContributesAndroidInjector(modules = [(MainModule::class), (MainBinding::class)])
    @ActivityScope
    abstract fun mainActivity(): MainActivity
}
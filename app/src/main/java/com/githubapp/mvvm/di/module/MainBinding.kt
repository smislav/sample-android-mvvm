package com.githubapp.mvvm.di.module

import com.githubapp.data.models.Repo
import com.githubapp.di.scope.FragmentScope
import com.githubapp.mvvm.ui.main.repos.ReposFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainBinding(){
    @ContributesAndroidInjector(modules = [(ReposModule::class)])
    @FragmentScope
    abstract fun reposFragment(): ReposFragment
}
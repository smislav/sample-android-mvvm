package com.githubapp.mvvm.di.module

import com.githubapp.di.scope.FragmentScope
import com.githubapp.mvvm.ui.main.profile.ProfileFragment
import com.githubapp.mvvm.ui.main.repos.ReposFragment
import com.githubapp.mvvm.ui.main.user.UserFragment
import com.githubapp.mvvm.ui.main.repo.RepoFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainBinding(){
    @ContributesAndroidInjector(modules = [(ReposModule::class)])
    @FragmentScope
    abstract fun reposFragment(): ReposFragment

    @ContributesAndroidInjector(modules = [(UserModule::class)])
    @FragmentScope
    abstract fun userFragment(): UserFragment

    @ContributesAndroidInjector(modules = [(RepoModule::class)])
    @FragmentScope
    abstract fun repoFragment(): RepoFragment

    @ContributesAndroidInjector(modules = [(ProfileModule::class)])
    @FragmentScope
    abstract fun profileFragment(): ProfileFragment
}
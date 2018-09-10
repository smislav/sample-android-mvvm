package com.githubapp.mvvm.di.component

import android.app.Application
import com.githubapp.mvvm.GithubApp
import com.githubapp.mvvm.di.module.ApplicationBinding
import com.githubapp.mvvm.di.module.ApplicationModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Component(modules = [
    (AndroidSupportInjectionModule::class),
    (ApplicationModule::class),
    (ApplicationBinding::class)])
@Singleton
interface ApplicationComponent : AndroidInjector<GithubApp> {
    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<GithubApp>()
}
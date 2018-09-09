package com.githubapp.mvvm.di.component

import android.app.Application
import com.githubapp.mvvm.GithubApp
import com.githubapp.mvvm.di.module.ApplicationBinding
import com.githubapp.mvvm.di.module.ApplicationModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule

@Component(modules = [
    (AndroidInjectionModule::class),
    (ApplicationModule::class),
    (ApplicationBinding::class)])
interface ApplicationComponent {
    @Component.Builder
    interface Builder{
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): ApplicationComponent
    }

    fun inject(app: GithubApp)
}
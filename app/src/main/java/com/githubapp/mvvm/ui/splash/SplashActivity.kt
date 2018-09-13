package com.githubapp.mvvm.ui.splash

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.githubapp.mvvm.R
import dagger.android.AndroidInjection
import javax.inject.Inject

class SplashActivity : AppCompatActivity() {
    companion object {
        const val SPLASH_TIME: Long = 3000
    }

    @Inject
    lateinit var viewModelFactory: SplashViewModelFactory

    private lateinit var viewModel: SplashViewModel

    private lateinit var navigation: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        navigation = Navigation.findNavController(this, R.id.navigation_host)

        viewModel = ViewModelProviders.of(this, viewModelFactory)[SplashViewModel::class.java]
        viewModel.logged.observe(this, Observer {
            when(it){
                true -> logged()
                false -> notLogged()
            }
        })

        Handler().postDelayed({
            viewModel.checkLogin()
        },SPLASH_TIME)
    }


    fun logged(){
        navigation.navigate(R.id.splashToMain)
        finish()
    }

    fun notLogged(){
        navigation.navigate(R.id.splashToLogin)
        finish()
    }
}

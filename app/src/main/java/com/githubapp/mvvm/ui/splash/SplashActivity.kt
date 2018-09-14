package com.githubapp.mvvm.ui.splash

import android.os.Bundle
import android.os.Handler
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.githubapp.mvvm.R
import com.githubapp.mvvm.ui.base.BaseActivity
import javax.inject.Inject

class SplashActivity : BaseActivity() {
    companion object {
        const val SPLASH_TIME: Long = 3000
    }

    @Inject
    lateinit var viewModelFactory: SplashViewModelFactory

    private lateinit var viewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

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
        navigation().navigate(R.id.splashToMain)
        finish()
    }

    fun notLogged(){
        navigation().navigate(R.id.splashToLogin)
        finish()
    }

    override fun navigation(): NavController {
        return Navigation.findNavController(this, R.id.navigation_splash)
    }
}

package com.githubapp.mvvm.ui.splash

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.githubapp.mvvm.R

class SplashActivity : AppCompatActivity() {
    companion object {
        const val SPLASH_TIME: Long = 3000
    }

    private lateinit var navigation: NavController
    private lateinit var viewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        navigation = Navigation.findNavController(this, R.id.navigation_host)

        viewModel = ViewModelProviders.of(this).get(SplashViewModel::class.java)
        viewModel.state.observe(this, Observer {
            when(it){
                SplashViewModel.LOGGED -> logged()
                SplashViewModel.NOT_LOGGED -> notLogged()
            }
        })

        Handler().postDelayed({
            viewModel.checkLogin()
        },SPLASH_TIME)
    }


    fun logged(){
        navigation.navigate(R.id.action_splashFragment_to_mainActivity)
        finish()
    }

    fun notLogged(){
        navigation.navigate(R.id.action_splashFragment_to_mainActivity)
        finish()
    }
}

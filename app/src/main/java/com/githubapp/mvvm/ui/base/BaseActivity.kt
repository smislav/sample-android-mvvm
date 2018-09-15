package com.githubapp.mvvm.ui.base

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import com.githubapp.mvvm.R
import com.githubapp.mvvm.ui.login.LoginManager
import dagger.android.AndroidInjection
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity() {
    @Inject protected lateinit var loginManager: LoginManager

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)

        super.onCreate(savedInstanceState)
    }

    abstract fun navigation(): NavController

    protected open fun loading(){
        showLoadingView()
    }

    protected open fun loaded(){
        hideLoadingView()
    }

    protected open fun networkError(){
        showMessage(getString(R.string.error_network))
        hideLoadingView()
    }

    protected open fun unknownError(){
        showMessage(getString(R.string.error_unknown))
        hideLoadingView()
    }

    protected open fun accessError(){
        showMessage(getString(R.string.error_access))

        loginManager.logout()

        navigation().navigate(R.id.splashActivity)
        finish()

        hideLoadingView()
    }

    protected open fun showLoadingView(){
    }

    protected open fun hideLoadingView(){
    }

    protected open fun showMessage(message: String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}

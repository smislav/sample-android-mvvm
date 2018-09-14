package com.githubapp.mvvm.ui.login

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.githubapp.mvvm.R
import com.githubapp.mvvm.enums.LoadingState
import com.githubapp.mvvm.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

class LoginActivity : BaseActivity() {
    @Inject
    lateinit var viewModelFactory: LoginViewModelFactory

    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        viewModel = ViewModelProviders.of(this, viewModelFactory)[LoginViewModel::class.java]
        viewModel.loadingState.observe(this, Observer { state ->
            when(state!!){
                LoadingState.LOADING -> loading()
                LoadingState.LOADED -> loaded()
                LoadingState.ACCESS_ERROR -> accessError()
                LoadingState.NETWORK_ERROR -> networkError()
                LoadingState.UNKNOWN_ERROR -> unknownError()
            }
        })

        buttonLogin.setOnClickListener{ onLoginClick() }
    }

    fun onLoginClick(){
        viewModel.login(
                editEmail.text.toString(),
                editPassword.text.toString())
    }

    override fun loaded() {
        navigation().navigate(R.id.loginToMain)
        finish()
    }

    override fun accessError() {
        showMessage(getString(R.string.error_access))
    }

    override fun showLoadingView() {
        progress.visibility = View.VISIBLE
        content.visibility = View.GONE
    }

    override fun hideLoadingView() {
        progress.visibility = View.GONE
        content.visibility = View.VISIBLE
    }

    override fun navigation(): NavController {
        return Navigation.findNavController(this, R.id.navigation_login)
    }
}

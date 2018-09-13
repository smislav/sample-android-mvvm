package com.githubapp.mvvm.ui.login

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.githubapp.mvvm.R
import com.githubapp.mvvm.enums.LoadingState
import com.githubapp.mvvm.ui.blank.BlankFragmentDirections
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

class LoginActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: LoginViewModelFactory

    private lateinit var viewModel: LoginViewModel

    private lateinit var navigation: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        navigation = Navigation.findNavController(this, R.id.navigation_login)

        viewModel = ViewModelProviders.of(this, viewModelFactory)[LoginViewModel::class.java]
        viewModel.loadingState.observe(this, Observer { state ->
            when(state!!){
                LoadingState.IN_PROGRESS -> onLoginStarted()
                LoadingState.DONE -> onLoginSucceeded()
                LoadingState.ACCESS_ERROR -> onLoginFailed(getString(R.string.error_access))
                LoadingState.NETWORK_ERROR -> onLoginFailed(getString(R.string.error_network))
                LoadingState.UNKNOWN_ERROR -> onLoginFailed(getString(R.string.error_unknown))
            }
        })

        buttonLogin.setOnClickListener{ onLoginClick() }
    }

    fun onLoginClick(){
        viewModel.login(
                editEmail.text.toString(),
                editPassword.text.toString())
    }

    fun onLoginStarted(){
        showLoading()
    }

    fun onLoginSucceeded(){
        navigation.navigate(R.id.loginToMain)
        hideLoading()
    }

    private fun onLoginFailed(message: String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        hideLoading()
    }

    private fun showLoading(){
        progress.visibility = View.VISIBLE
        content.visibility = View.GONE
    }

    private fun hideLoading(){
        progress.visibility = View.GONE
        content.visibility = View.VISIBLE
    }
}

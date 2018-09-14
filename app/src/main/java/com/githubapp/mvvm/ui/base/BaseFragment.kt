package com.githubapp.mvvm.ui.base

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.githubapp.mvvm.R
import com.githubapp.mvvm.ui.login.LoginManager
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

abstract class BaseFragment : Fragment() {
    @Inject
    lateinit var loginManager: LoginManager

    protected lateinit var navigation: NavController

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navigation = Navigation.findNavController(view)
    }

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

        navigation.navigate(R.id.splashActivity)
        activity?.finish()

        hideLoadingView()
    }

    protected open fun showLoadingView(){
    }

    protected open fun hideLoadingView(){
    }

    protected open fun showMessage(message: String){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}

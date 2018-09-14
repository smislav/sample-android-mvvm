package com.githubapp.mvvm.ui.main.profile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.githubapp.mvvm.R
import com.githubapp.mvvm.enums.LoadingState
import com.githubapp.mvvm.utils.startBrowser
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_profile.*
import javax.inject.Inject

class ProfileFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: ProfileViewModelFactory

    private lateinit var viewModel: ProfileViewModel

    private lateinit var navigation: NavController

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navigation = Navigation.findNavController(view)

        viewModel = ViewModelProviders.of(this, viewModelFactory)[ProfileViewModel::class.java]
        viewModel.state.observe(this, Observer { state ->
            when(state!!){
                LoadingState.IN_PROGRESS -> showLoading()
                LoadingState.DONE -> hideLoading()
                LoadingState.ACCESS_ERROR -> showError(getString(R.string.error_access))
                LoadingState.NETWORK_ERROR -> showError(getString(R.string.error_network))
                LoadingState.UNKNOWN_ERROR -> showError(getString(R.string.error_unknown))
            }
        })
        viewModel.user.observe(this, Observer { user ->
            if(user == null){
                navigation.navigate(ProfileFragmentDirections.profileToSplash())
                activity?.finish()
            }else{
                Glide   .with(this)
                        .load(user.image)
                        .apply(RequestOptions.circleCropTransform())
                        .into(imageUser)

                textUsername.text = user.username

                buttonLogout.setOnClickListener{
                    viewModel.logoutCurrentUser()
                }

                infoName.text = user.name
                infoBio.text = user.bio
                infoEmail.text = user.email
                infoLocation.text = user.location
                infoCompany.text = user.company
                infoUsername.text = user.username
                infoType.text = user.type
            }
        })
        viewModel.getCurrentUser()
    }

    private fun showLoading(){
        progress.visibility = View.VISIBLE
        content.visibility = View.GONE
    }

    private fun hideLoading(){
        progress.visibility = View.GONE
        content.visibility = View.VISIBLE
    }

    private fun showError(message: String){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        hideLoading()
    }
}

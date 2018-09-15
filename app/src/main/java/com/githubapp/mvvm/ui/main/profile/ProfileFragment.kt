package com.githubapp.mvvm.ui.main.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.githubapp.mvvm.R
import com.githubapp.mvvm.enums.LoadingState
import com.githubapp.mvvm.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_profile.*
import javax.inject.Inject

class ProfileFragment : BaseFragment() {
    @Inject lateinit var viewModelFactory: ProfileViewModelFactory

    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory)[ProfileViewModel::class.java]
        viewModel.state.observe(this, Observer { state ->
            when(state!!){
                LoadingState.LOADING -> loading()
                LoadingState.LOADED -> loaded()
                LoadingState.ACCESS_ERROR -> accessError()
                LoadingState.NETWORK_ERROR -> networkError()
                LoadingState.UNKNOWN_ERROR -> unknownError()
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

    override fun showLoadingView() {
        progress.visibility = View.VISIBLE
        content.visibility = View.GONE
    }

    override fun hideLoadingView() {
        progress.visibility = View.GONE
        content.visibility = View.VISIBLE
    }
}

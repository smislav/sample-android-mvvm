package com.githubapp.mvvm.ui.main.user

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.githubapp.mvvm.R
import com.githubapp.mvvm.enums.LoadingState
import com.githubapp.mvvm.utils.startBrowser
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_user.*
import kotlinx.android.synthetic.main.item_repo.*
import javax.inject.Inject

class UserFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: UserViewModelFactory

    private lateinit var viewModel: UserViewModel

    private lateinit var username: String

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        username = UserFragmentArgs.fromBundle(arguments).username

        viewModel = ViewModelProviders.of(this, viewModelFactory)[UserViewModel::class.java]
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
            Glide   .with(this)
                    .load(user.image)
                    .apply(RequestOptions.circleCropTransform())
                    .into(imageUser)

            textUsername.text = user.username

            buttonView.setOnClickListener{
                startBrowser(context!!, user.url)
            }

            infoName.text = user.name
            infoBio.text = user.bio
            infoEmail.text = user.email
            infoLocation.text = user.location
            infoCompany.text = user.company
            infoUsername.text = user.username
            infoType.text = user.type
        })
        viewModel.getUser(username)
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

package com.githubapp.mvvm.ui.main.repos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.githubapp.data.models.Repo
import com.githubapp.data.models.User
import com.githubapp.mvvm.databinding.FragmentReposBinding
import com.githubapp.mvvm.enums.LoadingState
import com.githubapp.mvvm.enums.Sort
import com.githubapp.mvvm.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_repos.*
import javax.inject.Inject

class ReposFragment : BaseFragment() {
    @Inject
    lateinit var viewModelFactory: ReposViewModelFactory

    private lateinit var viewModel: ReposViewModel

    private lateinit var adapter: ReposAdapter

    private lateinit var binding: FragmentReposBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentReposBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory)[ReposViewModel::class.java]
        viewModel.getLiveDataSource().observe(this, Observer { source ->
            source.loadingState.observe(this, Observer { state ->
                when(state!!){
                    LoadingState.LOADING -> loading()
                    LoadingState.LOADED -> loaded()
                    LoadingState.ACCESS_ERROR -> accessError()
                    LoadingState.NETWORK_ERROR -> networkError()
                    LoadingState.UNKNOWN_ERROR -> unknownError()
                }
            })
        })

        adapter = ReposAdapter()
        adapter.repoClick.observe(this, Observer { onRepoClick(it) })
        adapter.userClick.observe(this, Observer { onUserClick(it) })

        binding.viewModel = viewModel
        binding.adapter = adapter

        buttonProfile.setOnClickListener{ onProfileClick() }

        val layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        val itemDecoration = DividerItemDecoration(context, layoutManager.orientation)

        recyclerRepos.adapter = adapter
        recyclerRepos.layoutManager = layoutManager
        recyclerRepos.addItemDecoration(itemDecoration)

        refreshRepos.setOnRefreshListener {
            viewModel.reloadData()
            refreshRepos.isRefreshing = false
        }

        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.setQuery(query!!)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })

        toggleStars.setOnClickListener { viewModel.setSort(Sort.STARS) }
        toggleForks.setOnClickListener { viewModel.setSort(Sort.FORKS) }
        toggleUpdated.setOnClickListener { viewModel.setSort(Sort.UPDATED) }
    }

    override fun showLoadingView() {
        refreshRepos.isRefreshing = true
    }

    override fun hideLoadingView() {
        refreshRepos.isRefreshing = false
    }

    private fun onRepoClick(repo: Repo){
        navigation.navigate(
                ReposFragmentDirections.reposToRepo(
                        repo.user.username, repo.name)
        )
    }

    private fun onUserClick(user: User){
       navigation.navigate(
               ReposFragmentDirections.reposToUser(
                       user.username)
       )
    }

    private fun onProfileClick(){
        navigation.navigate(
                ReposFragmentDirections.reposToProfile()
        )
    }
}

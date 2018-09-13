package com.githubapp.mvvm.ui.main.repos

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.githubapp.mvvm.R
import com.githubapp.mvvm.enums.LoadingState
import com.githubapp.mvvm.enums.Sort
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_repos.*
import javax.inject.Inject


class ReposFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: ReposViewModelFactory

    private lateinit var viewModel: ReposViewModel

    private lateinit var adapter: ReposAdapter

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_repos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ReposAdapter()

        val layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        val itemDecoration = DividerItemDecoration(context, layoutManager.orientation)

        recyclerRepos.setAdapter(adapter)
        recyclerRepos.setLayoutManager(layoutManager)
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

        groupSort.setOnCheckedChangeListener { _, id ->
            when(id){
                R.id.toggleStars -> viewModel.setSort(Sort.STARS)
                R.id.toggleForks -> viewModel.setSort(Sort.FORKS)
                R.id.toggleUpdated -> viewModel.setSort(Sort.UPDATED)
            }
        }

        viewModel = ViewModelProviders.of(this, viewModelFactory)[ReposViewModel::class.java]
        viewModel.repos.observe(this, Observer {
            adapter.submitList(it)
        })
        viewModel.getLiveDataSource().observe(this, Observer { source ->
            source.loadingState.observe(this, Observer { state ->
                when(state!!){
                    LoadingState.IN_PROGRESS -> showLoading()
                    LoadingState.DONE -> hideLoading()
                    LoadingState.ACCESS_ERROR -> showError(getString(R.string.error_access))
                    LoadingState.NETWORK_ERROR -> showError(getString(R.string.error_network))
                    LoadingState.UNKNOWN_ERROR -> showError(getString(R.string.error_unknown))
                }
            })
        })
    }

    private fun showLoading(){
        refreshRepos.isRefreshing = true
    }

    private fun hideLoading(){
        refreshRepos.isRefreshing = false
    }

    private fun showError(message: String){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        hideLoading()
    }
}

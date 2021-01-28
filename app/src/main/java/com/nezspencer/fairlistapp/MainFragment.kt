package com.nezspencer.fairlistapp

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.nezspencer.fairlistapp.util.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_main.*

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {
    private val jobsViewModel: JobsViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val jobsAdapter = JobAdapter{
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToJobDetailFragment(it))
        }
        swipe_refresh.setOnRefreshListener {
            jobsViewModel.getJobs(true)
        }
        rv_users.adapter = jobsAdapter
        jobsViewModel.jobsLiveData.observe(viewLifecycleOwner, Observer {
            when(it.status) {
                Status.LOADING -> swipe_refresh.isRefreshing = true
                Status.SUCCESS -> {
                    swipe_refresh.isRefreshing = false
                    jobsAdapter.submitList(it.data)
                }
                Status.ERROR -> {
                    Snackbar.make(requireView(), it.errorMessage ?: "empty error", Snackbar.LENGTH_SHORT).show()
                }
            }
        })
    }
}
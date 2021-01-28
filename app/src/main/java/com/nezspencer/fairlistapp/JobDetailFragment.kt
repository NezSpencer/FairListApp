package com.nezspencer.fairlistapp

import android.os.Bundle
import android.view.View
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.nezspencer.fairlistapp.data.Job
import com.nezspencer.fairlistapp.util.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_job_detail.*

@AndroidEntryPoint
class JobDetailFragment : Fragment(R.layout.fragment_job_detail) {
    private val args by navArgs<JobDetailFragmentArgs>()
    private val jobsViewModel by viewModels<JobsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupJobDetailScreen(args.job)
        jobsViewModel.jobDetailLiveData.observe(viewLifecycleOwner, Observer {
            if (it.status == Status.SUCCESS) {
                setupJobDetailScreen(it.data!!)
            }
        })
        jobsViewModel.getJob(args.job.id)
    }

    private fun setupJobDetailScreen(job: Job) = with(job){
        companyLogo?.let { logoUrl ->
            Glide.with(iv_logo).load(logoUrl).apply(RequestOptions().placeholder(R.drawable.ic_work))
                .into(iv_logo)
        } ?: iv_logo.setImageResource(R.drawable.ic_work)

        tv_title.text = String.format("%s at %s", title, company)
        tv_employment_type.text = type
        tv_date.text = createdAt
        tv_location.text = location
        tv_job_url.text = url
        tv_job_description.text = HtmlCompat.fromHtml(description, HtmlCompat.FROM_HTML_MODE_LEGACY)
        tv_how_to_apply.text = HtmlCompat.fromHtml(howToApply, HtmlCompat.FROM_HTML_MODE_LEGACY)
    }
}
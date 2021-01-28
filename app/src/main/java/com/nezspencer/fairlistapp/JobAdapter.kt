package com.nezspencer.fairlistapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.nezspencer.fairlistapp.data.Job
import com.nezspencer.fairlistapp.data.hasSameContent
import kotlinx.android.synthetic.main.item_job.view.*

class JobAdapter(private val onJobClicked: (Job) -> Unit) : ListAdapter<Job, JobHolder>(JobDiff()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobHolder =
        JobHolder.create(parent)

    override fun onBindViewHolder(holder: JobHolder, position: Int) {
        holder.bind(getItem(position), onJobClicked)
    }
}

class JobDiff : DiffUtil.ItemCallback<Job>() {
    override fun areItemsTheSame(oldItem: Job, newItem: Job): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Job, newItem: Job): Boolean =
        oldItem.hasSameContent(newItem)
}

class JobHolder private constructor(listItem: View) : RecyclerView.ViewHolder(listItem) {
    fun bind(job: Job, onJobClicked: (Job) -> Unit) = with(itemView) {
        tv_title.text = String.format("%s at %s", job.title, job.company)
        tv_employment_type.text = job.type
        tv_location.text = job.location
        job.companyLogo?.let {
            Glide.with(iv_logo)
                .load(job.companyLogo)
                .apply(RequestOptions().placeholder(R.drawable.ic_work))
                .into(iv_logo)
        } ?: iv_logo.setImageResource(R.drawable.ic_work)

        tv_date.text = job.createdAt
        tv_job_url.text = job.url
        setOnClickListener { onJobClicked.invoke(job) }
    }

    companion object {
        fun create(parent: ViewGroup) = JobHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_job, parent, false)
        )
    }
}
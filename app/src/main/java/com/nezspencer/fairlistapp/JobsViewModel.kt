package com.nezspencer.fairlistapp

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nezspencer.fairlistapp.data.Job
import com.nezspencer.fairlistapp.repository.JobRepository
import com.nezspencer.fairlistapp.util.ResponseWrapper
import com.nezspencer.fairlistapp.util.Status
import kotlinx.coroutines.launch


class JobsViewModel @ViewModelInject constructor(private val jobRepository: JobRepository) :
    ViewModel() {

    private val _jobsLiveData = MutableLiveData<ResponseWrapper<List<Job>>>()
    val jobsLiveData: LiveData<ResponseWrapper<List<Job>>>
        get() = _jobsLiveData

    private val _jobDetailLiveData = MutableLiveData<ResponseWrapper<Job>>()
    val jobDetailLiveData: LiveData<ResponseWrapper<Job>>
        get() = _jobDetailLiveData

    fun getJobs(forceRefresh: Boolean = false) = viewModelScope.launch {
        _jobsLiveData.value =
            ResponseWrapper(Status.LOADING)
        _jobsLiveData.value = jobRepository.getJobs(forceRefresh)
    }

    fun getJob(jobId: String) = viewModelScope.launch {
        _jobDetailLiveData.value =
            ResponseWrapper(Status.LOADING)
        _jobDetailLiveData.value = jobRepository.getJob(jobId)
    }

}
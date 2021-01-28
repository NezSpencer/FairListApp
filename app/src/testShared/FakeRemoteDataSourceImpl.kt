package com.nezspencer.fairlistapp.data

import com.nezspencer.fairlistapp.datasource.RemoteDataSource
import com.nezspencer.fairlistapp.util.ResponseWrapper
import com.nezspencer.fairlistapp.util.Status

class FakeRemoteDataSourceImpl : RemoteDataSource {
    private val jobs = mutableListOf<Job>()
    private var status: Status = Status.SUCCESS
    private var errorMessage: String = "An error occurred."
    private var getAllJobsMethodHitCount = 0
    private var getSingleJobMethodHitCount = 0
    override suspend fun getJobs(): ResponseWrapper<List<Job>> {
        getAllJobsMethodHitCount++
        return if (status == Status.SUCCESS) ResponseWrapper(status, ArrayList(jobs))
        else ResponseWrapper(status, errorMessage = errorMessage)
    }

    override suspend fun getJob(id: String): ResponseWrapper<Job> {
        getSingleJobMethodHitCount++
        val job = jobs.find { it.id == id }
        return if (job == null) ResponseWrapper(
            Status.ERROR,
            errorMessage = errorMessage
        ) else
            ResponseWrapper(Status.SUCCESS, job)
    }

    fun setStatus(isSuccess: Boolean) {
        this@FakeRemoteDataSourceImpl.status = if (isSuccess) Status.SUCCESS else Status.ERROR
    }

    fun setErrorMessage(errorMessage: String) {
        this@FakeRemoteDataSourceImpl.errorMessage = errorMessage
    }

    fun createJobs(){
        jobs.add(createTestJobWithId("10"))
        jobs.add(createTestJobWithId("11"))
        jobs.add(createTestJobWithId("12"))
    }

    fun clearJobs(){
        jobs.clear()
    }

    fun getNumberOfHitsOnGetJobsMethod() = getAllJobsMethodHitCount

    fun getNumberOfHitsOnGetSingleJobMethod() = getSingleJobMethodHitCount

    fun clearGetJobsHitCount(){
        getAllJobsMethodHitCount = 0
    }

    fun clearGetSingleJobHitCount(){
        getSingleJobMethodHitCount = 0
    }
}
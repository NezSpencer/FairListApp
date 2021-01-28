package com.nezspencer.fairlistapp.datasource

import com.nezspencer.fairlistapp.data.Job
import com.nezspencer.fairlistapp.db.JobDao
import com.nezspencer.fairlistapp.util.ResponseWrapper
import com.nezspencer.fairlistapp.util.Status

interface LocalDataSource {
    suspend fun getJobs(): ResponseWrapper<List<Job>>

    suspend fun saveJobs(jobs: List<Job>)
}


class LocalDataSourceImpl(private val jobDao: JobDao) :
    LocalDataSource {
    override suspend fun getJobs(): ResponseWrapper<List<Job>> {
        val jobs = jobDao.getJobs()
        return ResponseWrapper(
            Status.SUCCESS,
            jobs
        )

    }

    override suspend fun saveJobs(jobs: List<Job>) {
        jobDao.saveJobs(jobs)
    }
}
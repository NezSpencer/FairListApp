package com.nezspencer.fairlistapp.repository

import com.nezspencer.fairlistapp.data.Job
import com.nezspencer.fairlistapp.datasource.LocalDataSource
import com.nezspencer.fairlistapp.datasource.RemoteDataSource
import com.nezspencer.fairlistapp.util.ResponseWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class JobRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) {

    suspend fun getJobs(forceRefresh: Boolean): ResponseWrapper<List<Job>> = withContext(Dispatchers.IO) {
        val cachedJobsResponse = localDataSource.getJobs()
        val timeToLive = cachedJobsResponse.data?.getOrNull(0)?.ttl ?: 0L
        val currentTime = System.currentTimeMillis()
        if (!forceRefresh && timeToLive > currentTime) {
            cachedJobsResponse
        } else {
            val newJobsResponse = remoteDataSource.getJobs()
            if (!newJobsResponse.data.isNullOrEmpty()) {
                newJobsResponse.data.map { it.ttl = currentTimePlus5mins() }
                localDataSource.saveJobs(newJobsResponse.data)
            }
            newJobsResponse
        }
    }

    suspend fun getJob(id: String) : ResponseWrapper<Job> = withContext(Dispatchers.IO) {
        remoteDataSource.getJob(id)
    }

    private fun currentTimePlus5mins(): Long {
        val fiveMinsInSeconds = 60_000 * 5
        return System.currentTimeMillis() + fiveMinsInSeconds
    }
}
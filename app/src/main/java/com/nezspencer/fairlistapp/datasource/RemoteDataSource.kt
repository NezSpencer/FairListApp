package com.nezspencer.fairlistapp.datasource

import com.haroldadmin.cnradapter.NetworkResponse
import com.nezspencer.fairlistapp.data.Job
import com.nezspencer.fairlistapp.network.JobApi
import com.nezspencer.fairlistapp.util.ResponseWrapper
import com.nezspencer.fairlistapp.util.Status
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface RemoteDataSource {
    suspend fun getJobs(): ResponseWrapper<List<Job>>

    suspend fun getJob(id: String): ResponseWrapper<Job>
}

class RemoteDataSourceImpl(private val api: JobApi) :
    RemoteDataSource {
    override suspend fun getJobs(): ResponseWrapper<List<Job>> = withContext(Dispatchers.IO) {
        when (val response = api.getJobs()) {
            is NetworkResponse.Success -> {
                ResponseWrapper(
                    Status.SUCCESS,
                    response.body
                )
            }
            is NetworkResponse.ServerError -> {
                ResponseWrapper(
                    Status.ERROR,
                    errorMessage = response.toString()
                )
            }
            is NetworkResponse.NetworkError,
            is NetworkResponse.UnknownError -> {
                ResponseWrapper(
                    Status.ERROR,
                    errorMessage = response.toString()
                )
            }
        }
    }

    override suspend fun getJob(id: String): ResponseWrapper<Job> = withContext(Dispatchers.IO) {
        when (val response = api.getJob(id)) {
            is NetworkResponse.Success -> {
                ResponseWrapper(
                    Status.SUCCESS,
                    response.body
                )
            }
            is NetworkResponse.ServerError -> {
                ResponseWrapper(
                    Status.ERROR,
                    errorMessage = response.toString()
                )
            }
            is NetworkResponse.NetworkError,
            is NetworkResponse.UnknownError -> {
                ResponseWrapper(
                    Status.ERROR,
                    errorMessage = "Something went wrong. Retry"
                )
            }
        }
    }
}
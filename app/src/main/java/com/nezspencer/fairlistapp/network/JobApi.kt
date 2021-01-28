package com.nezspencer.fairlistapp.network

import com.haroldadmin.cnradapter.NetworkResponse
import com.nezspencer.fairlistapp.data.ErrorResponse
import com.nezspencer.fairlistapp.data.Job
import retrofit2.http.GET
import retrofit2.http.Path

interface JobApi {

    @GET("/positions.json?description=python&page=1")
    suspend fun getJobs(): NetworkResponse<List<Job>, ErrorResponse>

    @GET("/positions/{jobId}.json")
    suspend fun getJob(@Path("jobId") jobId: String): NetworkResponse<Job, ErrorResponse>
}
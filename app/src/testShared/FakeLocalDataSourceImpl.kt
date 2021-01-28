package com.nezspencer.fairlistapp.data

import com.nezspencer.fairlistapp.datasource.LocalDataSource
import com.nezspencer.fairlistapp.util.ResponseWrapper
import com.nezspencer.fairlistapp.util.Status

class FakeLocalDataSourceImpl : LocalDataSource {
    private val cache = mutableListOf<Job>()
    private var getJobsHits = 0
    private var saveJobsHits = 0
    override suspend fun getJobs(): ResponseWrapper<List<Job>> {
        getJobsHits++
        return ResponseWrapper(Status.SUCCESS, ArrayList(cache))
    }

    override suspend fun saveJobs(jobs: List<Job>) {
        saveJobsHits++
        cache.addAll(jobs)
    }

    fun createRecentJobs(){
        cache.add(createTestJobWithIdAndExpirationDate("1", getTimeOneHourAheadInMilliSecs()))
        cache.add(createTestJobWithIdAndExpirationDate("2", getTimeOneHourAheadInMilliSecs()))
        cache.add(createTestJobWithIdAndExpirationDate("3", getTimeOneHourAheadInMilliSecs()))
    }

    fun createExpiredJobs(){
        cache.add(createTestJobWithId("1"))
        cache.add(createTestJobWithId("2"))
        cache.add(createTestJobWithId("3"))
    }

    fun clear(){
        getJobsHits = 0
        saveJobsHits = 0
        cache.clear()
    }

    fun resetGetJobsHitCount(){
        getJobsHits = 0
    }

    fun resetSaveJobsHitCount(){
        saveJobsHits = 0
    }

    private fun getTimeOneHourAheadInMilliSecs(): Long{
        val oneHourinMilliSecs = 3600000
        return System.currentTimeMillis() + oneHourinMilliSecs
    }

    fun getNumberOfHitsOnGetJobsMethod() = getJobsHits

    fun getNumberOfHitsOnSaveJobsMethod() = saveJobsHits
}
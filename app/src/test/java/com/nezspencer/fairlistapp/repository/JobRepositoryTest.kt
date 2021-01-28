package com.nezspencer.fairlistapp.repository

import com.nezspencer.fairlistapp.data.FakeLocalDataSourceImpl
import com.nezspencer.fairlistapp.data.FakeRemoteDataSourceImpl
import com.nezspencer.fairlistapp.util.Status
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class JobRepositoryTest {
    private lateinit var localDataSource: FakeLocalDataSourceImpl
    private lateinit var remoteDataSource: FakeRemoteDataSourceImpl
    private lateinit var repository: JobRepository

    @Before
    fun setup() {
        localDataSource = FakeLocalDataSourceImpl()
        remoteDataSource = FakeRemoteDataSourceImpl()
        repository = JobRepository(remoteDataSource, localDataSource)
    }

    @Test
    @Throws(Exception::class)
    fun givenNoJobExistsInLocalDataSource_verifyJobsAreFetchedFromRemoteDataSource() = runBlocking{
        remoteDataSource.createJobs()
        val jobsFromRemote = remoteDataSource.getJobs().data!!
        val fetchedJobs = repository.getJobs(false).data!!
        Assert.assertEquals(jobsFromRemote.size, fetchedJobs.size)
        Assert.assertTrue(jobsFromRemote.isNotEmpty())
    }

    @Test
    @Throws(Exception::class)
    fun givenNoJobExistsInLocalDataSource_verifyJobsFetchedMatchJobsFromRemoteDataSource() = runBlocking{
        remoteDataSource.createJobs()
        val jobsFromRemote = remoteDataSource.getJobs().data!!
        val fetchedJobs = repository.getJobs(false).data!!
        Assert.assertEquals(jobsFromRemote.size, fetchedJobs.size)
        Assert.assertEquals(jobsFromRemote[0].id, fetchedJobs[0].id)
        Assert.assertEquals(jobsFromRemote[1].id, fetchedJobs[1].id)
        Assert.assertEquals(jobsFromRemote[2].id, fetchedJobs[2].id)
    }

    @Test
    @Throws(Exception::class)
    fun givenNoJobExistsInLocalDataSource_verifyLocalDataSourceIsCalledBeforeCallingRemoteDataSource() {
        runBlocking{
            remoteDataSource.createJobs()
            repository.getJobs(false)
            Assert.assertTrue(localDataSource.getNumberOfHitsOnGetJobsMethod() == 1)
        }
    }

    @Test
    @Throws(Exception::class)
    fun givenNoJobExistsInLocalDataSourceAndNoRemoteDataSourceReturnsNoJob_verifyNothingIsSavedToLocalDataSource() = runBlocking{
        val jobsFromRemote = remoteDataSource.getJobs().data!!
        remoteDataSource.clearGetJobsHitCount()
        localDataSource.clear()
        val fetchedJobs = repository.getJobs(false).data!!
        Assert.assertTrue(remoteDataSource.getNumberOfHitsOnGetJobsMethod() == 1)
        Assert.assertEquals(jobsFromRemote.size, fetchedJobs.size)
        Assert.assertTrue(fetchedJobs.isEmpty())
        Assert.assertTrue(localDataSource.getNumberOfHitsOnSaveJobsMethod() == 0)
        Assert.assertTrue(localDataSource.getJobs().data.isNullOrEmpty())
    }

    @Test
    @Throws(Exception::class)
    fun givenNoJobExistsInLocalDataSourceAndRemoteDataSourceReturnsJobs_verifyJobsAreSavedToTheLocalDataSource() {
        runBlocking{
            remoteDataSource.createJobs()
            repository.getJobs(false)
            Assert.assertTrue(localDataSource.getNumberOfHitsOnSaveJobsMethod() == 1)
            val cachedJobs = localDataSource.getJobs().data!!
            Assert.assertTrue(cachedJobs.isNotEmpty())
        }
    }


    @Test
    @Throws(Exception::class)
    fun givenJobExistsInLocalDataSourceAndCacheExpirationTimeHasntBeenReachedAndForceRefreshIsFalse_verifyJobsAreFetchedFromTheLocalDataSource() {
        runBlocking{
            localDataSource.createRecentJobs()
            val cachedJobs = localDataSource.getJobs().data!!
            localDataSource.resetGetJobsHitCount()
            val jobs = repository.getJobs(false).data!!
            Assert.assertTrue(remoteDataSource.getNumberOfHitsOnGetJobsMethod() == 0)
            Assert.assertTrue(localDataSource.getNumberOfHitsOnGetJobsMethod() == 1)
            Assert.assertTrue(jobs.size == cachedJobs.size)
            Assert.assertTrue(jobs.isNotEmpty())
            Assert.assertEquals(cachedJobs[0].id, jobs[0].id)
            Assert.assertEquals(cachedJobs[1].id, jobs[1].id)
            Assert.assertEquals(cachedJobs[2].id, jobs[2].id)
        }
    }

    @Test
    @Throws(Exception::class)
    fun givenJobExistsInLocalDataSourceAndCacheExpirationTimeHasntBeenReachedButForceRefreshIsTrue_verifyJobsAreFetchedFromTheRemoteDataSource() {
        runBlocking{
            localDataSource.createRecentJobs()
            remoteDataSource.createJobs()
            val jobsInRemote = remoteDataSource.getJobs().data!!
            remoteDataSource.clearGetJobsHitCount()
            val jobs = repository.getJobs(true).data!!
            Assert.assertTrue(remoteDataSource.getNumberOfHitsOnGetJobsMethod() == 1)
            Assert.assertTrue(jobs.size == jobsInRemote.size)
            Assert.assertTrue(jobs.isNotEmpty())
            Assert.assertEquals(jobsInRemote[0].id, jobs[0].id)
            Assert.assertEquals(jobsInRemote[1].id, jobs[1].id)
            Assert.assertEquals(jobsInRemote[2].id, jobs[2].id)
        }
    }

    @Test
    @Throws(Exception::class)
    fun givenJobExistsInLocalDataSourceAndCacheExpirationTimeHasBeenReachedButForceRefreshIsFalse_verifyJobsAreFetchedFromTheRemoteDataSource() {
        runBlocking{
            remoteDataSource.createJobs()
            val jobsInRemote = remoteDataSource.getJobs().data!!
            remoteDataSource.clearGetJobsHitCount()
            val jobs = repository.getJobs(false).data!!
            Assert.assertTrue(remoteDataSource.getNumberOfHitsOnGetJobsMethod() == 1)
            Assert.assertTrue(jobs.size == jobsInRemote.size)
            Assert.assertTrue(jobs.isNotEmpty())
            Assert.assertEquals(jobsInRemote[0].id, jobs[0].id)
            Assert.assertEquals(jobsInRemote[1].id, jobs[1].id)
            Assert.assertEquals(jobsInRemote[2].id, jobs[2].id)
        }
    }

    @Test
    @Throws(Exception::class)
    fun givenGetJobsFetchFromRemoteIsSuccessful_verifyJobsAreReturnedWithSuccessStatus() {
        runBlocking{
            remoteDataSource.createJobs()
            remoteDataSource.setStatus(true)
            val jobsResponse = repository.getJobs(false)
            Assert.assertEquals(Status.SUCCESS, jobsResponse.status)
        }
    }

    @Test
    @Throws(Exception::class)
    fun givenGetJobsFetchFromRemoteFails_verifyJobsAreReturnedWithErrorStatus() {
        runBlocking{
            remoteDataSource.createJobs()
            remoteDataSource.setStatus(false)
            val jobsResponse = repository.getJobs(false)
            Assert.assertEquals(Status.ERROR, jobsResponse.status)
        }
    }

    @Test
    @Throws(Exception::class)
    fun givenGetJobsFetchFromRemoteFails_verifyErrorMessageReturnedMatchesTheOneSentFromRemoteDataSource() {
        runBlocking{
            remoteDataSource.createJobs()
            val errorMessage = "This is a test error"
            remoteDataSource.setStatus(false)
            remoteDataSource.setErrorMessage(errorMessage)
            val jobsResponse = repository.getJobs(false)
            Assert.assertTrue(jobsResponse.status == Status.ERROR)
            Assert.assertEquals(errorMessage, jobsResponse.errorMessage)
        }
    }

    @Test
    @Throws(Exception::class)
    fun givenJobsExistInRemoteDataSourceAndJobIdIsValid_verifyTheGivenJobIsReturned() {
        runBlocking{
            remoteDataSource.createJobs()
            val jobId = "10"
            val job = repository.getJob(jobId).data
            Assert.assertTrue(remoteDataSource.getNumberOfHitsOnGetSingleJobMethod() == 1)
            Assert.assertTrue(job != null)
            Assert.assertEquals(jobId, job?.id)
        }
    }

    @Test
    @Throws(Exception::class)
    fun givenJobsExistInRemoteDataSourceAndJobIdIsValid_verifySuccessStatusIsReturned() {
        runBlocking{
            remoteDataSource.createJobs()
            val jobId = "10"
            val jobResponse = repository.getJob(jobId)
            Assert.assertTrue(remoteDataSource.getNumberOfHitsOnGetSingleJobMethod() == 1)
            Assert.assertEquals(Status.SUCCESS, jobResponse.status)
        }
    }

    @Test
    @Throws(Exception::class)
    fun givenJobIdIsInvalid_verifyNoJobIsReturned() {
        runBlocking{
            remoteDataSource.createJobs()
            val jobId = "21"
            val job = repository.getJob(jobId).data
            Assert.assertTrue(remoteDataSource.getNumberOfHitsOnGetSingleJobMethod() == 1)
            Assert.assertTrue(job == null)
        }
    }

    @Test
    @Throws(Exception::class)
    fun givenJobIdIsInvalid_verifyErrorStatusIsReturned() {
        runBlocking{
            remoteDataSource.createJobs()
            val jobId = "21"
            val jobResponse = repository.getJob(jobId)
            Assert.assertTrue(remoteDataSource.getNumberOfHitsOnGetSingleJobMethod() == 1)
            Assert.assertEquals(Status.ERROR, jobResponse.status)
        }
    }

    @Test
    @Throws(Exception::class)
    fun givenJobIdIsInvalid_verifyErrorMessageReturnedMatchesTheOneSentFromRemoteDataSource() {
        runBlocking{
            remoteDataSource.createJobs()
            val jobId = "21"
            val errorMessage = "This test job with id $jobId does not exist"
            remoteDataSource.setErrorMessage(errorMessage)
            val jobResponse = repository.getJob(jobId)
            Assert.assertTrue(remoteDataSource.getNumberOfHitsOnGetSingleJobMethod() == 1)
            Assert.assertEquals(errorMessage, jobResponse.errorMessage)
        }
    }


}
package com.nezspencer.fairlistapp.datasource

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.nezspencer.fairlistapp.data.Job
import com.nezspencer.fairlistapp.data.createTestJobWithId
import com.nezspencer.fairlistapp.db.FairListDb
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class LocalDataSourceImplTest {
    private lateinit var db: FairListDb
    private lateinit var localDataSourceImpl: LocalDataSourceImpl

    @Before
    fun initializeDb() {
        db = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().targetContext,
            FairListDb::class.java
        )
            .build()
        localDataSourceImpl = LocalDataSourceImpl(db.jobDao())
    }

    @After
    @Throws(IOException::class)
    fun tearDown() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun given_nonEmptyJobListToSave_verifyThatJobsAreSaved() {
        runBlocking {
            val jobsToSave =
                listOf(createTestJobWithId("1"), createTestJobWithId("2"))
            localDataSourceImpl.saveJobs(jobsToSave)
            val savedJobs = localDataSourceImpl.getJobs()
            assertEquals(savedJobs.data!!.size, jobsToSave.size)
            assertEquals(savedJobs.data!![0].id, jobsToSave[0].id)
            assertEquals(savedJobs.data!![1].id, jobsToSave[1].id)
        }
    }

    @Test
    @Throws(Exception::class)
    fun given_EmptyJobListToSave_verifyThatNoJobisSaved() {
        runBlocking {
            val emptyListToSave = listOf<Job>()
            localDataSourceImpl.saveJobs(emptyListToSave)
            val savedJobs = localDataSourceImpl.getJobs()
            assertEquals(savedJobs.data!!.size, 0)
        }
    }

    @Test
    @Throws(Exception::class)
    fun given_noJobsExist_verifyThatNoJobisReturned() {
        runBlocking {
            val savedJobs = localDataSourceImpl.getJobs()
            assertEquals(savedJobs.data!!.size, 0)
        }
    }

    @Test
    @Throws(Exception::class)
    fun given_savedJobsExist_verifyAllSavedJobsAreReturned() {
        runBlocking {
            val jobsToSave =
                listOf(createTestJobWithId("1"), createTestJobWithId("2"))
            localDataSourceImpl.saveJobs(jobsToSave)
            val savedJobs = localDataSourceImpl.getJobs()
            assertEquals(savedJobs.data!!.size, jobsToSave.size)
            assertEquals(savedJobs.data!![0].id, jobsToSave[0].id)
            assertEquals(savedJobs.data!![1].id, jobsToSave[1].id)
        }
    }
}
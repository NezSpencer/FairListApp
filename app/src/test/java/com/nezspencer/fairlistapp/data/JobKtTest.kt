package com.nezspencer.fairlistapp.data


import org.junit.Assert
import org.junit.Test


class JobKtTest {

    @Test
    fun givenJobMatchesOtherJob_hasSameContentReturnsTrue() {
        val job1 = createTestJobWithDescription("This is job A")
        val job2 = createTestJobWithDescription("This is job A")
        Assert.assertTrue(job1.hasSameContent(job2))
    }

    @Test
    fun givenSameJobWithDifferentDescriptions_hasSameContentReturnsFalse(){
        val job1 = createTestJobWithDescription("This is job 1")
        val job2 = job1.copy(description = "This is job 1 modified")
        Assert.assertFalse(job1.hasSameContent(job2))
    }

    @Test
    fun givenDifferentJobs_hasSameContentReturnsFalse(){
        val job1 = createTestJobWithDescription("This is job 1")
        val job2 = createTestJobWithId("21")
        Assert.assertFalse(job1.hasSameContent(job2))
    }
}
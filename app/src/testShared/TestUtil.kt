package com.nezspencer.fairlistapp.data

fun createTestJobWithDescription(description: String): Job {
    return Job("1", "full time", "www", "today", "", "", "", "", description, "","")
}

fun createTestJobWithId(id: String): Job {
    return Job(id, "full time", "www", "today", "", "", "", "", "description", "","")
}

fun createTestJobWithIdAndExpirationDate(id: String, expiration: Long): Job {
    return Job(id, "full time", "www", "today", "", "", "", "", "description", "","", expiration)
}
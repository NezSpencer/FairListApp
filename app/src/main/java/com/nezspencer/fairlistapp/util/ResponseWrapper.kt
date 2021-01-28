package com.nezspencer.fairlistapp.util

data class ResponseWrapper<T>(val status: Status, val data: T? = null, val errorMessage: String? = null)

enum class Status {
    LOADING,
    ERROR,
    SUCCESS
}
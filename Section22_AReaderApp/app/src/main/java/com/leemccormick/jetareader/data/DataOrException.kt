package com.leemccormick.jetareader.data

// Use T for genetic, Use to this call for api object
data class DataOrException<T, Boolean, E : Exception>(
    var data: T? = null,
    var loading: kotlin.Boolean? = null,
    var e: E? = null
)

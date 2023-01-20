package com.leemccormick.jetareader.screens.login

import android.os.Message

data class LoadingState(val state: Status, val message: String? = null) {
    companion object {
        val SUCCESS = LoadingState(Status.SUCCESS)
        val FAILED = LoadingState(Status.FAILED)
        val LOADING = LoadingState(Status.LOADING)
        val IDLE = LoadingState(Status.IDLE)
    }

    enum class Status {
        SUCCESS,
        FAILED,
        LOADING,
        IDLE
    }
}

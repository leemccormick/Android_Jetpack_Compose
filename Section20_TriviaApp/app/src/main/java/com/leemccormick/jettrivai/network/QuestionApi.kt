package com.leemccormick.jettrivai.network

import com.leemccormick.jettrivai.model.Question
import retrofit2.http.GET
import javax.inject.Singleton

@Singleton
interface QuestionApi {
    @GET("world.json")
    suspend fun getAllQuestions() : Question
}
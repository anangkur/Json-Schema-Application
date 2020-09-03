package com.anangkur.jsonschemeapplication.data.remote

import com.anangkur.jsonschemeapplication.data.remote.model.Questions
import retrofit2.http.GET

interface DummyService {
    @GET("json-scheme-2")
    suspend fun getQuestion(): Questions
}
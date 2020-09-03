package com.anangkur.jsonschemeapplication.data.remote

import com.anangkur.jsonschemeapplication.data.Repository
import com.anangkur.jsonschemeapplication.data.remote.RetrofitServices.getApiService
import com.anangkur.jsonschemeapplication.data.remote.model.Questions

class RemoteDataSource(
    private val dummyService: DummyService
): Repository {

    companion object {
        var INSTANCE: RemoteDataSource? = null
        fun getInstance(): RemoteDataSource {
            if (INSTANCE == null) {
                INSTANCE = RemoteDataSource(getApiService())
            }
            return INSTANCE!!
        }
    }

    override suspend fun getQuestion(): Questions {
        return dummyService.getQuestion()
    }
}
package com.anangkur.jsonschemeapplication.data

import com.anangkur.jsonschemeapplication.data.local.LocalDataSource
import com.anangkur.jsonschemeapplication.data.remote.RemoteDataSource
import com.anangkur.jsonschemeapplication.data.remote.model.Questions

class DataSource(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
): Repository {

    companion object {
        private var INSTANCE: DataSource? = null
        fun getInstance(): DataSource {
            if (INSTANCE == null) {
                INSTANCE = DataSource(
                    RemoteDataSource.getInstance(),
                    LocalDataSource.getInstance()
                )
            }
            return INSTANCE!!
        }
    }

    override suspend fun getQuestion(): Questions {
        return remoteDataSource.getQuestion()
    }
}
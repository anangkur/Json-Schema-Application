package com.anangkur.jsonschemeapplication.data.local

import com.anangkur.jsonschemeapplication.data.Repository
import com.anangkur.jsonschemeapplication.data.remote.model.Questions

class LocalDataSource: Repository {

    companion object {
        private var INSTANCE: LocalDataSource? = null
        fun getInstance(): LocalDataSource {
            if (INSTANCE == null) {
                INSTANCE = LocalDataSource()
            }
            return INSTANCE!!
        }
    }

    override suspend fun getQuestion(): Questions {
        throw UnsupportedOperationException()
    }
}
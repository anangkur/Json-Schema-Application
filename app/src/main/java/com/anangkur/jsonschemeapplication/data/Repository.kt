package com.anangkur.jsonschemeapplication.data

import com.anangkur.jsonschemeapplication.data.remote.model.Questions

interface Repository {

    suspend fun getQuestion(): Questions
}
package com.anangkur.jsonschemeapplication.utils

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser

/**
 * Created by ilgaputra15
 * on Saturday, 28/03/2020 19.51
 * Mobile Engineer - https://github.com/ilgaputra15
 **/
object Converts {
    private val gson = Gson()
    fun convertMapToJsonObject(data: Map<Any?, Any?>) : JsonObject {
        return JsonParser().parse(gson.toJson(data).toString()).asJsonObject
    }
}
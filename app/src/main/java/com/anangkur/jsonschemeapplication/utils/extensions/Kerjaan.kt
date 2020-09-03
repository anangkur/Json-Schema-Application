package com.anangkur.jsonschemeapplication.utils.extensions

import com.anangkur.jsonschemeapplication.R
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

/**
 * Created by ilgaputra15
 * on Friday, 20/03/2020 23.39
 * Mobile Engineer - https://github.com/ilgaputra15
 **/

fun String.getCategoryName(): String {
    return when {
        this.contains("time") -> "Check-In"
        this.contains("task") -> "Form-Submission"
         else -> ""
    }
}

fun String.isTaskSubmission(): Boolean {
    return when {
        this.contains("task") -> true
        else -> false
    }
}

fun String.getCategoryImage(): Int {
    return when {
        this.contains("time") -> R.drawable.ic_badge_time
        else -> R.drawable.ic_badge_task
    }
}

fun String.setJobTimeState(): String {
    return when {
        this.contains("check_in") -> "Check In"
        this.contains("check_out") -> "Check Out"
        else -> this
    }
}

fun String.isCheckIn(): Boolean {
    return this.contains("check_in")
}

fun String.isJSONValid(): Boolean {
    try { JSONObject(this)
    } catch (ex: JSONException) {
        try {
            JSONArray(this)
        } catch (ex1: JSONException) {
            return false
        }
    }
    return true
}

val JsonElement?.handled: JsonElement?
    get() = when {
        this?.isJsonNull == true || this?.isJsonObject == true -> JsonObject()
        this?.isJsonArray == true -> this.asJsonArray
        else -> this
    }
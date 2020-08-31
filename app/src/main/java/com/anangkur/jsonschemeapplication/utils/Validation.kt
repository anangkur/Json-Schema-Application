package com.anangkur.jsonschemeapplication.utils

import java.util.regex.Pattern

/**
 * Created by ilgaputra15
 * on Saturday, 28/03/2020 21.24
 * Mobile Engineer - https://github.com/ilgaputra15
 **/

object Validation {
    fun isStringBase64(data: String): Boolean {
        return data.contains("Base64:")
    }

    fun isEmailValid(email: String): Boolean {
        val pattern = Pattern.compile("^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
        val matcher = pattern.matcher(email)
        if (!matcher.matches()) {
            return false
        }
        return true
    }
}
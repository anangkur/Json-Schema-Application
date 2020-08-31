package com.anangkur.jsonschemeapplication.utils

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by ilgaputra15
 * on Saturday, 21/03/2020 21.02
 * Mobile Engineer - https://github.com/ilgaputra15
 **/
object DateHelper {

    private const val language = "in"
    private const val pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSZ"

    fun ddMMMMyyyyFormat(valueDate: String): String {
        val responseFormat = SimpleDateFormat(pattern, Locale(language))
        val showFormat = SimpleDateFormat("dd MMMM yyyy", Locale(language))
        val dateTimeOfValueDate: Date = responseFormat.parse(valueDate)!!
        return showFormat.format(dateTimeOfValueDate)
    }

    fun timeSimpleFormat(valueDate: String): String {
        val responseFormat = SimpleDateFormat(pattern, Locale(language))
        val showFormat = SimpleDateFormat("hh.mm a", Locale(language))
        val dateTimeOfValueDate: Date = responseFormat.parse(valueDate)!!
        return showFormat.format(dateTimeOfValueDate)
    }

    fun convertDateToString(value: Date, pattern: String): String {
        val formatter: DateFormat = SimpleDateFormat(pattern, Locale(language))
        return formatter.format(value)
    }
}
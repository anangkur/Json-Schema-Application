package com.anangkur.jsonschemeapplication.utils

import com.anangkur.jsonschemeapplication.model.AnswerSchemaRule
import com.anangkur.jsonschemeapplication.model.JsonSchemaRule
import com.anangkur.jsonschemeapplication.model.UiSchemaRule
import com.anangkur.jsonschemeapplication.utils.extensions.handled
import com.google.gson.JsonElement
import com.google.gson.JsonObject

/**
 * Created by ilgaputra15
 * on Sunday, 22/03/2020 20.20
 * Mobile Engineer - https://github.com/ilgaputra15
 **/

fun provideJsonSchema(jsonRule: JsonObject) =
    JsonSchemaRule(
        title = jsonRule["title"]?.asString!!,
        type = jsonRule["type"]?.asString!!,
        minimum = jsonRule["minimum"]?.asInt,
        maximum = jsonRule["maximum"]?.asInt,
        items = jsonRule["items"]?.asJsonObject,
        enum = jsonRule["enum"]?.asJsonArray
    )

fun provideUiSchema(uiRule: JsonObject) =
    UiSchemaRule(
        order = uiRule["ui:order"]?.asInt,
        uiTitle = uiRule["ui:title"]?.asString,
        uiWidget = uiRule["ui:widget"]?.asString,
        uiHelp = uiRule["ui:help"]?.asString,
        uiHelpImage = uiRule["ui:help_image"]?.asString,
        uiPlaceholder = uiRule["ui:placeholder"]?.asString,
        uiDescription = uiRule["ui:description"]?.asString,
        uiOptions = uiRule["ui:options"]?.asJsonObject
    )

fun provideAnswerSchema(answerRule: JsonElement?, answerStatusRule: JsonObject?): AnswerSchemaRule =
    AnswerSchemaRule(
        answer = answerRule?.handled,
        status = answerStatusRule?.get("status")?.asString,
        rejectionReason = answerStatusRule?.get("rejectionReason")?.asString
    )
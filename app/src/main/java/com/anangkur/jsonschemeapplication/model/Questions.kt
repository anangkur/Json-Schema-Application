package com.anangkur.jsonschemeapplication.model

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject

/**
 * Created by ilgaputra15
 * on Sunday, 22/03/2020 20.18
 * Mobile Engineer - https://github.com/ilgaputra15
 **/

data class JsonSchemaRule(
    val title: String, // as question/label text
    val type: String, // as data `type` holder, not ui:widget type
    val description: String? = "", // as `description` above field
    val minimum: Int? = 1, // min counts
    val maximum: Int?, // max counts
    val minLength: Int? = 1, // min char length
    val maxLength: Int? = 1, // max char length
    val items: JsonObject? = JsonObject(), // as data holder for: checkboxes or multiple files
    val enum: JsonArray? = JsonArray(), // as answer enums/list: select (dropdown), radio
    val enumNames: JsonArray? = JsonArray() // as answer enums/list: select (dropdown), radio
)

data class UiSchemaRule(
    val order: Int? = 0,
    val uiTitle: String? = null, // `ui:title` replace `title` json schema
    val uiWidget: String? = null, // `ui:widget` to define an android view/widget type
    val uiHelp: String? = null, // `ui:help` as caption/error text
    val uiPlaceholder: String? = null, // replace `default` json schema, exist only in: select
    val uiDescription: String? = null, // replace `description` json schema
    val uiHelpImage: String? = null, // replace `description` json schema
    val uiOptions: JsonObject? = JsonObject() // custom option/rule
)

data class AnswerSchemaRule(
    val answer: JsonElement? = null, // answer data holder for each field
    val status: String? = null, // to check each answer field status
    val rejectionReason: String? = null // replacing field hint as error message
)
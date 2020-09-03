package com.anangkur.jsonschemeapplication.model

/**
 * Created by ilgaputra15
 * on Sunday, 22/03/2020 20.39
 * Mobile Engineer - https://github.com/ilgaputra15
 **/

data class DynamicView(
    val componentName: String,
    val jsonSchema: JsonSchemaRule,
    val uiSchemaRule: UiSchemaRule,
    val answerSchemaRule: AnswerSchemaRule?,
    val isRequired: Boolean,
    var isValidated: Boolean = true,
    var value: Any? = null,
    var fileName: String? = null,
    var preview: Any? = null,
    var isError: Boolean = false,
    var errorValue: String? = null,
    var isEnable: Boolean = true
)
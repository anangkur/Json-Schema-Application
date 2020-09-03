package com.anangkur.jsonschemeapplication.data.remote.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by ilgaputra15
 * on Wednesday, 18/03/2020 19.51
 * Mobile Engineer - https://github.com/ilgaputra15
 **/

data class Submission(
    @SerializedName("id") val id: Int,
    @SerializedName("submission_id") val submission_id: Int,
    @SerializedName("agent_id") val agent_id: Int,
    @SerializedName("status") val status: String,
    @SerializedName("answer_schema") val answer_schema: Map<Any,Any>?,
    @SerializedName("project_name") val project_name: String,
    @SerializedName("project_status") val project_status: String,
    @SerializedName("project_category") val project_category: String,
    @SerializedName("created_at") val created_at: String,
    @SerializedName("updated_at") val updated_at: String?,
    @SerializedName("deleted_at") val deleted_at: String?
)

data class Training(
    @SerializedName("project_id") val project_id: Int,
    @SerializedName("video_url") val video_url: String?,
    @SerializedName("photo_url") val photo_url: String,
    @SerializedName("description") val description: String,
    @SerializedName("id") val id: Int,
    @SerializedName("created_at") val created_at: String,
    @SerializedName("updated_at") val updated_at: String,
    @SerializedName("deleted_at") val deleted_at: String
)

data class Questions(
    @SerializedName("id") val id: Int,
    @SerializedName("project_id") val project_id: Int,
    @SerializedName("project_title") val project_title: String,
    @SerializedName("question_schema") val question_schema: QuestionSchema,
    @SerializedName("ui_schema") val ui_schema: Map<Any?,Any?>,
    @SerializedName("prefered_answer") val prefered_answer: Map<Any,Any>?,
    @SerializedName("criteria_schema") val criteria_schema: Map<Any,Any>?,
    @SerializedName("created_at") val created_at: String,
    @SerializedName("updated_at") val updated_at: String,
    @SerializedName("deleted_at") val deleted_at: String
)

data class QuestionSchema(
    @SerializedName("type") val type: String,
    @SerializedName("title") val title: String,
    @SerializedName("required") val required: List<String>,
    @SerializedName("properties") val properties: Map<Any?,Any?>,
    @SerializedName("description") val description: String
)

data class SubmissionFileBody(
    @SerializedName("file_name") val file_name: String,
    @SerializedName("file") val file: String
)

data class SubmissionFileResponse(
    @SerializedName("agent_submission_id") val agent_submission_id: Int?,
    @SerializedName("file_name") val file_name: String,
    @SerializedName("file_size") val file_size: Double,
    @SerializedName("mime_type") val mime_type: String,
    @SerializedName("url") val url: String,
    @SerializedName("id") val id: Int,
    @SerializedName("created_at") val created_at: String,
    @SerializedName("updated_at") val updated_at: String?,
    @SerializedName("deleted_at") val deleted_at: String?
)

data class SubmissionFileProcess(
    val key: String,
    val response: SubmissionFileResponse?
)

data class PostSubmissionBody(
    @SerializedName("submission_id") val submission_id: Int,
    @SerializedName("answer_schema") val answer_schema: Map<String, Any?>,
    @SerializedName("update_id") val update_id: Int?
)

data class PostSubmissionResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("agent_id") val agent_id: Int,
    @SerializedName("status") val status: String,
    @SerializedName("rejected_reason_schema") val rejected_reason_schema: Any?,
    @SerializedName("answer_schema") val answer_schema: Map<String, Any?>,
    @SerializedName("created_at") val created_at: String,
    @SerializedName("updated_at") val updated_at: String?,
    @SerializedName("deleted_at") val deleted_at: String?
)

data class UpdateSubmissionFileBody(
    @SerializedName("agent_submission_id") val agent_submission_id: Int
)

data class UpdateSubmissionFileResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("agent_submission_id") val agent_submission_id: Int,
    @SerializedName("file_name") val file_name: String,
    @SerializedName("url") val url: String
)

@Parcelize
data class StateTimeSubmissionResponse(
    @SerializedName("project_id") val project_id: Int,
    @SerializedName("state") val state: String,
    @SerializedName("submission_id") val submission_id: Int
) : Parcelable

data class TimeSubmissionBody(
    @SerializedName("submission_id") val submission_id: Int,
    @SerializedName("lat") val lat: Double,
    @SerializedName("long") val long: Double,
    @SerializedName("type") val type: String,
    @SerializedName("check_with") val check_with: String,
    @SerializedName("code") val code: String
)

data class SubmissionDetail(
    @SerializedName("id") val id: Int,
    @SerializedName("submission_id") val submission_id: Int,
    @SerializedName("agent_id") val agent_id: Int,
    @SerializedName("status") val status: String,
    @SerializedName("answer_schema") val answer_schema: Map<String,Any>,
    @SerializedName("rejected_reason_schema") val rejected_reason_schema: RejectedReasonSchema?,
    @SerializedName("project_title") val project_title: String,
    @SerializedName("project_id") val project_id: Int,
    @SerializedName("project_status") val project_status: String,
    @SerializedName("created_at") val created_at: String,
    @SerializedName("updated_at") val updated_at: String?,
    @SerializedName("deleted_at") val deleted_at: String?,
    @SerializedName("required_checkout") val required_checkout: Boolean,
    @SerializedName("max_submission") val max_submission: Int?,
    @SerializedName("count_agent_submission") val count_agent_submission: Int

)

data class RejectedReasonSchema(
    @SerializedName("all") val all: RejectedAll?,
    @SerializedName("data_point") val data_point: Map<Any?,Any?>?
)

data class RejectedAll(
    @SerializedName("reason") val reason: String,
    @SerializedName("additional_reason") val additional_reason: String
)

@Parcelize
data class CriteriaSubmission(
    val title: String,
    val help: String?,
    val helpImage: String?
) : Parcelable
package it.piriottu.ktorkotlin.models

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class PostResponse(
    @SerializedName("body")
    val body: String,
    val id: Int,
    val title: String,
    val userId: Int
)
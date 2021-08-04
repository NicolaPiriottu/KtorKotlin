package it.piriottu.ktorkotlin.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Post(
    @SerializedName("body")
    val body: String,
    val id: Int,
    val title: String,
    val userId: Int
)
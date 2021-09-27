package it.piriottu.ktorkotlin.models

import com.google.gson.annotations.SerializedName
/**
 * Created by OverApp on 21/09/21.
 *  Visit https://www.overapp.com/
 */

data class PostResponse(
    @SerializedName("body")
    val body: String,
    val id: Int,
    val title: String,
    val userId: Int
)
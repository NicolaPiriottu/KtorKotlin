package it.piriottu.ktorkotlin.api

import io.ktor.client.call.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.util.network.*
import it.piriottu.ktorkotlin.model.Post
import it.piriottu.ktorkotlin.utils.NetworkResponseCode

object ApiRepositories {

    private val API_WORKER: ApiWorker = ApiWorker()
    private val networkResponseCode=NetworkResponseCode()

    suspend fun getAllPosts(): NetworkResponse<MutableList<Post>> {

        return try {
            val response: HttpResponse =
                API_WORKER.getClient().get(API_WORKER.BASE_URL + "/posts") {
                    method = HttpMethod.Get
                }
            // Return response
            (NetworkResponse.Success(response.receive()))

        } catch (e: Throwable) {
            (NetworkResponse.Error(networkResponseCode.checkError(e)))
        }
    }

    suspend fun getPostById(idPost: String): NetworkResponse<MutableList<Post>> {

        return try {
            val response: HttpResponse =
                API_WORKER.getClient().get(API_WORKER.BASE_URL + "/posts") {
                    method = HttpMethod.Get
                    parameter("id", idPost)
                }

            // Return response
            (NetworkResponse.Success(response.receive()))

        } catch (e: Throwable) {
            (NetworkResponse.Error(networkResponseCode.checkError(e)))
        }
    }


    suspend fun sendPost(post: Post): NetworkResponse<Boolean> {

        return try {
            API_WORKER.getClient().post<HttpResponse> {
                contentType(ContentType.Application.Json)
                body = post //Gson().toJson(post)
                url(API_WORKER.BASE_URL + "/posts")
            }

            // Return response
            (NetworkResponse.Success(true))
        } catch (e: Throwable) {
            (NetworkResponse.Error(networkResponseCode.checkError(e)))
        }
    }
}
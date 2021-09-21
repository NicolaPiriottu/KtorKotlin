package it.piriottu.ktorkotlin.repositories.api

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import it.piriottu.ktorkotlin.models.Post
import it.piriottu.ktorkotlin.utils.NetworkResponseCode

object ApiRepositories {

    private val API_WORKER: ApiWorker = ApiWorker()
    private val networkResponseCode = NetworkResponseCode()

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


    suspend fun createPost(post: Post): NetworkResponse<Boolean> {

        return try {
            API_WORKER.getClient().post<HttpResponse> {
                url(API_WORKER.BASE_URL + "/posts")
                header("Accept", "application/json")
                header("Content-Type", "application/json")
                contentType(ContentType.Application.Json)
                method = HttpMethod.Post
                body = post
            }

            // Return response
            (NetworkResponse.Success(true))
        } catch (e: Throwable) {
            (NetworkResponse.Error(networkResponseCode.checkError(e)))
        }
    }

    suspend fun createPostWithParams( bodyPost: String, title: String, userId: Int): NetworkResponse<Boolean> {

        return try {
            //Set body request
            val post = hashMapOf<String, Any>("body" to bodyPost,"title" to title, "userId" to userId )

            API_WORKER.getClient().post<HttpResponse> {
                url(API_WORKER.BASE_URL + "/posts")
                header("Accept", "application/json")
                header("Content-Type", "application/json")
                contentType(ContentType.Application.Json)
                method = HttpMethod.Post
                body = post
            }

            // Return response
            (NetworkResponse.Success(true))
        } catch (e: Throwable) {
            (NetworkResponse.Error(networkResponseCode.checkError(e)))
        }
    }

    suspend fun deletePost(id: Int): NetworkResponse<Boolean> {

        return try {

            API_WORKER.getClient().delete<HttpResponse> {
                url(API_WORKER.BASE_URL + "/posts/$id")
                header("Accept", "application/json")
                header("Content-Type", "application/json")
                method = HttpMethod.Delete
            }

            // Return response
            (NetworkResponse.Success(true))
        } catch (e: Throwable) {
            (NetworkResponse.Error(networkResponseCode.checkError(e)))
        }
    }

    suspend fun editPost(bodyPost: String, id: Int, title: String, userId: Int): NetworkResponse<Boolean> {
        //Set body request
        val post = hashMapOf<String, Any>("body" to bodyPost,"title" to title, "userId" to userId )

        return try {

            API_WORKER.getClient().put<HttpResponse> {
                url(API_WORKER.BASE_URL + "/posts/$id")
                header("Accept", "application/json")
                header("Content-Type", "application/json")
                contentType(ContentType.Application.Json)
                method = HttpMethod.Put
                body = post
            }

            // Return response
            (NetworkResponse.Success(true))
        } catch (e: Throwable) {
            (NetworkResponse.Error(networkResponseCode.checkError(e)))
        }
    }
}
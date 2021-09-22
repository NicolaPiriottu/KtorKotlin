package it.piriottu.ktorkotlin.repositories.api

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import it.piriottu.ktorkotlin.models.PostResponse
import it.piriottu.ktorkotlin.utils.NetworkResponseCode

object ApiRepositories {

    private val API_WORKER: ApiWorker = ApiWorker()
    private val networkResponseCode = NetworkResponseCode()

    suspend fun getAllPosts(): NetworkResponse<MutableList<PostResponse>> {

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

    suspend fun getPostById(idPost: String): NetworkResponse<MutableList<PostResponse>> {

        return try {
            val response: HttpResponse =
                API_WORKER.getClient().get(API_WORKER.BASE_URL + "/posts") {
                    method = HttpMethod.Get
                    //posts?id=5
                    parameter("id", idPost)
                }

            // Return response
            (NetworkResponse.Success(response.receive()))

        } catch (e: Throwable) {
            (NetworkResponse.Error(networkResponseCode.checkError(e)))
        }
    }


    suspend fun createPost(postResponse: PostResponse): NetworkResponse<Boolean> {

        return try {
            API_WORKER.getClient().post<HttpResponse> {
                url(API_WORKER.BASE_URL + "/posts")
                method = HttpMethod.Post
                body = postResponse
            }

            // Return response
            (NetworkResponse.Success(true))
        } catch (e: Throwable) {
            (NetworkResponse.Error(networkResponseCode.checkError(e)))
        }
    }

    suspend fun createPostWithParams(
        bodyPost: String,
        title: String,
        userId: Int
    ): NetworkResponse<Boolean> {

        return try {
            //Set body request
            val post =
                hashMapOf<String, Any>("body" to bodyPost, "title" to title, "userId" to userId)

            API_WORKER.getClient().post<HttpResponse> {
                url(API_WORKER.BASE_URL + "/posts")
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
                method = HttpMethod.Delete
            }

            // Return response
            (NetworkResponse.Success(true))
        } catch (e: Throwable) {
            (NetworkResponse.Error(networkResponseCode.checkError(e)))
        }
    }

    suspend fun editPost(
        bodyPost: String,
        id: Int,
        title: String,
        userId: Int
    ): NetworkResponse<Boolean> {
        //Set body request
        val post = hashMapOf<String, Any>("body" to bodyPost, "title" to title, "userId" to userId)

        return try {

            API_WORKER.getClient().put<HttpResponse> {
                url(API_WORKER.BASE_URL + "/posts/$id")
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
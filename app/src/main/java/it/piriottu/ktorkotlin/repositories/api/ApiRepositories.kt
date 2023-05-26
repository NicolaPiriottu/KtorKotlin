package it.piriottu.ktorkotlin.repositories.api

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import it.piriottu.ktorkotlin.models.PostResponse
import it.piriottu.ktorkotlin.utils.NetworkResponseCode

/**
 * Created by OverApp on 21/09/2021.
 * Updated 26/05/2023
 *  Visit https://www.overapp.com/
 */
object ApiRepositories {

    private val API_WORKER: ApiWorker = ApiWorker()
    private val networkResponseCode = NetworkResponseCode()

    suspend fun getAllPosts(): NetworkResponse<MutableList<PostResponse>> {

        return try {
            val response: HttpResponse =
                API_WORKER.getClient().get(API_WORKER.BASE_URL + "/posts")
            // Return response
            (NetworkResponse.Success(response.body()))//response.receive()

        } catch (e: Throwable) {
            (NetworkResponse.Error(networkResponseCode.checkError(e)))
        }
    }

    suspend fun getPostById(idPost: String): NetworkResponse<MutableList<PostResponse>> {

        return try {
            val response: HttpResponse =
                API_WORKER.getClient().get(API_WORKER.BASE_URL + "/posts") {
                    //posts?id=5
                    parameter("id", idPost)
                }

            // Return response
            (NetworkResponse.Success(response.body()))//response.receive()

        } catch (e: Throwable) {
            (NetworkResponse.Error(networkResponseCode.checkError(e)))
        }
    }


    suspend fun createPost(postResponse: PostResponse): NetworkResponse<Boolean> {

        return try {
            API_WORKER.getClient().post /*post<HttpResponse>*/ {
                url(API_WORKER.BASE_URL + "/posts")
                setBody(postResponse)//body = postResponse

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

            API_WORKER.getClient().post /*post<HttpResponse>*/ {
                url(API_WORKER.BASE_URL + "/posts")
                setBody(post)//body = post
            }

            // Return response
            (NetworkResponse.Success(true))
        } catch (e: Throwable) {
            (NetworkResponse.Error(networkResponseCode.checkError(e)))
        }
    }

    suspend fun deletePost(id: Int): NetworkResponse<Boolean> {

        return try {

            API_WORKER.getClient().delete/*delete<HttpResponse>*/ {
                url(API_WORKER.BASE_URL + "/posts/$id")
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

            API_WORKER.getClient().put/*put<HttpResponse>*/ {
                url(API_WORKER.BASE_URL + "/posts/$id")
                setBody(post)//body = post
            }

            // Return response
            (NetworkResponse.Success(true))
        } catch (e: Throwable) {
            (NetworkResponse.Error(networkResponseCode.checkError(e)))
        }
    }
}
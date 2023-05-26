package it.piriottu.ktorkotlin.repositories.api


import android.util.Log
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.gson.gson
import it.piriottu.ktorkotlin.managers.SessionManager


//OLD
/*
import io.ktor.client.features.logging.*
import io.ktor.client.features.observer.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
* */
/**
 * Created by OverApp on 21/09/21.
 *  Visit https://www.overapp.com/
 */
class ApiWorker {

    val BASE_URL = "https://jsonplaceholder.typicode.com"

    private val client = HttpClient(CIO) {

        //Response validation
        //By default, Ktor doesn't validate a response
        //https://ktor.io/docs/response-validation.html
        expectSuccess = true

        //Header
        install(DefaultRequest) {
            header("Accept", "application/json")
            header("Content-type", "application/json")
            contentType(ContentType.Application.Json)
            //Pass your token
            header("Authorization", "Bearer ${SessionManager.userToken}")
        }
        // Json
       /* install(JsonFeature) {
            serializer = GsonSerializer()
        }*/

        install(ContentNegotiation) {
            gson()
        }

        // Timeout
        install(HttpTimeout) {
            requestTimeoutMillis = 15000L
            connectTimeoutMillis = 15000L
            socketTimeoutMillis = 15000L
        }

        //Now you see response logs inside terminal
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.HEADERS
        }

        //Print other logs
        install(ResponseObserver) {
            onResponse { response ->
                Log.d("ApiService", "HTTP status: ${response.status.value}")
            }
        }
    }

    fun getClient(): HttpClient {
        return client
    }
}
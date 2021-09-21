package it.piriottu.ktorkotlin.repositories.api


import android.util.Log
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.logging.*
import io.ktor.client.features.observer.*
import io.ktor.client.request.*
import io.ktor.http.*
import it.piriottu.ktorkotlin.managers.SessionManager


class ApiWorker {

    val BASE_URL = "https://jsonplaceholder.typicode.com"

    private val client = HttpClient(CIO) {

        //Header
        install(DefaultRequest) {
            header("Accept", "application/json")
            header("Content-type", "application/json")
            contentType(ContentType.Application.Json)
            //Pass your token
            header("Authorization", "Bearer ${SessionManager.userToken}")
        }
        // Json
        install(JsonFeature) {
            serializer = GsonSerializer()
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
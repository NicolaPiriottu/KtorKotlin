package it.piriottu.ktorkotlin.api


import android.util.Log
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.observer.*


class ApiWorker {

    val BASE_URL = "https://jsonplaceholder.typicode.com"

    private val client = HttpClient(CIO) {

        //Header
        install(DefaultRequest) {
            headers.append("Content-type", "application/json")
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
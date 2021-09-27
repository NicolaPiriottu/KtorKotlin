package it.piriottu.ktorkotlin.utils

import io.ktor.client.features.*
import io.ktor.util.network.*
/**
 * Created by OverApp on 21/09/21.
 *  Visit https://www.overapp.com/
 */
class NetworkResponseCode {

    /**
     * responses status code.
     */
     fun checkError(e: Throwable): Int {
        // Handle Error
        return when (e) {

            //For 3xx responses
            is RedirectResponseException -> {
                (e.response.status.value)
            }
            //For 4xx responses
            is ClientRequestException -> {
                (e.response.status.value)
            }

            //For 5xx responses
            is ServerResponseException -> {
                (e.response.status.value)
            }

            // Network Error
            is UnresolvedAddressException -> {
                // Internet Error
                -1
            }
            else -> {
                // Unhandled error
                -2
            }
        }
    }
}
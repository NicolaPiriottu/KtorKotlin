package it.piriottu.ktorkotlin.api

sealed class NetworkResponse<out T : Any> {
    /**
     * response with a 2xx status code
     */
    data class Success<out T : Any>(val data: T) : NetworkResponse<T>()

    /**
     * response with a non-2xx status exception.
     *
     */
    data class Error(val code: Int) : NetworkResponse<Nothing>()
}
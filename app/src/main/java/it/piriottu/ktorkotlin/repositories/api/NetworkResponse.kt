package it.piriottu.ktorkotlin.repositories.api

sealed class NetworkResponse<out T : Any> {
    /**
     * response with a 2xx status code
     */
    data class Success<out T : Any>(val data: T) : NetworkResponse<T>()

    /**
     * response with a non-2xx status code.
     *
     */
    data class Error(val code: Int) : NetworkResponse<Nothing>()
}
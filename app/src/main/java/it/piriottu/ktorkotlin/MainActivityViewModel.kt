package it.piriottu.ktorkotlin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import it.piriottu.ktorkotlin.api.ApiRepositories
import it.piriottu.ktorkotlin.api.NetworkResponse
import it.piriottu.ktorkotlin.model.Post
import it.piriottu.ktorkotlin.utils.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivityViewModel : ViewModel() {

    //region UseCase
    sealed class UseCaseLiveData {
        data class ShowItems(val items: MutableList<Post>) : UseCaseLiveData()
        data class Error(val code: Int) : UseCaseLiveData()
        data class Saved(val isSaved: Boolean) : UseCaseLiveData()
    }
    //endregion UseCase

    //region LiveData
    val useCaseLiveData = MutableLiveData<Event<UseCaseLiveData>>()
    //endregion LiveData
    //region Public Methods

    //Example 1 GET
    fun getPosts() {
        viewModelScope.launch {
            callPosts()
        }
    }

    //Example 2 GET with params
    fun getPostById(idPost: String) {
        viewModelScope.launch {
            callPostById(idPost)
        }
    }

    //Example 3
    fun sendPost(post: Post) {
        viewModelScope.launch {
            callSendPost(post)
        }
    }

    //endregion Public Methods
    //region Private Methods
    private suspend fun callPosts() {
        withContext(Dispatchers.IO) {
            ApiRepositories.getAllPosts()
        }.apply {
            when (this) {
                is NetworkResponse.Success -> useCaseLiveData.value =
                    Event(UseCaseLiveData.ShowItems(this.data))
                is NetworkResponse.Error -> useCaseLiveData.value =
                    Event(UseCaseLiveData.Error(this.code))
            }
        }

    }

    private suspend fun callPostById(idPost: String) {
        withContext(Dispatchers.IO) {
            ApiRepositories.getPostById(idPost)
        }.apply {
            when (this) {
                is NetworkResponse.Success -> useCaseLiveData.value =
                    Event(UseCaseLiveData.ShowItems(this.data))
                is NetworkResponse.Error -> useCaseLiveData.value =
                    Event(UseCaseLiveData.Error(this.code))
            }
        }

    }


    private suspend fun callSendPost(post: Post) {

        withContext(Dispatchers.IO) {
            ApiRepositories.sendPost(post)
        }.apply {
            when (this) {
                is NetworkResponse.Success -> useCaseLiveData.value =
                    Event(UseCaseLiveData.Saved(this.data))
                is NetworkResponse.Error -> useCaseLiveData.value =
                    Event(UseCaseLiveData.Error(this.code))
            }
        }
    }
    //endregion Private Methods
}






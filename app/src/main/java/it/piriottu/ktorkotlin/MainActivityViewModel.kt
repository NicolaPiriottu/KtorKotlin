package it.piriottu.ktorkotlin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import it.piriottu.ktorkotlin.repositories.api.ApiRepositories
import it.piriottu.ktorkotlin.repositories.api.NetworkResponse
import it.piriottu.ktorkotlin.models.PostResponse
import it.piriottu.ktorkotlin.utils.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by OverApp on 21/09/21.
 *  Visit https://www.overapp.com/
 */
class MainActivityViewModel : ViewModel() {

    //region UseCase
    sealed class UseCaseLiveData {
        data class ShowPosts(val items: MutableList<PostResponse>) : UseCaseLiveData()
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
    fun createPost(postResponse: PostResponse) {
        viewModelScope.launch {
            callCreatePost(postResponse)
        }
    }

    //Example 4
    fun createPostWithParams(bodyPost: String, title: String, userId: Int) {
        viewModelScope.launch {
            callCreatePostWithParams(bodyPost, title, userId)
        }
    }

    //Example 5
    fun deletePost(postId: Int) {
        viewModelScope.launch {
            callDeletePost(postId)
        }
    }

    //Example 6
    fun editPost(bodyPost: String, id: Int, title: String, userId: Int) {
        viewModelScope.launch {
            callEditPost(bodyPost, id, title, userId)
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
                    Event(UseCaseLiveData.ShowPosts(this.data))

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
                    Event(UseCaseLiveData.ShowPosts(this.data))

                is NetworkResponse.Error -> useCaseLiveData.value =
                    Event(UseCaseLiveData.Error(this.code))
            }
        }
    }


    private suspend fun callCreatePost(postResponse: PostResponse) {

        withContext(Dispatchers.IO) {
            ApiRepositories.createPost(postResponse)
        }.apply {
            when (this) {
                is NetworkResponse.Success -> useCaseLiveData.value =
                    Event(UseCaseLiveData.Saved(this.data))

                is NetworkResponse.Error -> useCaseLiveData.value =
                    Event(UseCaseLiveData.Error(this.code))
            }
        }
    }

    private suspend fun callCreatePostWithParams(bodyPost: String, title: String, userId: Int) {

        withContext(Dispatchers.IO) {
            ApiRepositories.createPostWithParams(bodyPost, title, userId)
        }.apply {
            when (this) {
                is NetworkResponse.Success -> useCaseLiveData.value =
                    Event(UseCaseLiveData.Saved(this.data))

                is NetworkResponse.Error -> useCaseLiveData.value =
                    Event(UseCaseLiveData.Error(this.code))
            }
        }
    }

    private suspend fun callDeletePost(postId: Int) {

        withContext(Dispatchers.IO) {
            ApiRepositories.deletePost(postId)
        }.apply {
            when (this) {
                is NetworkResponse.Success -> useCaseLiveData.value =
                    Event(UseCaseLiveData.Saved(this.data))

                is NetworkResponse.Error -> useCaseLiveData.value =
                    Event(UseCaseLiveData.Error(this.code))
            }
        }
    }

    private suspend fun callEditPost(bodyPost: String, id: Int, title: String, userId: Int) {

        withContext(Dispatchers.IO) {
            ApiRepositories.editPost(bodyPost, id, title, userId)
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






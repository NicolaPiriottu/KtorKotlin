package it.piriottu.ktorkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import it.piriottu.ktorkotlin.managers.SessionManager

/**
 * Created by OverApp on 21/09/21.
 *  Visit https://www.overapp.com/
 */
class MainActivity : AppCompatActivity() {
    /**
     * ViewModel
     **/
    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Setup
        setupObserver()
        //TODO copy your token here
        SessionManager.userToken = "COPY-YOUR-TOKEN-HERE"

        /**
         * GET
         **/
        //Example 1 GET
        viewModel.getPosts()
        //Example 2 GET with params : https://jsonplaceholder.typicode.com/posts?id=5
        //viewModel.getPostById("5")
        /**
         * POST
         **/
        //Example 3 POST whit object
        //val post = Post("Body post", 1, "Title post", 1)
        //viewModel.createPost(post)
        //Example 4 POST whit params
        //viewModel.createPostWithParams("Hello World!", "First insert", 1)
        /**
         * DELETE
         * Example 4
         **/
        //viewModel.deletePost(1)
        /**
         * PUT
         **/
        //viewModel.editPost("Hello Big Big World!", 1,"First update!", 1)
    }

    private fun setupObserver() {
        // Use Case
        viewModel.useCaseLiveData.observe(this, {
            it.getContentIfNotHandled()?.let { useCase ->
                when (useCase) {
                    is MainActivityViewModel.UseCaseLiveData.Error -> {
                        Log.d("MainActivity", "Error ${useCase.code}")
                        showToast("Error ${useCase.code}")
                    }
                    is MainActivityViewModel.UseCaseLiveData.ShowItems -> {
                        Log.d("MainActivity", "ShowItems ${useCase.items}")
                        showToast("ShowItems ${useCase.items}")
                    }
                    is MainActivityViewModel.UseCaseLiveData.Saved -> {
                        Log.d("MainActivity", "Saved ${useCase.isSaved}")
                        showToast("Saved ${useCase.isSaved}")
                    }
                }
            }
        })
    }

    private fun showToast(response: String) {
        Toast.makeText(this, response, Toast.LENGTH_LONG).show()
    }
}
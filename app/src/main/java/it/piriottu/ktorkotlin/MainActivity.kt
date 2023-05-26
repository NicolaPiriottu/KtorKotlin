package it.piriottu.ktorkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import it.piriottu.ktorkotlin.managers.SessionManager
import it.piriottu.ktorkotlin.models.PostResponse

/**
 * Created by OverApp on 21/09/21.
 *  Visit https://www.overapp.com/
 */
class MainActivity : AppCompatActivity() {
    /**
     * ViewModel
     **/
    private val viewModel: MainActivityViewModel by viewModels()
    lateinit var progress: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Setup
        setupObserver()
        //TODO copy your token here
        SessionManager.userToken = "COPY-YOUR-TOKEN-HERE"

        progress = findViewById(R.id.progress)
    }

    fun buttonEvent(view: View) {

        progress.isVisible = true
        when (view.id) {
            /**
             * GET
             **/
            R.id.getPostsBtn -> {
                //Example 1 GET
                viewModel.getPosts()
            }

            R.id.getPostBtn -> {
                //Example 2 GET with params : https://jsonplaceholder.typicode.com/posts?id=5
                viewModel.getPostById("5")
            }
            /**
             * POST
             **/
            R.id.postWhitObjectBtn -> {
                //Example 3 POST whit object
                val post = PostResponse("Body post", 1, "Title post", 1)
                viewModel.createPost(post)
            }

            R.id.postWhitParamsBtn -> {
                //Example 4 POST whit params
                viewModel.createPostWithParams("Hello World!", "First insert", 1)
            }
            /**
             * DELETE
             **/
            R.id.deletePostBtn -> {
                //Example 5
                viewModel.deletePost(1)
            }
            /**
             * PUT
             **/
            R.id.putPostBtn -> {
                //Example 6
                viewModel.editPost("Hello Big Big World!", 1, "First update!", 1)
            }
        }
    }

    private fun setupObserver() {
        // Use Case
        viewModel.useCaseLiveData.observe(this) {
            it.getContentIfNotHandled()?.let { useCase ->
                progress.isVisible = false
                when (useCase) {
                    is MainActivityViewModel.UseCaseLiveData.Error -> {
                        Log.d("MainActivity", "Error ${useCase.code}")
                        showToast("Error ${useCase.code}")
                    }

                    is MainActivityViewModel.UseCaseLiveData.ShowPosts -> {
                        Log.d("MainActivity", "ShowItems ${useCase.items}")
                        showToast("ShowItems ${useCase.items}")
                    }

                    is MainActivityViewModel.UseCaseLiveData.Saved -> {
                        Log.d("MainActivity", "Saved ${useCase.isSaved}")
                        showToast("Saved ${useCase.isSaved}")
                    }
                }
            }
        }
    }

    private fun showToast(response: String) {
        Toast.makeText(this, response, Toast.LENGTH_LONG).show()
    }
}
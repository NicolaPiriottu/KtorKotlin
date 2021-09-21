package it.piriottu.ktorkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import it.piriottu.ktorkotlin.model.Post

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


        //Example 1 GET
        viewModel.getPosts()
        //Example 2 GET with params
        //viewModel.getPostById("5")

        //Example 3 POST
        //val post = Post("Body post", 1, "Title post", 1)
        //viewModel.sendPost(post)

    }

    private fun setupObserver() {
        // Use Case
        viewModel.useCaseLiveData.observe(this, {
            it.getContentIfNotHandled()?.let { useCase ->
                when (useCase) {
                    is MainActivityViewModel.UseCaseLiveData.Error -> {
                        Log.d("MainActivity", "Error ${useCase.code}")
                    }
                    is MainActivityViewModel.UseCaseLiveData.ShowItems -> {
                        Log.d("MainActivity", "ShowItems ${useCase.items}")
                    }
                    is MainActivityViewModel.UseCaseLiveData.Saved -> {
                        Log.d("MainActivity", "Saved ${useCase.isSaved}")
                    }
                }
            }
        })
    }
}
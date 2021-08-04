package it.piriottu.ktorkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import it.piriottu.ktorkotlin.model.Post

class MainActivity : AppCompatActivity() {

    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Example 1
        viewModel.getPosts()
        //Example 2
        //viewModel.getPostById("5")
        //Example 3
        //val post = Post("Body post", 1, "Title post", 1)
        //viewModel.sendPost(post)

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
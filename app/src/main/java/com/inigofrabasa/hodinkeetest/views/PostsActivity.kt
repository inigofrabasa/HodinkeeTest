package com.inigofrabasa.hodinkeetest.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.inigofrabasa.hodinkeetest.HodinkeeApplication
import com.inigofrabasa.hodinkeetest.R
import com.inigofrabasa.hodinkeetest.adapters.PostsAdapter
import com.inigofrabasa.hodinkeetest.databinding.ActivityPostsBinding
import com.inigofrabasa.hodinkeetest.di.ApplicationComponent
import com.inigofrabasa.hodinkeetest.model.ArticleView
import com.inigofrabasa.hodinkeetest.model.ResponseHelper
import com.inigofrabasa.hodinkeetest.utils.gone
import com.inigofrabasa.hodinkeetest.utils.visible
import com.inigofrabasa.hodinkeetest.viewmodel.PostsViewModel
import javax.inject.Inject

class PostsActivity : AppCompatActivity() {

    private lateinit var binding : ActivityPostsBinding
    private lateinit var responseHelperObserver : Observer<ResponseHelper>
    private lateinit var postsObserver : Observer<List<ArticleView>>

    private val lifecycleComponent: ApplicationComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        (application as HodinkeeApplication).appComponent
    }

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject lateinit var postsAdapter: PostsAdapter
    private lateinit var postsViewModel: PostsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleComponent.inject(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_posts)
        postsViewModel = ViewModelProvider(this, viewModelFactory).get(PostsViewModel::class.java)

        binding.postsScreenRecyclerView.adapter = postsAdapter
        binding.postsScreenRecyclerView.layoutManager =
            LinearLayoutManager(this@PostsActivity, RecyclerView.VERTICAL, false) as RecyclerView.LayoutManager?

        getPosts()
    }

    private fun getPosts(){
        responseHelperObserver = Observer { responseHelper ->
            responseHelper.run {
                if(!isSuccessful){
                    binding.postsScreenProgressBar.gone()
                    binding.postsScreenErrorMessage.text = responseHelper.message
                }
            }
        }
        postsViewModel.getHelperLiveData.observe(this, responseHelperObserver )
        postsObserver = Observer { posts ->
            posts.run {
                binding.postsScreenProgressBar.gone()
                binding.postsScreenRecyclerView.visible()
                postsAdapter.setCollection(posts as MutableList)
            }
        }
        postsViewModel.postsLiveData.observe(this, postsObserver)
        postsViewModel.getPosts("watches")
    }

    override fun onDestroy() {
        super.onDestroy()
        postsViewModel.getHelperLiveData.removeObserver(responseHelperObserver)
        postsViewModel.postsLiveData.removeObserver(postsObserver)
    }
}
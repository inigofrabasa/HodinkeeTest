package com.inigofrabasa.hodinkeetest.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.inigofrabasa.hodinkeetest.databinding.ItemPostBinding
import com.inigofrabasa.hodinkeetest.model.ArticleView
import javax.inject.Inject

class PostsAdapter
@Inject constructor() : RecyclerView.Adapter<PostsAdapter.ViewHolder>() {
    private var collection: MutableList<ArticleView> = mutableListOf()

    fun setCollection(collection: MutableList<ArticleView>){
        this.collection = collection
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ViewHolder {
        lateinit var view : ViewDataBinding
        view = ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(collection[position])
    }

    override fun getItemCount() = collection.size

    class ViewHolder(private var binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(articleView : ArticleView) {
            (binding as ItemPostBinding).apply {
                this.article = articleView
                this.itemContent.setOnClickListener {  }
            }
        }
    }
}
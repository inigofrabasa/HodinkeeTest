package com.inigofrabasa.hodinkeetest.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.inigofrabasa.hodinkeetest.databinding.ItemPaginationBinding
import com.inigofrabasa.hodinkeetest.databinding.ItemPostBinding
import com.inigofrabasa.hodinkeetest.model.ArticleView
import com.inigofrabasa.hodinkeetest.model.ItemBaseView
import com.inigofrabasa.hodinkeetest.model.LoadMoreView
import javax.inject.Inject

class PostsAdapter
@Inject constructor() : RecyclerView.Adapter<PostsAdapter.ViewHolder>() {
    private var collection: MutableList<ItemBaseView> = mutableListOf()

    fun setCollection(collection: MutableList<ItemBaseView>){
        this.collection = collection
        notifyDataSetChanged()
    }

    fun addCollectionBottom(collection: MutableList<ItemBaseView>){
        if(this.collection.isNotEmpty() && collection.isNotEmpty()){
            if(this.collection[this.collection.size.minus(1)] is LoadMoreView){
                val removeAt = this.collection.size.minus(1)
                this.collection.removeAt(removeAt)
                notifyItemRemoved(removeAt)

                val sizeToAdd = collection.size - this.collection.size
                this.collection.addAll(collection.subList(collection.size - sizeToAdd, collection.size))
                notifyItemRangeInserted(collection.size -  sizeToAdd, sizeToAdd)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ViewHolder {
        lateinit var view : ViewDataBinding
        when(viewType){
            ItemViewType.ArticleType().id -> view = ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            ItemViewType.LoaderType().id -> view = ItemPaginationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        }

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(collection[position])
    }

    override fun getItemCount() = collection.size

    override fun getItemViewType(position: Int): Int {
        return when(collection[position]){
            is ArticleView -> ItemViewType.ArticleType().id
            is LoadMoreView -> ItemViewType.LoaderType().id
            else -> ItemViewType.ArticleType().id
        }
    }

    class ViewHolder(private var binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(itemBaseView : ItemBaseView) {
            when(itemBaseView){
                is ArticleView -> {
                    (binding as ItemPostBinding).apply {
                        this.article = itemBaseView
                        this.itemContent.setOnClickListener {  }
                    }
                }
            }
        }
    }

    sealed class ItemViewType{
        class ArticleType(val id : Int = 0) : ItemViewType()
        class LoaderType(val id : Int = 1) : ItemViewType()
    }
}
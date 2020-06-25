package com.perisic.luka.post.ui.all

import androidx.recyclerview.widget.DiffUtil
import com.perisic.luka.base.base.BasePagedRecyclerAdapter
import com.perisic.luka.data.remote.model.response.PostResponse
import com.perisic.luka.post.databinding.ItemPostBinding

class AllPostsAdapter : BasePagedRecyclerAdapter<PostResponse, ItemPostBinding>(
    inflater = ItemPostBinding::inflate,
    binder = {
        title = it.title
    },
    diffCallback = object : DiffUtil.ItemCallback<PostResponse>() {

        override fun areItemsTheSame(oldItem: PostResponse, newItem: PostResponse): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PostResponse, newItem: PostResponse): Boolean {
            return oldItem == newItem
        }
    }
)
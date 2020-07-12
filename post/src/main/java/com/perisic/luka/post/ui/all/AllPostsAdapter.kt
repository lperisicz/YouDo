package com.perisic.luka.post.ui.all

import android.view.View
import androidx.recyclerview.widget.DiffUtil
import com.perisic.luka.base.base.BasePagedRecyclerAdapter
import com.perisic.luka.data.remote.model.response.PostResponse
import com.perisic.luka.post.databinding.ItemPostBinding

class AllPostsAdapter(
    moreVisible: Boolean = true,
    moreCallback: (view: View, post: PostResponse) -> Unit = { _, _ -> },
    clickCallback: (post: PostResponse) -> Unit = {}
) : BasePagedRecyclerAdapter<PostResponse, ItemPostBinding>(
    inflater = ItemPostBinding::inflate,
    binder = {
        post = it
        postCallback = object : PostClickCallback {
            override fun onMoreClick(view: View, post: PostResponse) = moreCallback(view, post)
            override fun onClick(post: PostResponse) = clickCallback(post)
        }
        moreEnabled = moreVisible
    },
    diffCallback = object : DiffUtil.ItemCallback<PostResponse>() {

        override fun areItemsTheSame(oldItem: PostResponse, newItem: PostResponse): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PostResponse, newItem: PostResponse): Boolean {
            return oldItem == newItem
        }
    }
) {

    interface PostClickCallback {
        fun onMoreClick(view: View, post: PostResponse)
        fun onClick(post: PostResponse)
    }

}
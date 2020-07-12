package com.perisic.luka.post.ui.all

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.perisic.luka.base.base.BaseFragment
import com.perisic.luka.data.remote.model.base.NetworkState
import com.perisic.luka.data.remote.model.response.PostResponse
import com.perisic.luka.post.R
import com.perisic.luka.post.databinding.FragmentAllPostsBinding
import kotlinx.android.synthetic.main.fragment_all_posts.*
import org.koin.android.viewmodel.ext.android.viewModel

internal class AllPostsFragment : BaseFragment<FragmentAllPostsBinding>() {

    override val layoutId: Int = R.layout.fragment_all_posts
    private val adapter = AllPostsAdapter(
        moreVisible = false,
        clickCallback = this::onPostClick
    )

    private val viewModel by viewModel<AllPostsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
        observeData()
    }

    private fun setupUi() {
        recyclerViewAllPosts.adapter = adapter
        swipeRefreshLayoutPosts.setOnRefreshListener {
            adapter.currentList?.dataSource?.invalidate()
        }
    }

    private fun observeData() {
        viewModel.postsResponse.observe(viewLifecycleOwner, Observer(adapter::submitList))
        viewModel.networkState.observe(viewLifecycleOwner, Observer {
            swipeRefreshLayoutPosts.isRefreshing = it == NetworkState.LOADING
        })
    }

    private fun onPostClick(post: PostResponse) {
        findNavController().navigate(
            AllPostsFragmentDirections.actionMyPostsFragmentToPostDetailsFragment(
                post = post
            )
        )
    }

}
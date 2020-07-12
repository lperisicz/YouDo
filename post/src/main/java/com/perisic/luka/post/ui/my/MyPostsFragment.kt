package com.perisic.luka.post.ui.my

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.PopupMenu
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.perisic.luka.base.base.BaseFragment
import com.perisic.luka.data.remote.model.base.NetworkState
import com.perisic.luka.data.remote.model.response.PostResponse
import com.perisic.luka.post.R
import com.perisic.luka.post.databinding.FragmentMyPostsBinding
import com.perisic.luka.post.ui.all.AllPostsAdapter
import kotlinx.android.synthetic.main.fragment_my_posts.*
import org.koin.android.viewmodel.ext.android.viewModel

internal class MyPostsFragment : BaseFragment<FragmentMyPostsBinding>() {

    override val layoutId: Int = R.layout.fragment_my_posts
    private val adapter = AllPostsAdapter(
        moreCallback = this::onPostMoreClick,
        clickCallback = this::onPostClick
    )

    private fun onPostClick(postResponse: PostResponse) {
        findNavController().navigate(
            MyPostsFragmentDirections.actionMyPostsFragmentToPostDetailsFragment(postResponse)
        )
    }

    private val viewModel by viewModel<MyPostsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
        observeData()
    }

    private fun setupUi() {
        recyclerViewMyPosts.adapter = adapter
        fabCreate.setOnClickListener {
            findNavController().navigate(
                MyPostsFragmentDirections.actionMyPostsFragmentToCreatePostFragment()
            )
        }
        swipeRefreshLayoutPosts.setOnRefreshListener {
            viewModel.postsResponse.value?.dataSource?.invalidate()
        }
    }

    private fun observeData() {
        viewModel.postsResponse.observe(viewLifecycleOwner, Observer(adapter::submitList))
        viewModel.postDeleteResponse.observe(viewLifecycleOwner, Observer {
            adapter.currentList?.dataSource?.invalidate()
        })
        viewModel.networkState.observe(viewLifecycleOwner, Observer {
            swipeRefreshLayoutPosts.isRefreshing = it == NetworkState.LOADING
        })
    }

    private fun onPostMoreClick(view: View, post: PostResponse) {
        val popup = PopupMenu(requireContext(), view)
        val inflater = popup.menuInflater
        inflater.inflate(
            R.menu.menu_post,
            popup.menu
        )
        popup.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_delete -> {
                    viewModel.deletePost(post.id)
                    true
                }
                else -> false
            }
        }
        popup.show()
    }

}
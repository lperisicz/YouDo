package com.perisic.luka.post.ui.my

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.perisic.luka.base.base.BaseFragment
import com.perisic.luka.post.R
import com.perisic.luka.post.databinding.FragmentMyPostsBinding
import com.perisic.luka.post.ui.all.AllPostsAdapter
import kotlinx.android.synthetic.main.fragment_my_posts.*
import org.koin.android.viewmodel.ext.android.viewModel

internal class MyPostsFragment : BaseFragment<FragmentMyPostsBinding>() {

    override val layoutId: Int = R.layout.fragment_my_posts
    private val adapter = AllPostsAdapter()
    private val viewModel by viewModel<MyPostsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
        observeData()
    }

    private fun setupUi() {
        recyclerViewMyPosts.adapter = adapter
    }

    private fun observeData() {
        viewModel.postsResponse.observe(viewLifecycleOwner, Observer(adapter::submitList))
    }

}
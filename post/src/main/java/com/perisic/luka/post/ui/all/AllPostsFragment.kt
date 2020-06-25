package com.perisic.luka.post.ui.all

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.perisic.luka.base.base.BaseFragment
import com.perisic.luka.post.R
import com.perisic.luka.post.databinding.FragmentAllPostsBinding
import kotlinx.android.synthetic.main.fragment_all_posts.*
import org.koin.android.viewmodel.ext.android.viewModel

internal class AllPostsFragment : BaseFragment<FragmentAllPostsBinding>() {

    override val layoutId: Int = R.layout.fragment_all_posts
    private val adapter = AllPostsAdapter()
    private val viewModel by viewModel<AllPostsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
        observeData()
    }

    private fun setupUi() {
        recyclerViewAllPosts.adapter = adapter
    }

    private fun observeData() {
        viewModel.postsResponse.observe(viewLifecycleOwner, Observer(adapter::submitList))
    }

}
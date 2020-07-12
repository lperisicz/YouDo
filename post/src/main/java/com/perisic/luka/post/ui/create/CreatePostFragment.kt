package com.perisic.luka.post.ui.create

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.perisic.luka.base.base.BaseFragment
import com.perisic.luka.data.remote.model.base.BaseResponse
import com.perisic.luka.post.R
import com.perisic.luka.post.databinding.FragmentCreatePostBinding
import kotlinx.android.synthetic.main.fragment_create_post.*
import org.koin.android.viewmodel.ext.android.viewModel

internal class CreatePostFragment : BaseFragment<FragmentCreatePostBinding>() {

    override val layoutId: Int = R.layout.fragment_create_post
    private val viewModel by viewModel<CreatePostViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
        observeData()
    }

    private fun setupUi() {
        textInputLayoutTitle.editText?.doOnTextChanged { text, _, _, _ ->
            viewModel.title.value = text?.toString()
        }
        textInputLayoutDescription.editText?.doOnTextChanged { text, _, _, _ ->
            viewModel.description.value = text?.toString()
        }
        textInputLayoutPrice.editText?.doOnTextChanged { text, _, _, _ ->
            viewModel.price.value = text?.toString()
        }
        switchFull.setOnCheckedChangeListener { _, isChecked ->
            textViewFullState.setText(if (isChecked) R.string.per_hour else R.string.full)
            viewModel.full.value = isChecked
        }
        switchLocation.setOnCheckedChangeListener { _, isChecked ->
            viewModel.useLocation.value = isChecked
        }
        buttonCreate.setOnClickListener {
            viewModel.createPost()
        }
    }

    private fun observeData() {
        viewModel.createPostResponse.observe(viewLifecycleOwner, Observer {
            if (it?.networkState?.status == BaseResponse.STATUS.SUCCESS) findNavController().navigateUp()
        })
    }

}
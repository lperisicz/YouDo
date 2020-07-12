package com.perisic.luka.post.ui.details

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.perisic.luka.base.base.BaseFragment
import com.perisic.luka.base.extensions.callNumber
import com.perisic.luka.base.extensions.sendMail
import com.perisic.luka.post.R
import com.perisic.luka.post.databinding.FragmentPostDetailsBinding

internal class PostDetailsFragment : BaseFragment<FragmentPostDetailsBinding>(), ContactListener {

    override val layoutId: Int = R.layout.fragment_post_details
    private val args by navArgs<PostDetailsFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.post = args.post
        binding.listener = this
    }

    override fun onContactClick(type: String) {
        when (type) {
            "phone" -> {
                args.post.user.contacts.find { it.type == type }?.value?.let {
                    callNumber(it)
                }
            }
            "email" -> {
                args.post.user.contacts.find { it.type == type }?.value?.let {
                    sendMail(it)
                }
            }
            else -> null
        } ?: let {
            Snackbar.make(
                binding.root,
                getString(R.string.unknown_contact),
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }

}

interface ContactListener {

    fun onContactClick(type: String)

}
package com.perisic.luka.user.ui

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import com.perisic.luka.base.base.BaseFragment
import com.perisic.luka.data.remote.model.base.BaseResponse
import com.perisic.luka.user.R
import com.perisic.luka.user.databinding.FragmentUserBinding
import kotlinx.android.synthetic.main.fragment_user.*
import org.koin.android.viewmodel.ext.android.viewModel

internal class UserFragment : BaseFragment<FragmentUserBinding>() {

    override val layoutId: Int = R.layout.fragment_user
    private val viewModel by viewModel<UserViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
        observeData()
    }

    private fun setupUi() {
        textInputLayoutPhone.editText?.doOnTextChanged { text, _, _, _ ->
            viewModel.phone.value = text?.toString()
        }
        textInputLayoutEmail.editText?.doOnTextChanged { text, _, _, _ ->
            viewModel.email.value = text?.toString()
        }
        sliderRadius.addOnChangeListener { _, value, fromUser ->
            if (fromUser) {
                viewModel.radius.value = value.toInt()
            }
        }
        buttonUpdate.setOnClickListener {
            viewModel.updateUserData()
        }
    }

    private fun observeData() {
        viewModel.radius.observe(viewLifecycleOwner, Observer {
            if (it != sliderRadius.value.toInt()) {
                sliderRadius.value = it.toFloat()
            }
        })
        viewModel.phone.observe(viewLifecycleOwner, Observer {
            if (textInputLayoutPhone.editText?.text?.toString() != it) {
                textInputLayoutPhone.editText?.setText(it)
            }
        })
        viewModel.email.observe(viewLifecycleOwner, Observer {
            if (textInputLayoutEmail.editText?.text?.toString() != it) {
                textInputLayoutEmail.editText?.setText(it)
            }
        })
        viewModel.taxonomies.observe(viewLifecycleOwner, Observer {
            if (chipGroupTaxonomies.childCount == 0) {
                it?.let {
                    it.forEach { taxonomy ->
                        val chip = Chip(context)
                        chip.text = taxonomy.title
                        chip.isCheckable = true
                        chip.isChecked = taxonomy.selected
                        chip.setOnClickListener {
                            taxonomy.selected = chip.isChecked
                        }
                        chipGroupTaxonomies.addView(chip)
                    }
                    viewModel.taxonomies.value = it
                }
            }
        })
        viewModel.updateUserResponse.observe(viewLifecycleOwner, Observer {
            if (it.status == BaseResponse.STATUS.SUCCESS) Snackbar.make(
                buttonUpdate,
                it.status.name,
                Snackbar.LENGTH_SHORT
            ).show()
        })
    }

}
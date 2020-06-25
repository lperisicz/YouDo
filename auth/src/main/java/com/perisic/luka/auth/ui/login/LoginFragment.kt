package com.perisic.luka.auth.ui.login

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.perisic.luka.auth.R
import com.perisic.luka.auth.databinding.FragmentLoginBinding
import com.perisic.luka.base.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_login.*
import org.koin.android.viewmodel.ext.android.viewModel

internal class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    override val layoutId: Int = R.layout.fragment_login
    private val loginViewModel by viewModel<LoginViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
        observeData()
    }

    private fun setupUi() {
        buttonLogin.setOnClickListener {
            loginViewModel.login(
                username = textInputLayoutUsername.editText?.text?.toString() ?: "",
                password = textInputLayoutPassword.editText?.text?.toString() ?: ""
            )
        }
    }

    private fun observeData() {
        loginViewModel.loginResponse.observe(viewLifecycleOwner, Observer {
            Snackbar.make(
                textInputLayoutUsername,
                "NEKI RESPONSE: ${it.networkState.status.name}",
                Snackbar.LENGTH_SHORT
            ).show()
        })
    }

}
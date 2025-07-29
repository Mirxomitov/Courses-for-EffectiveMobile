package com.example.courses.ui.login

import android.content.Intent
import android.os.Bundle
import android.text.InputFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.core.util.isCyrillic
import com.example.courses.R
import com.example.courses.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setupObservers()
        setupClicks()
    }

    private fun setupViews() {
        val blockCyrillic = InputFilter { source, _, _, _, _, _ ->
            if (source.any { it.isCyrillic() }) "" else null
        }
        binding.etEmail.filters = arrayOf(blockCyrillic)

        binding.etEmail.doAfterTextChanged {
            viewModel.onEmailChanged(it.toString())
        }

        binding.etPassword.doAfterTextChanged {
            viewModel.onPasswordChanged(it.toString())
        }
    }

    private fun setupObservers() {
        viewModel.isFormValid.observe(viewLifecycleOwner) { isValid ->
            binding.loginButton.isEnabled = isValid
        }

        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            when (state) {
                LoginUiState.EnterLoading -> {
                    binding.loginButton.isEnabled = false
                    binding.loginProgress.visibility = View.VISIBLE
                }

                LoginUiState.EnterSuccess -> {
                    binding.loginProgress.visibility = View.GONE
                    findNavController().navigate(
                        R.id.homeFragment,
                        null,
                        NavOptions.Builder()
                            .setPopUpTo(
                                R.id.loginFragment,
                                inclusive = true,
                                saveState = true
                            )
                            .build()
                    )
                }

                is LoginUiState.EnterError -> {
                    binding.loginButton.isEnabled = true
                    binding.loginProgress.visibility = View.GONE
                    Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                }

                else -> {}
            }
        }
    }

    private fun setupClicks() {
        binding.loginButton.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            viewModel.login(email, password)
        }

        binding.buttonOk.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = "https://vk.com/".toUri()
            }
            startActivity(intent)
        }
        binding.buttonOk.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = "https://ok.ru/".toUri()
            }
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

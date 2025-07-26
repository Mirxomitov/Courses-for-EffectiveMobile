package com.example.courses.ui.login

import android.os.Bundle
import android.text.InputFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.courses.R
import com.example.courses.databinding.FragmentLoginBinding
import com.example.core.util.ui.isCyrillic
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
    }

    private fun setupClicks() {
        binding.loginButton.setOnClickListener {
            viewModel.isFormValid.value.let { isValid ->
                if (isValid == true) {
                    val email = binding.etEmail.text.toString()
                    val password = binding.etPassword.text.toString()

                    viewModel.login(email, password)
                } else {
                    binding.etEmail.error = getString(R.string.invalid_email)
                    binding.etPassword.error = getString(R.string.invalid_password)
                }
        }
    }
        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

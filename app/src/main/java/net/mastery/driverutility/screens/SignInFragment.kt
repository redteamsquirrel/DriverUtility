package net.mastery.driverutility.screens

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.widget.doOnTextChanged
import androidx.navigation.Navigation
import net.mastery.driverutility.R
import net.mastery.driverutility.application.BaseFragment
import net.mastery.driverutility.databinding.SignInFragmentBinding
import net.mastery.driverutility.extensions.setSafeOnClickListener
import net.mastery.driverutility.models.User
import net.mastery.driverutility.services.EndpointInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SignInFragment : BaseFragment() {

    override val binding get() = _binding as SignInFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SignInFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initSignInInputs()
        initAction()
    }

    private fun initSignInInputs() {
        setPasswordMask()

        binding.signInUsername.doOnTextChanged { _, _, _, _ ->
            resetErrors()
        }
        binding.signInUsername.setOnEditorActionListener { _, _, _ ->
            binding.signInUsername.setText(binding.signInUsername.text?.toString()?.trim())
            if(!validEmail(binding.signInUsername.text?.toString())) {
                binding.signInUsernameLayout.error = getString(R.string.sign_in_enter_email)
            }
            else {
                binding.signInPassword.requestFocus()
            }
            true
        }

        binding.signInPassword.inputType =
            InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
        binding.signInPassword.doOnTextChanged { _, _, _, _ ->
            resetErrors()
        }
        binding.signInPassword.setOnEditorActionListener { _, _, _ ->
            binding.signInPassword.setText(binding.signInPassword.text?.toString()?.trim())
            if(binding.signInPassword.text.isNullOrEmpty()) {
                binding.signInPasswordLayout.error = getString(R.string.sign_in_enter_password)
            }
            else {
                hideFragmentViewKeyboard(binding.signInPassword)
                signIn()
            }
            true
        }
    }

    private val emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\\.+[a-z]+"
    fun validEmail(email: String?): Boolean {
        return (!email.isNullOrEmpty() && email.matches(emailPattern.toRegex()))
    }

    private fun initAction() {
        binding.signInAction.setSafeOnClickListener {
            resetErrors()

            if(!validEmail(binding.signInUsername.text?.toString())) {
                binding.signInUsernameLayout.error = getString(R.string.sign_in_enter_email)
                return@setSafeOnClickListener
            }

            if(binding.signInPassword.text.isNullOrEmpty()) {
                binding.signInPasswordLayout.error = getString(R.string.sign_in_enter_password)
                return@setSafeOnClickListener
            }
            else {
                signIn()
            }

        }
    }

    private fun setPasswordMask() {
        try {
            val method = binding.signInPasswordLayout.javaClass.getDeclaredMethod(
                "passwordVisibilityToggleRequested",
                Boolean::class.javaPrimitiveType
            )
            method.isAccessible = true
            method.invoke(binding.signInPasswordLayout, true)
        } catch (e: Exception) {
            // nothing to do
        }
    }

    private fun resetErrors() {
        binding.signInUsernameLayout.error = ""
        binding.signInPasswordLayout.error = ""
    }

    private fun signIn() {
        val endpointInterface = EndpointInterface.create().signIn(
            username = binding.signInUsername.text.toString(),
            password = binding.signInPassword.text.toString()
        )
        with(endpointInterface) {
            enqueue( object : Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    response.body()?.let { user ->
                        view?.let { view ->
                            Navigation.findNavController(view).navigate(
                                R.id.nav_driver_list,
                                bundleOf(
                                    USER_ARG_KEY to user
                                )
                            )
                        }
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    // intentionally unimplemented to limit scope
                }
            })
        }

    }

}
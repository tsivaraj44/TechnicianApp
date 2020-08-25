package com.bpositive.technician.login.view

import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bpositive.technician.BaseFragment
import com.bpositive.R
import com.bpositive.technician.utils.ShowToast
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        signinBtn.setOnClickListener {

            val user = userNameField.text.toString()
            val password = passwordField.text.toString()

            if (TextUtils.isEmpty(user)) {
                activity?.ShowToast(resources.getString(R.string.enter_mobilno_or_email))
            } else if (!validateUser(user)) {
                activity?.ShowToast(resources.getString(R.string.enter_valid_mobilno_or_email))
            } else if (TextUtils.isEmpty(password)){
                activity?.ShowToast(resources.getString(R.string.enter_password))
            } else {
                moveToHomeScreen()
            }
        }

    }

    private fun validateUser(user: String): Boolean =
        Patterns.EMAIL_ADDRESS.matcher(user).matches() || Patterns.PHONE.matcher(user).matches()

    override fun getTitle(): String {
        return "Login"
    }

    override fun getShowHomeToolbar(): Boolean {
        return false
    }

    private fun moveToHomeScreen(){
        when (findNavController().currentDestination?.id) {
            R.id.loginFragment -> findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
        }
    }

}
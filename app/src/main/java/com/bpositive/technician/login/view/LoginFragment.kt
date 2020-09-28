package com.bpositive.technician.login.view

import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.bpositive.R
import com.bpositive.technician.BaseFragment
import com.bpositive.technician.core.PreferenceManager
import com.bpositive.technician.login.model.LoginRequest
import com.bpositive.technician.login.model.ReqGenerateOtp
import com.bpositive.technician.login.model.ReqVerifyOtp
import com.bpositive.technician.login.viewModel.LoginViewModel
import com.bpositive.technician.utils.ShowToast
import com.bpositive.technician.utils.showKeyboard
import com.bpositive.technician.utils.toast
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : BaseFragment() {

    val viewModel: LoginViewModel by lazy {
        this.let {
            ViewModelProviders.of(it, LoginViewModel.Factory(context))
                .get(LoginViewModel::class.java)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        etPhoneNo.showKeyboard()

        btnSignIn.setOnClickListener {
            if (btnSignIn.tag.toString() == "0") {
                val user = etPhoneNo.text.toString()
                if (TextUtils.isEmpty(user)) {
                    activity?.ShowToast(resources.getString(R.string.enter_mobilno))
                } else if (!validateUser(user)) {
                    activity?.ShowToast(resources.getString(R.string.enter_valid_mobilno))
                } else {
                    generateOtp()
                }
            } else {
                val otp = etOtp.text.toString()
                when {
                    TextUtils.isEmpty(otp) -> activity?.ShowToast(resources.getString(R.string.label_enter_otp))
                    otp.length != 4 -> activity?.ShowToast(resources.getString(R.string.enter_valid_otp))
                    else -> verifyOtp()
                }
            }
        }

        tvWrongNo.setOnClickListener {
            etOtp.isEnabled = false
            etPhoneNo.isEnabled = true
            btnSignIn.tag = "0"
            etOtp.setText("")
            btnSignIn.text = getString(R.string.action_send_otp)
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

    private fun generateOtp() {
        pbLogin.visibility = View.VISIBLE
        viewModel.generateOtp(
            reqGenerateOtp = ReqGenerateOtp(
                mobileNumber = etPhoneNo.text.toString(),
                countryCode = "+91",
                deviceType = Build.MANUFACTURER,
                deviceToken = PreferenceManager(requireContext()).getToken(),
                deviceId = ""
            ), onSuccess = {
                pbLogin.visibility = View.GONE
                activity?.toast(it.message.toString())
                etOtp.isEnabled = true
                etPhoneNo.isEnabled = false
                etPhoneNo.visibility = View.GONE
                etOtp.visibility = View.VISIBLE
                btnSignIn.tag = "1"
                etOtp.setText(it.otp.toString())
                btnSignIn.text = getString(R.string.action_verify_otp)
            }, onError = {
                pbLogin.visibility = View.GONE
                activity?.toast(it)
            })
    }

    private fun verifyOtp() {
        pbLogin.visibility = View.VISIBLE
        viewModel.verifyOtp(
            reqVerifyOtp = ReqVerifyOtp(
                mobileNumber = etPhoneNo.text.toString(),
                countryCode = "+91",
                otp = etOtp.text.toString(),
                deviceToken = PreferenceManager(requireContext()).getToken()
            ), onSuccess = {
                pbLogin.visibility = View.GONE
                activity?.toast(it.message.toString())
                it.details?.let { loginDetails ->
                    PreferenceManager(context!!).apply {
                        saveTechnicianId(loginDetails.id.toString().toInt())
                        saveTechnicianName(loginDetails.name.toString())
                        saveTechnicianNo(loginDetails.mobileNumber.toString())
                        saveTechnicianEmail(loginDetails.email.toString())
                    }
                    when (findNavController().currentDestination?.id) {
                        R.id.loginFragment -> findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                    }
                }
            }, onError = {
                pbLogin.visibility = View.GONE
                activity?.toast(it)
            })
    }

    private fun moveToHomeScreen() {
        pbLogin.visibility = View.VISIBLE
        viewModel.doLogin(
            loginRequest = LoginRequest(
                mobileNumber = etPhoneNo.text.toString(),
                countryCode = "+91",
                password = "passwordField.text.toString()",
                deviceType = Build.MANUFACTURER,
                deviceToken = "jnf66jdh77"
            ), onSuccess = {
                pbLogin.visibility = View.GONE
                activity?.toast(it.message.toString())
                PreferenceManager(context!!).apply {
                    saveTechnicianId(it.userDetails?.technicianId.toString().toInt())
                    saveTechnicianName(it.userDetails?.firstName + " " + it.userDetails?.lastName)
                }
                when (findNavController().currentDestination?.id) {
                    R.id.loginFragment -> findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                }
            }, onError = {
                pbLogin.visibility = View.GONE
                activity?.toast(it)
            })
    }

}
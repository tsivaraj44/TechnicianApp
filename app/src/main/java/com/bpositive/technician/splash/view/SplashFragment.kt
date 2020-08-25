package com.bpositive.technician.splash.view

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.bpositive.technician.BaseFragment
import com.bpositive.technician.MainActivity
import com.bpositive.R
import com.bpositive.technician.core.SessionManager
import com.bpositive.databinding.FragmentSplashBinding
import com.bpositive.technician.splash.viewModel.SplashViewModel
import com.bpositive.technician.utils.Language.ENGLISH
import com.bpositive.technician.utils.sessionNames.USER_LANGUAGE

class SplashFragment : BaseFragment() {

    var viewModel: SplashViewModel? = null
    private lateinit var viewDataBinding: FragmentSplashBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val session = SessionManager(context)
        val userLanguage: String? = session.getSession(USER_LANGUAGE).toString()
        chooseLanguage(if (userLanguage!!.isEmpty()) ENGLISH else userLanguage)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = FragmentSplashBinding.inflate(inflater, container, false).apply {
            viewModel = ViewModelProviders.of(this@SplashFragment).get(SplashViewModel::class.java)
        }

        viewDataBinding.splashModel = viewModel
        viewDataBinding.lifecycleOwner = this
//        viewModel?.start()
        Handler().postDelayed({
            moveToNextScreen()
        },2000)


        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        startObserveResponse()
        startObserveMessage()

        /*viewModel?.dataLoading?.observe(this, Observer {

            Log.i("Check", "frag -->>> " + it)
        })*/

        /*val splashService = SplashRepository(SplashService.create())

        splashService.searchUser("Lagos", "Java")
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->
                Log.d("Result", "Result is:${result.items.size}")
            }, { error ->
                error.printStackTrace()
            })*/

        /*viewModel.liveData?.observe(this, Observer {

            Log.i("SplashFragment", "Response isSuccessful: "+it.total_count)

        })*/


    }


    override fun getTitle(): String {
        return "Splash Screen"
    }

    override fun getShowHomeToolbar(): Boolean {
        return false
    }

    companion object {
        const val TAG = "SplashFragment"
        fun newInstanceTest() = SplashFragment()
    }

    private fun startObserveMessage() {
        viewModel?.toastMessage!!.observe(activity as MainActivity, Observer {
            Toast.makeText((activity as MainActivity), it, Toast.LENGTH_LONG).show()
        })
    }

    private fun startObserveResponse() {
        viewModel?.responseMutableLiveData!!.observe(activity as MainActivity, Observer {
            Log.i("SplashFragment", "Observed: " + it.details)

            moveToNextScreen()

        })
    }

    private fun moveToNextScreen() {
        when (findNavController().currentDestination?.id) {
            R.id.splashFragment -> findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
        }
    }

}
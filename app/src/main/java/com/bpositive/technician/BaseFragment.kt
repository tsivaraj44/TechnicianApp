package com.bpositive.technician

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.bpositive.technician.core.SessionManager
import com.bpositive.technician.utils.FragmentListener
import com.bpositive.technician.utils.sessionNames.USER_LANGUAGE
import java.util.*

abstract class BaseFragment : Fragment() {

    private var fragmentListener: FragmentListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is FragmentListener)
            fragmentListener = context
        else
            throw RuntimeException("$context must implement FragmentListener")

        if (fragmentListener != null)
            fragmentListener?.setShowHomeViews(getShowHomeToolbar())
        else
            Log.i("showHomeViews", "showHomeViews null")
    }

    open fun chooseLanguage(language: String) {
        println("selected --- language " + language)
        val sessionManager = SessionManager(context!!)
        sessionManager.saveSession(USER_LANGUAGE, language)
        val locale = Locale(language)
        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)
        context?.createConfigurationContext(config)
        context!!.getResources().updateConfiguration(
            config,
            context!!.getResources().getDisplayMetrics()
        )
        activity?.getBaseContext()?.getResources()!!
            .updateConfiguration(
                config,
                activity?.getBaseContext()!!.getResources().getDisplayMetrics()
            )

        //activity?.recreate()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

    }

    override fun onResume() {
        super.onResume()
        Log.i("showHomeViews", "showHomeViews onResume")
        if (fragmentListener != null) {
            fragmentListener?.setTitle(getTitle())

            Log.i("showHomeViews", "showHomeViews onResume fragmentListener")

            fragmentListener?.setShowHomeViews(getShowHomeToolbar())
            

        } else
            Log.i("showHomeViews", "showHomeViews onResume fragmentListener null")
    }

    protected abstract fun getTitle(): String

    protected abstract fun getShowHomeToolbar(): Boolean


}
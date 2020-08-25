package com.bpositive.technician.language.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.bpositive.technician.BaseFragment
import com.bpositive.technician.MainActivity
import com.bpositive.R
import com.bpositive.databinding.FragmentLanguageBinding
import com.bpositive.technician.language.view.adapter.LanguageSelectionAdapter
import com.bpositive.technician.language.viewModel.LanguageViewModel

/**
 * A simple [Fragment] subclass.
 */
class LanguageFragment : BaseFragment() {

    var viewModel: LanguageViewModel? = null
    private lateinit var dataBinding: FragmentLanguageBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_language, container, false)

        dataBinding = FragmentLanguageBinding.inflate(inflater, container, false).apply {
            viewModel =
                ViewModelProviders.of(this@LanguageFragment).get(LanguageViewModel::class.java)
        }

        dataBinding.languageModel = viewModel

        return dataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        dataBinding.lifecycleOwner = this
        setAdapter()
        viewModel?.start()

        viewModel?.onSelectedLang!!.observe(activity as MainActivity, Observer {
            changeLanguage(it.langCode)
        })

    }

    override fun onResume() {
        super.onResume()
//        findNavController().navigate(R.id.action_languageFragment_to_homeFragment)
    }

    private fun setAdapter() {
        if (viewModel != null) {
            val languageSelectionAdapter = LanguageSelectionAdapter(viewModel)
            dataBinding.languageRecyclerView.adapter = languageSelectionAdapter
        }
    }

    override fun getTitle(): String {
        return "Language"
    }

    override fun getShowHomeToolbar(): Boolean {
        return false
    }

    private fun changeLanguage(locale: String) {
        // Language code here
        moveToHomeScree()
    }

    private fun moveToHomeScree() {
        when (findNavController().currentDestination?.id) {
            R.id.languageFragment -> {
//                findNavController().navigate(R.id.action_languageFragment_to_homeFragment)
            }
        }

    }

}

package com.bpositive.technician.home.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bpositive.technician.BaseFragment
import com.bpositive.technician.MainActivity
import com.bpositive.R
import com.bpositive.databinding.FragmentHomeBinding
import com.bpositive.technician.home.model.DomainListItems
import com.bpositive.technician.home.view.adapter.HomeDomainListAdapter
import com.bpositive.technician.home.viewModel.HomeViewModel


/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : BaseFragment() {

    companion object {
        private val TAG = this.javaClass.simpleName
    }

    var viewModel: HomeViewModel? = null
    private lateinit var dataBinding: FragmentHomeBinding

    private lateinit var domainListAdapter: HomeDomainListAdapter


    override fun getTitle(): String {
        return resources.getString(R.string.app_name)
    }

    override fun getShowHomeToolbar(): Boolean {
        return true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_home, container, false)

        dataBinding = FragmentHomeBinding.inflate(inflater, container, false).apply {
            viewModel = ViewModelProviders.of(this@HomeFragment).get(HomeViewModel::class.java)
        }

        dataBinding.homeModel = viewModel
//        dataBinding.lifecycleOwner = this
//
//        viewModel?.start()

        return dataBinding.root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        dataBinding.lifecycleOwner = this
        setupDomainAdapter()
        viewModel?.start()

        startObserveHomeResponse()
    }

    /*private fun setAdapter(domainResponse: HomeDomainListResponse) {
//        homeRecyclerView.layoutManager = GridLayoutManager(activity as MainActivity,GRID_SPAN_COUNT)

        //MiddleDivideritemDecoration.ALL means both vertical and horizontal dividers
        //You can also use MiddleDividerItemDecoration.VERTICAL / MiddleDividerItemDecoration.HORIZONTAL if you just want horizontal / vertical dividers
//        homeRecyclerView.addItemDecoration(MiddleDividerItemDecoration(context!!, MiddleDividerItemDecoration.VERTICAL))
//        homeRecyclerView.layoutManager = GridLayoutManager(context!!, GRID_SPAN_COUNT)

        homeRecyclerView.layoutManager =
            GridLayoutManager(activity as MainActivity, GRID_SPAN_COUNT)
//        homeRecyclerView.addItemDecoration(GridItemDecoration(10, GRID_SPAN_COUNT))

//        val lDrawable = ContextCompat.getDrawable(activity as MainActivity, R.drawable.line_divider)
//        val rDrawable = ContextCompat.getDrawable(activity as MainActivity, R.drawable.line_divider)
//        homeRecyclerView.addItemDecoration(GridDividerItemDecoration(lDrawable, rDrawable,2))

        homeRecyclerView.adapter =
            HomeDomainListAdapter(
                viewModel,
                domainResponse.domainItems as List<DomainListItems>,
                activity as MainActivity
            )


        *//*Handler().postDelayed({
            when (findNavController().currentDestination?.id) {
                R.id.homeFragment -> findNavController().navigate(R.id.action_homeFragment_to_homeDetailFragment)
            }

        }, 1000)*//*

//        moveToCaseDesc()

    }*/

    private fun startObserveHomeResponse() {
        /*viewModel?.responseDetails!!.observe(activity as MainActivity, Observer {
//            Toast.makeText(activity, "it", Toast.LENGTH_LONG).show()
//            setAdapter(it)
        })*/

        viewModel?.clickedItemLiveData!!.observe(activity as MainActivity, Observer {
            Log.i(TAG, "clickedItemLiveData: " + it.domain_name)
            moveToCaseListScreen(it)
        })
    }

    /*private fun moveToCaseDesc() {

        var bundle: Bundle = Bundle()

        //requiredAmount, name, followers, serialNumber, raisedAmount, balanceAmount, description, serialNumber, date, time, viewers

        bundle.putString("requiredAmount", "700")
        bundle.putString("name", "Ravikumar")
        bundle.putString("followers", "0135")
        bundle.putString("serialNumber", "123")
        bundle.putString("raisedAmount", "325")
        bundle.putString("balanceAmount", "375")
        bundle.putString("currency", "SDG")

        var jsonArray: JSONArray = JSONArray()
        var jsonObject: JSONObject = JSONObject()
        jsonObject.put("type", 1)
        jsonObject.put("url", "https://fakeimg.pl/350x200/ff0000/000")
        jsonArray.put(jsonObject)
        var jsonObject1: JSONObject = JSONObject()
        jsonObject1.put("type", 2)
        jsonObject1.put("url", "https://s3.amazonaws.com/uifaces/faces/twitter/araa3185/128.jpg")
        jsonArray.put(jsonObject1)
        var jsonObject2: JSONObject = JSONObject()
        jsonObject2.put("type", 2)
        jsonObject2.put("url", "https://fakeimg.pl/350x200/?text=World&font=lobster")
        jsonArray.put(jsonObject1)

        var sourceArray: String = jsonArray.toString()
        bundle.putString("sourceArray", sourceArray)

        bundle.putString(
            "description",
            "Android mobile application is one of the best mobile device in world wild. because lot of the future is available and lot of the update's are released by the google."
        )
        bundle.putString("date", "05/03/2020")
        bundle.putString("time", "07:10:2020")
        bundle.putString("viewers", "501")

        bundle.putString(
            "headerImage",
            "https://s3.amazonaws.com/uifaces/faces/twitter/araa3185/128.jpg"
        )
        bundle.putString(
            "mainImage",
            "https://s3.amazonaws.com/uifaces/faces/twitter/araa3185/128.jpg"
        )


        *//*replaceFragmentInActivity(SettingsFragment.newInstanceTest().apply {
            arguments = bundle
        }, R.id.fragment_container, SplashFragment.TAG)*//*

        *//*Handler().postDelayed({
            when (findNavController().currentDestination?.id) {
                R.id.homeFragment -> findNavController().navigate(
                    R.id.action_homeFragment_to_caseDetailsFragment,
                    bundle
                )
            }

        }, 3000)*//*

    }*/

    private fun moveToCaseListScreen(clickedItems: DomainListItems) {
        /*when (findNavController().currentDestination?.id) {
            R.id.homeFragment -> {

                findNavController().navigate(
                    R.id.action_homeFragment_to_homeDetailFragment,
                    bundleOf(
                        DOMAIN_ID to clickedItems.id,
                        DOMAIN_IMG_URL to clickedItems.domain_image,
                        DOMAIN_NAME to clickedItems.domain_name
                    )
                )
            }
        }*/
    }

    private fun setupDomainAdapter() {
        if (viewModel != null) {
            domainListAdapter = HomeDomainListAdapter(viewModel)
            dataBinding.homeRecyclerView.adapter = domainListAdapter
        }
    }

}

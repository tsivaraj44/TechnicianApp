package com.bpositive.technician.homeDetail.view

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.bpositive.technician.BaseFragment
import com.bpositive.R
import com.bpositive.technician.core.widget.CustomTextView
import com.bpositive.technician.core.widget.LoginDialog
import com.bpositive.databinding.HomeDetailFragAdapter
import com.bpositive.technician.homeDetail.model.InitiatorsItem
import com.bpositive.technician.homeDetail.model.RequestHomeDetail
import com.bpositive.technician.homeDetail.view.recyclerView.HomeDetailAdapter
import com.bpositive.technician.homeDetail.view.recyclerView.PaginationListener
import com.bpositive.technician.homeDetail.view.recyclerView.PaginationListener.Companion.PAGE_START
import com.bpositive.technician.homeDetail.view.sortFilterDialog.FilterDialog
import com.bpositive.technician.homeDetail.view.sortFilterDialog.SortDialog
import com.bpositive.technician.homeDetail.viewModel.HomeDetailViewModel
import com.bpositive.technician.utils.*
import com.bpositive.technician.utils.BundleConstants.DOMAIN_ID
import com.bpositive.technician.utils.BundleConstants.DOMAIN_IMG_URL
import com.bpositive.technician.utils.BundleConstants.DOMAIN_NAME
import com.bpositive.technician.utils.orderByConstants.DESCENDING
import kotlinx.android.synthetic.main.fragment_home_detail.*

class HomeDetailFragment : BaseFragment(), View.OnClickListener, LoginDialog.CustomDialogListener {

    var viewModel: HomeDetailViewModel? = null
    lateinit var viewDataBinding: HomeDetailFragAdapter
    private var mIsLastPage = false
    private var mIsLoading = false
    var requestHomeDetail = RequestHomeDetail(
        order = "$DESCENDING",  //  1-> ASC, 0-> DESC
        limit = "$limit",
        offset = PAGE_START
    )

    var headerTitle: String? = null
    var headerImgUrl: String? = null

    private lateinit var loginDialog: LoginDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        headerImgUrl = arguments?.getString(DOMAIN_IMG_URL)
        headerTitle = arguments?.getString(DOMAIN_NAME)


        println("headerImgUrl: $headerImgUrl")
        headerImagesTitle(headerImgUrl, headerTitle)

        viewDataBinding = HomeDetailFragAdapter.inflate(inflater, container, false).apply {
            viewModel =
                ViewModelProviders.of(this@HomeDetailFragment).get(HomeDetailViewModel::class.java)
            homeDetailModel = viewModel
            lifecycleOwner = this@HomeDetailFragment
        }
        viewModel?.getHomeDetailResponse(
            requestHomeDetail.apply {
                categoryId = "${arguments?.getInt(DOMAIN_ID, 0)}"
            }, {}, isListAvailable = { isListAvailable ->
                if (isListAvailable)
                    tvNoCase.visibility = View.GONE
                else
                    tvNoCase.visibility = View.VISIBLE
            }, onSuccessInitiatorList = {
                val adapter = InitiatorSearchAdapter(context!!, R.layout.initiator_item, it)
                adapter.setList(it)
                acTvSearch.setAdapter(adapter)
            })
        return viewDataBinding.root
    }

    override fun getTitle(): String {
        return "Details"
    }

    override fun getShowHomeToolbar(): Boolean {
        return true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        rvHomeDetail.adapter = HomeDetailAdapter(
            viewModel as HomeDetailViewModel,
            homeDetailListener = { homeDetail, isContribute ->
                when (isContribute) {
                    1 -> {      //  Contribute
                        loginDialog = LoginDialog(this)
                        loginDialog.show(childFragmentManager, loginDialog.tag)
                    }
                    /*2 -> {     //  Detail
                        if (findNavController().currentDestination?.id == R.id.homeDetailFragment)
                            findNavController().navigate(
                                R.id.action_homeDetailFragment_to_caseDetailsFragment, bundleOf(
                                    BundleConstants.CASE_DETAILS to homeDetail
                                )
                            )
                    }*/
                    3 -> {     //  Follow
                    }
                }
            })

        rvHomeDetail.addOnScrollListener(object :
            PaginationListener(rvHomeDetail.layoutManager as LinearLayoutManager) {
            override fun loadMoreItems() {
                if (!mIsLoading && !mIsLastPage) {
                    mIsLoading = true
                    doApiCall(requestHomeDetail.apply { offset += 1 })
                }
            }

            override val isLastPage: Boolean = mIsLastPage
            override var isLoading = mIsLoading
        })

        tvFilter.setOnClickListener(this)
        tvSort.setOnClickListener(this)
        tvClear.setOnClickListener(this)
        tvSearch.setOnClickListener(this)

        ivSearch.setOnClickListener {
            if (TextUtils.isEmpty(acTvSearch.text.toString())) {
                tvSearch.text = resources.getString(R.string.label_search)
                view.hideKeyboard()
                hideSearchView()
            } else {
                acTvSearch.setText("")
                mIsLoading = true
                doApiCall(requestHomeDetail.apply {
                    initiatorId = null
                    offset = PAGE_START
                })
            }
        }

        acTvSearch.setOnItemClickListener { parent, view, position, id ->
            val initiator = parent?.getItemAtPosition(position) as InitiatorsItem
            acTvSearch.setText(initiator.initiatiorName)
            resetList()
            hideSearchView()
            tvSearch.text = acTvSearch.text.toString()
            doApiCall(
                requestHomeDetail.apply {
                    initiatorId = initiator.initiatorId.toString()
                    offset = PAGE_START
                }
            )
        }

    }

    private fun hideSearchView() {
        groupNormalView.visibility = View.VISIBLE
        groupSearchView.visibility = View.GONE
    }

    private fun doApiCall(requestHomeDetail: RequestHomeDetail) {
        if (this.requestHomeDetail.offset.toString().toInt() <= totalPage && mIsLoading) {
            viewModel?.getHomeDetailResponse(requestHomeDetail, { isSuccess ->
                if (isSuccess)
                    mIsLastPage = true
                mIsLoading =
                    false  //  Callback, so that the scroll listener won't call the api more than once
                view?.hideKeyboard()
            }, isListAvailable = { isListAvailable ->
                if (isListAvailable)
                    tvNoCase.visibility = View.GONE
                else
                    tvNoCase.visibility = View.VISIBLE
            }, onSuccessInitiatorList = {
                if (acTvSearch.adapter == null) {
                    val adapter = InitiatorSearchAdapter(context!!, R.layout.initiator_item, it)
                    adapter.setList(it)
                    acTvSearch.setAdapter(adapter)
                } else {
                    (acTvSearch.adapter as InitiatorSearchAdapter).setList(it)
                }
            })
        } else
            mIsLastPage = true
    }

    private fun resetList() {
        mIsLoading = true
        mIsLastPage = false
        (rvHomeDetail.adapter as HomeDetailAdapter).clear()
        tvNoCase.visibility = View.VISIBLE
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tvSort -> {
                SortDialog.showSortDialog(childFragmentManager) { mSort, orderFromDialog ->
                    resetList()
                    doApiCall(
                        requestHomeDetail.apply {
                            orderBy = mSort
                            order = "$orderFromDialog"
                            offset = PAGE_START
                        }
                    )
                }
            }
            R.id.tvSearch -> {
                groupNormalView.visibility = View.GONE
                groupSearchView.visibility = View.VISIBLE
                view?.showKeyboard()
            }
            R.id.tvFilter -> {
                FilterDialog.showFilterDialog(childFragmentManager) { mStartDate, mEndDate ->
                    resetList()
                    doApiCall(
                        requestHomeDetail.apply {
                            startDate = mStartDate
                            endDate = mEndDate
                            offset = PAGE_START
                        }
                    )
                }
            }
            R.id.tvClear -> {
                context?.showAlert("Sure want to clear this filter?", confirmation = {
                    if (it) {
                        resetList()
                        tvSearch.text = resources.getString(R.string.label_search)
                        acTvSearch.setText("")
                        doApiCall(
                            requestHomeDetail.apply {
                                order = "$DESCENDING"
                                offset = PAGE_START
                                startDate = ""
                                endDate = ""
                                orderBy = ""
                                initiatorId = null
                            }
                        )
                    }
                })
            }
        }
    }

    companion object {
        const val totalPage = 10
        const val limit = 10
    }

    override fun onCustomDialogListener() {
        loginDialog.dismiss()
    }

    fun headerImagesTitle(url: String?, title: String?) {
        activity?.findViewById<LinearLayout>(R.id.followerLay)?.visibility = View.GONE
        activity?.findViewById<CustomTextView>(R.id.nameTv)?.text = title
        val imgView = activity?.findViewById<AppCompatImageView>(R.id.userImage)!!
        imgView.loadImage(headerImgUrl!!)
        imgView.background = null
    }



}

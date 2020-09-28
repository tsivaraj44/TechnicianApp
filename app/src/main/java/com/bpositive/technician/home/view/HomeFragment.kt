package com.bpositive.technician.home.view

import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.bpositive.R
import com.bpositive.technician.BaseFragment
import com.bpositive.technician.core.PreferenceManager
import com.bpositive.technician.home.model.Settlement
import com.bpositive.technician.home.viewModel.HomeViewModel
import com.bpositive.technician.myProfile.model.ProfileRequest
import com.bpositive.technician.utils.BroadCastConstant.LOCAL_BROADCAST
import com.bpositive.technician.utils.NotificationBroadCast
import com.bpositive.technician.utils.NotificationListener
import com.bpositive.technician.utils.toast
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseFragment(), NotificationListener {

    val viewModel: HomeViewModel by lazy {
        this.let {
            ViewModelProviders.of(it, HomeViewModel.Factory(context))
                .get(HomeViewModel::class.java)
        }
    }

    private var notificationBroadCast: NotificationBroadCast? = null

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
        notificationBroadCast = NotificationBroadCast()
        context?.registerReceiver(notificationBroadCast, IntentFilter(LOCAL_BROADCAST))
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        notificationBroadCast?.bindListener(this)

        rvSettlement.adapter = SettlementAdapter {

        }

        btnProfile.setOnClickListener {
            findNavController().navigate(R.id.myProfileFragment)
        }
        btnMyWork.setOnClickListener {
            findNavController().navigate(R.id.myWorkFragment)
        }

        getSettlement()

    }

    private fun getSettlement() {
        pbSettlement?.visibility = View.VISIBLE
        viewModel.getSettlement(
            profileRequest = ProfileRequest(
                PreferenceManager(context!!).getTechnicianId().toString().toInt()
            ),
            onSuccess = { settlement ->
                if (settlement.settlementList != null && settlement.settlementList.isNotEmpty()) {
                    (rvSettlement?.adapter as SettlementAdapter).addSettlementList(settlement.settlementList as List<Settlement>)
                    tvNoSettlement.visibility = View.GONE
                }
                tvTotalAmount.text =
                    getString(R.string.label_settlement) + " " + settlement.givenAmount.toString()
                pbSettlement?.visibility = View.GONE
            },
            onError = {
                activity?.toast(it)
                tvTotalAmount.text = getString(R.string.label_settlement) + " 0.00"
                pbSettlement?.visibility = View.GONE
            })
    }

    override fun onDestroyView() {
        context?.unregisterReceiver(notificationBroadCast)
        super.onDestroyView()
    }

    override fun callHomeDomainApi() {
        getSettlement()
    }

}

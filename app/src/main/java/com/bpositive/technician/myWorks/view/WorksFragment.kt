package com.bpositive.technician.myWorks.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bpositive.R
import com.bpositive.technician.core.PreferenceManager
import com.bpositive.technician.myWorks.model.request.MoveToPendingReq
import com.bpositive.technician.myWorks.model.request.MyWorkRequest
import com.bpositive.technician.myWorks.model.response.Works
import com.bpositive.technician.myWorks.view.adapter.MyWorkAdapter
import com.bpositive.technician.myWorks.view.fragment.CompletedDialogFragment
import com.bpositive.technician.myWorks.viewModel.MyWorksViewModel
import com.bpositive.technician.utils.BundleConstants.WORK
import com.bpositive.technician.utils.BundleConstants.WORK_STATUS
import com.bpositive.technician.utils.TravelStatus.COMPLETED
import com.bpositive.technician.utils.TravelStatus.IN_PROGRESS
import com.bpositive.technician.utils.TravelStatus.PENDING
import com.bpositive.technician.utils.TravelStatus.UP_COMING
import com.bpositive.technician.utils.WorkStatus
import com.bpositive.technician.utils.showMoveTo
import com.bpositive.technician.utils.toast
import kotlinx.android.synthetic.main.fragment_work.*

class WorksFragment(val type: Int, val viewModel: MyWorksViewModel) : Fragment() {

    /*val viewModel: MyWorksViewModel by lazy {
        this.let {
            ViewModelProviders.of(it, MyWorksViewModel.Factory(context))
                .get(MyWorksViewModel::class.java)
        }
    }*/

    private var rootView: View? = null
    private var isApiCalled = false
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (rootView == null) rootView = inflater.inflate(R.layout.fragment_work, container, false)
        return rootView;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        strWork.setOnRefreshListener {
            getMyWork()
            strWork.isRefreshing = false
        }

        /*strWork.post {
            getMyWork()
            strWork.isRefreshing = false
        }*/

        rvMyWork.adapter = MyWorkAdapter(type) { work ->
            when (type) {
                UP_COMING -> {
                    when (findNavController().currentDestination?.id) {
                        R.id.myWorkFragment -> {
                            findNavController().navigate(
                                R.id.action_myWorkFragment_to_workDetailFragment,
                                bundleOf(WORK to work, WORK_STATUS to WorkStatus.UPCOMING)
                            )
                        }
                    }
                }
                IN_PROGRESS -> {
                    when (findNavController().currentDestination?.id) {
                        R.id.myWorkFragment -> {
                            findNavController().navigate(
                                R.id.action_myWorkFragment_to_workDetailFragment,
                                bundleOf(WORK to work, WORK_STATUS to IN_PROGRESS)
                            )
                        }
                    }
                }
                PENDING -> {
                    when (findNavController().currentDestination?.id) {
                        R.id.myWorkFragment -> {
                            findNavController().navigate(
                                R.id.action_myWorkFragment_to_workDetailFragment,
                                bundleOf(WORK to work, WORK_STATUS to PENDING)
                            )
                        }
                    }
                }
            }
        }

        if (type == IN_PROGRESS && !isApiCalled) {
            getMyWork()
            isApiCalled = true
        }

    }

    fun getMyWork() {
        pbWorks?.visibility = View.VISIBLE
        viewModel.getWorkList(
            myWorkRequest = MyWorkRequest(
                PreferenceManager(context!!).getTechnicianId(),
                type
            ), onSuccess = {
                it.details?.let { workList ->
                    if (!workList.isNullOrEmpty()) {
                        (rvMyWork?.adapter as MyWorkAdapter).addWorkList(workList as List<Works>)
                        tvNoWorks?.visibility = View.GONE
                    }
                }
                pbWorks?.visibility = View.GONE
            }, onError = {
                activity?.toast(it)
                pbWorks?.visibility = View.GONE
            })
    }

    private fun moveTo(work: Works) {
        context?.showMoveTo {
            when (it) {
                PENDING ->
                    moveToPending(work)
                COMPLETED ->
                    completeWork(work)
            }
        }
    }

    private fun moveToPending(work: Works) {
        CompletedDialogFragment.getInstance("Move to pending", false) { cost, comment ->
            pbWorks.visibility = View.VISIBLE
            /*{"technician_id":1,"job_id":7,"amount":100.00,"comments":"some work balance"}*/
            viewModel.moveToPending(
                moveToPendingReq = MoveToPendingReq(
                    jobId = work.jobId.toString().toInt(),
                    technicianId = work.technicianId.toString().toInt(),
                    amount = cost.toDouble(), comments = comment
                ), onSuccess = {
                    pbWorks.visibility = View.GONE
                    activity?.toast(it.message.toString())
                }, onError = {
                    activity?.toast(it)
                    pbWorks.visibility = View.GONE
                })
        }.show(childFragmentManager, CompletedDialogFragment.javaClass.simpleName)
    }

    private fun completeWork(work: Works) {
        CompletedDialogFragment.getInstance("Complete", true) { cost, comment ->
            pbWorks.visibility = View.VISIBLE
            /*{"technician_id":1,"job_id":7,"amount":100.00,"comments":"rep changed"}*/
            val files = mutableListOf("", "", "")
            viewModel.completeWork(
                moveToPendingReq = MoveToPendingReq(
                    jobId = work.jobId.toString().toInt(),
                    technicianId = work.technicianId.toString().toInt(),
                    amount = cost.toDouble(), comments = comment
                ), files = files, onSuccess = {
                    pbWorks.visibility = View.GONE
                    activity?.toast(it.message.toString())
                }, onError = {
                    activity?.toast(it)
                    pbWorks.visibility = View.GONE
                })
        }.show(childFragmentManager, CompletedDialogFragment.javaClass.simpleName)
    }

    override fun onDestroyView() {
        if (view != null) {
            val parentViewGroup = view?.parent as ViewGroup?
            parentViewGroup?.removeAllViews();
        }
        super.onDestroyView()
    }

    companion object {
        fun newInstance(type: Int, viewModel: MyWorksViewModel) = WorksFragment(type, viewModel)
    }

}
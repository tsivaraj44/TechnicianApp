package com.bpositive.technician.myWorks.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.bpositive.R
import com.bpositive.technician.myWorks.model.request.MyWorkRequest
import com.bpositive.technician.myWorks.model.request.StartWorkRequest
import com.bpositive.technician.myWorks.model.response.Works
import com.bpositive.technician.myWorks.view.adapter.MyWorkAdapter
import com.bpositive.technician.myWorks.viewModel.MyWorksViewModel
import com.bpositive.technician.utils.toast
import kotlinx.android.synthetic.main.fragment_work.*

class WorksFragment(val type: Int) : Fragment() {

    val viewModel: MyWorksViewModel by lazy {
        this.let {
            ViewModelProviders.of(it, MyWorksViewModel.Factory(context))
                .get(MyWorksViewModel::class.java)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_work, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvMyWork.adapter = MyWorkAdapter { work ->
            pbWorks.visibility = View.VISIBLE
            viewModel.startWork(
                startWorkRequest = StartWorkRequest(
                    work.jobId.toString().toInt(),
                    work.technicianId.toString().toInt()
                ), onSuccess = {
                    pbWorks.visibility = View.GONE
                    activity?.toast(it.message.toString())
                }, onError = {
                    pbWorks.visibility = View.GONE
                })
        }

        getMyWork()
    }

    private fun getMyWork() {
        pbWorks.visibility = View.VISIBLE
        viewModel.getWorkList(myWorkRequest = MyWorkRequest(1, type), onSuccess = {
            it.details?.let { workList ->
                if (!workList.isNullOrEmpty()) {
                    (rvMyWork.adapter as MyWorkAdapter).addWorkList(workList as List<Works>)
                    tvNoWorks.visibility = View.GONE
                }
            }
            pbWorks.visibility = View.GONE
        }, onError = {
            pbWorks.visibility = View.GONE
        })
    }

    companion object {
        fun newInstance(type: Int) = WorksFragment(type)
    }

}
package com.bpositive.technician.home.service.repository

import com.bpositive.technician.core.NetworkManager
import com.bpositive.technician.home.model.DomainListItems
import com.bpositive.technician.home.model.HomeDomainListResponse
import com.bpositive.technician.home.service.HomeService
import com.bpositive.technician.utils.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class HomeRepository {

    val completedJOb = Job()
    private val backgroundScope = CoroutineScope(Dispatchers.IO + completedJOb)
    private val foregroundScope = CoroutineScope(Dispatchers.Main)


    private val homeRepository: HomeService by lazy {
        NetworkManager.baseURL(BaseData.BASE_URL).serviceClass(HomeService::class.java)
            .create<HomeService>()
    }

    fun callDomainListAPI(taskCallback: TaskCallback<HomeDomainListResponse>) {

        /*backgroundScope.launch {
            when (val result: Result<HomeDomainListResponse> =
                homeRepository.getHomeDomainList(type = APITypes.getdomains).awaitResult()) {

                is Result.Ok -> foregroundScope.launch { taskCallback.onComplete(result.value) }

                is Result.Error -> foregroundScope.launch { taskCallback.onException(result.exception) }

                is Result.Exception -> foregroundScope.launch { taskCallback.onException(result.exception) }

            }
        }*/

        // Just Uncomment the above code for API call

        var domainList1 = DomainListItems(1,"Category1","")
        var domainList2 = DomainListItems(2,"Category2","")
        var domainList3 = DomainListItems(3,"Category3","")
        var domainList4 = DomainListItems(4,"Category4","")
        var domainList5 = DomainListItems(5,"Category5","")
        var domainList6 = DomainListItems(6,"Category6","")
        var domainList7 = DomainListItems(7,"Category7","")
        var domainList8 = DomainListItems(8,"Category8","")
        var domainList9 = DomainListItems(9,"Category9","")

        val details = ArrayList<DomainListItems>()
        details.add(domainList1)
        details.add(domainList2)
        details.add(domainList3)
        details.add(domainList4)
        details.add(domainList5)
        details.add(domainList6)
        details.add(domainList7)
        details.add(domainList8)
        details.add(domainList9)

        var response = HomeDomainListResponse("Test Message,",1,
            details)

        taskCallback.onComplete(response)

    }

}
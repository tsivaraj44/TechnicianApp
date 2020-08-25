package com.bpositive.technician.caseDetails.model


data class CaseDetailsModel(
    var mainImage: String = "",
    var headerImage: String = "",
    var name: String = "",
    var follwer: String = "",
    var follwerImage: String = "",
    var serialNo: String = "",
    var requiredAmount: Int = 1000,
    var risedAmount: Int = 0,
    var balanceAmount: Int = 0,
    var descriptionText: String = "",
    var date: String = "",
    var time: String = "",
    var viewers: String = "",
    var data: ArrayList<Source> = ArrayList()
) {
    class Source {
        var url: String = ""
        var type: Int = 0
        var name: String = ""
    }
}

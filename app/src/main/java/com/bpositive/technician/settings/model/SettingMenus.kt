package com.bpositive.technician.settings.model

data class SettingMenus(
    var message: String = "",
    var status: Int = 0,
    var details: List<Details>
) {
    data class Details(
        var id: String = "",
        var domain_name: String = "",
        var domain_image: String = "",
        var selected: String = ""
    )
}
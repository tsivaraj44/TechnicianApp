package com.bpositive.technician.home.model

import com.google.gson.annotations.SerializedName


data class HomeDomainListResponse(
    val message: String,
    val status: Int,

    @SerializedName("details")
    val domainItems: List<DomainListItems>?=null
)


data class DomainListItems(
    val id: Int,
    val domain_name: String,
    val domain_image: String
)

/*data class HomeDomainListResponse(
    val total_count: Int, val items: List<DomainListItems>
)


data class DomainListItems(
    val login: String,
    val id: Long,
    val url: String,
    val html_url: String,
    val followers_url: String,
    val following_url: String,
    val starred_url: String,
    val gists_url: String,
    val type: String,
    val score: Int
)*/


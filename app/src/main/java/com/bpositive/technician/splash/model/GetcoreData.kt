package com.bpositive.technician.splash.model

/*
data class GetcoreRequest(
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
)

data class GetCoreResponse(val total_count:Int, val items:List<GetcoreRequest>)*/

data class GetCoreResponse(val status: Int, val message: String, val details: Details)

data class Details(val contact_name: String)



package com.bpositive.technician.myWorks.model.response

import com.google.gson.annotations.SerializedName

data class StartWorkResponse(
    @SerializedName("details") val details: Details? = null,
    @SerializedName("http_status") val httpStatus: Int? = null,
    @SerializedName("message") val message: String? = null,
    @SerializedName("status") val status: Boolean? = null
)
/*{"message":"Please upload the valid image with maximum file size is 78.13 MB.",
"http_status":4,"status":false,"job_attachments_1":false,"job_attachments_2":false,
"job_attachments_3":false,
"files_list":{"job_attachments_1":{"name":"1600964675768.jpeg","type":"image\/*","tmp_name":"\/tmp\/php7UEegd","error":0,"size":41936}}}*/
 */
data class Details(
    @SerializedName("job_id") val jobId: String? = null
)

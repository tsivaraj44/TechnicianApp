package com.bpositive.technician.homeDetail.model

import android.os.Parcelable
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter
import com.google.gson.annotations.SerializedName
import com.bpositive.technician.utils.makeCircularImage
import com.bpositive.technician.utils.replaceComma
import kotlinx.android.parcel.Parcelize

data class ResponseHomeDetail(
    @SerializedName("details") val detailList: List<Details?>? = null,
    @SerializedName("initiators") val initiatorList: List<InitiatorsItem?>? = null,
    val message: String? = null,
    val status: Int? = null
)

data class InitiatorsItem(
    @SerializedName("initiator_id") val initiatorId: String? = null,
    @SerializedName("initiatior_name") val initiatiorName: String? = null
)

@Parcelize
data class Details(
    val date: String? = null,
    @SerializedName("initiator_image") val initiatorImage: String? = null,
    @SerializedName("initiatior_name") val initiatiorName: String? = null,
    @SerializedName("case_description") val caseDescription: String? = null,
    @SerializedName("national_number") val nationalNumber: String? = null,
    @SerializedName("currency_code") val currencyCode: String? = null,
    @SerializedName("required_amount") val requiredAmount: String? = null,
    @SerializedName("raised_amount") val raisedAmount: String? = null,
    @SerializedName("balance_amount") val balanceAmount: String? = null,
    @SerializedName("follow_count") val followCount: String? = null,
    val id: String? = null,
    val time: String? = null,
    val views: String? = null,
    val documents: List<String>? = null
) : Parcelable {
    companion object {
        @JvmStatic
        @BindingAdapter("load_pic")
        fun loadPicture(imageView: ImageView, url: String?) {
            imageView.makeCircularImage(url.toString())
        }

        @JvmStatic
        @BindingAdapter("custom_progress", "balanceAmount", "raisedAmount", "isBalance")
        fun setProgress(
            progressBar: ProgressBar,
            requiredAmount: String,
            balanceAmount: String,
            raisedAmount: String,
            isBalance: Boolean
        ) {
            val percentage = if (!isBalance) {
                if (raisedAmount.replaceComma() != 0L) {
                    ((requiredAmount.replaceComma().minus(raisedAmount.replaceComma())).times(100)).div(
                        requiredAmount.replaceComma()
                    )
                } else 0
            } else {
                if (raisedAmount.replaceComma() != 0L) {
                    ((requiredAmount.replaceComma().minus(balanceAmount.replaceComma())).times(100)).div(
                        requiredAmount.replaceComma()
                    )
                } else 0
            }

            progressBar.progress = percentage.toInt()
        }
    }
}
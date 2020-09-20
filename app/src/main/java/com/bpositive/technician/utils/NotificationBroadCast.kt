package com.bpositive.technician.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class NotificationBroadCast : BroadcastReceiver() {

    var notificationListener: NotificationListener? = null

    fun bindListener(notificationListener: NotificationListener) {
        this.notificationListener = notificationListener
    }

    override fun onReceive(p0: Context?, p1: Intent?) {
        notificationListener?.callHomeDomainApi()
    }

}

interface NotificationListener {
    fun callHomeDomainApi()
}
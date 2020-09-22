package com.bpositive.technician

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.bpositive.R
import com.bpositive.technician.core.PreferenceManager
import com.bpositive.technician.utils.BroadCastConstant.LOCAL_BROADCAST
import com.bpositive.technician.utils.BundleConstants
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.json.JSONObject

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        Log.d(TAG, "From: ${remoteMessage.from}")
        Log.d(TAG, "From data : ${remoteMessage.data}")

        /*{"multicast_id":7081988626618154947,"success":0,"failure":1,"canonical_ids":0,"results":[{"error":"MismatchSenderId"}]}*/
        /*{"title":"Kiranaa-Order notification","body":"Hi User,Your order is inprogress.","sound":"default","badge":"1"}*/

        remoteMessage.data.let { msg ->
            msg.isNotEmpty().let {
                if (it) {
                    sendNotification(segregateMessage(remoteMessage), remoteMessage)
                }
            }
        }

        remoteMessage.notification?.let {
            Log.d(TAG, "Message Notification Body: ${it.body}")
        }

    }

    override fun onNewToken(token: String) {
        println("GET___________TOKEN________: $token")
        println("GET___________TOKEN________: ${FirebaseInstanceId.getInstance().token.toString()}")
        PreferenceManager(baseContext).saveToken(token)
        sendRegistrationToServer(token)
    }

    /**
     * Schedule async work using WorkManager.
     */
    private fun scheduleJob() {
        // [START dispatch_job]
        // val work = OneTimeWorkRequest.Builder(MyWorker::class.java).build()
        // WorkManager.getInstance().beginWith(work).enqueue()
        // [END dispatch_job]
    }

    /**
     * Handle time allotted to BroadcastReceivers.
     */
    private fun handleNow() {
        Log.d(TAG, "Short lived task is done.")
    }

    private fun sendRegistrationToServer(token: String?) {
        // TODO: Implement this method to send token to your app server.
        Log.d(TAG, "sendRegistrationTokenToServer($token)")
    }

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */
    private fun sendNotification(
        messageBody: String,
        remoteMessage: RemoteMessage
    ) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        remoteMessage.data.let { msg ->
            val message = msg["message"]
            val jsonObject = JSONObject(message)
            intent.putExtra(BundleConstants.MESSAGE, jsonObject["message"].toString())
            this.sendBroadcast(Intent(LOCAL_BROADCAST))
        }

        val pendingIntent =
            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val channelId = getString(R.string.default_notification_channel_id)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(getString(R.string.app_name))
            .setContentText(messageBody)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Channel human readable title",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.canShowBadge()
            channel.setShowBadge(true)
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build())
        //  notificationManager.notify(System.currentTimeMillis().toInt() /* ID of notification */, notificationBuilder.build())
    }

    private fun segregateMessage(remoteMessage: RemoteMessage): String {
        /*{"title":"Kiranaa-Order notification","body":"Hi User,Your order is inprogress.","sound":"default","badge":"1"}*/
        remoteMessage.data.let { msg ->
            val message = msg["message"]
            //  val jsonObject = JsonParser().parse(message).asJsonObject
            println("GET______$msg")
            val jsonObject = JSONObject(message)
            return jsonObject["message"].toString()
        }
    }

    companion object {
        private const val TAG = "MyFirebaseMsgService"
    }
}
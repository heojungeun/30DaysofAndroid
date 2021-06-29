package com.example.firstfcm

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.firebase.messaging.ktx.remoteMessage
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager

class MyFirebaseMessagingService: FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d(TAG, "From: ${remoteMessage.from}")

        if(remoteMessage.data.isNotEmpty()){
            Log.d(TAG, "Message data payload: ${remoteMessage.data}")
            sendNotification(remoteMessage)
        }

        remoteMessage.notification?.let{
            Log.d(TAG, "Message Notification Body: ${it.body}")
        }
    }

    override fun onNewToken(token: String) {
        Log.d(TAG, "Refreshed token: $token")
    }

    private fun sendNotification(remoteMessage: RemoteMessage){

        createNotificationChannel()

        val type = remoteMessage.data["type"]?.let {
            NotificationType.valueOf(it)
        }
        val title = remoteMessage.data["title"]
        val message = remoteMessage.data["message"]

        type ?: return

        NotificationManagerCompat.from(this).notify(1, createNotification(type, title, message))
    }

    private fun createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT)
            channel.description = CHANNEL_DESCRIPTION

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)

        }
    }

    private fun createNotification(
        type: NotificationType,
        title: String?,
        message: String?
    ): Notification {

        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra("notificationType","${type.title} 타입")
            addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        }

        val pendingIntent = PendingIntent.getActivity(this, type.id, intent, PendingIntent.FLAG_ONE_SHOT)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_stat_name)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)
            //.build()

        when(type){
            NotificationType.NORMAL -> Unit
            NotificationType.EXPANDABLE -> {
                notificationBuilder.setStyle(NotificationCompat.BigTextStyle()
                        .bigText( "😀 😃 😄 😁 😆 😅 😂 🤣 🥲 ☺️ 😊 😇 " +
                                "🙂 🙃 😉 😌 😍 🥰 😘 😗 😙 😚 😋 😛 " +
                                "😝 😜 🤪 🤨 🧐 🤓 😎 🥸 🤩 🥳 😏 😒 " +
                                "😞 😔 😟 😕 🙁 ☹️ 😣 😖 😫 😩 🥺 😢 " +
                                "😭 😤 😠 😡 🤬 🤯 😳 🥵 🥶 😱 😨 😰 " +
                                "😥 😓 🤗 🤔 🤭 🤫 🤥 😶 😐 😑 😬 🙄 " +
                                "😯 😦 😧 😮 😲 🥱 😴 🤤 😪 😵 🤐 🥴 " +
                                "🤢 🤮 🤧 😷 🤒 🤕"
                        )
                )
            }
            NotificationType.CUSTOM -> {
                notificationBuilder.setStyle(NotificationCompat.DecoratedCustomViewStyle())
                        .setCustomContentView(
                                RemoteViews(
                                        packageName,
                                        R.layout.view_custom_notification
                                ).apply {
                                    setTextViewText(R.id.title,title)
                                    setTextViewText(R.id.message, message)
                                }
                        )
            }
        }
        return notificationBuilder.build()
    }

    companion object {
        private const val TAG = "MyFirebaseMsgService"
        private const val CHANNEL_NAME = "Emoji Party"
        private const val CHANNEL_DESCRIPTION = "Channel for Emoji Party"
        private const val CHANNEL_ID = "Channel Id"
    }
}
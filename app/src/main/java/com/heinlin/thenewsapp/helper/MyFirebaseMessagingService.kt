/*
package com.heinlin.thenewsapp.helper

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    private val channelId = "default_channel"
    private val notificationId = 0

    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        // Log the received message for debugging
        Log.d("FCM Message", "Message received: ${remoteMessage.data}")

        // Check if the message contains a notification payload
        remoteMessage.notification?.let {
            Log.d("FCM Notification", "Title: ${it.title}, Body: ${it.body}")
            // If the app is in the foreground, display the notification manually
            if (isAppInForeground()) {
                showNotification(it.title, it.body)
            }
        }

        // Handle data message if it's present
        if (remoteMessage.data.isNotEmpty()) {
            Log.d("FCM Data", "Data: ${remoteMessage.data}")
            // If you have custom data, process it here
            // For example, you might want to trigger an action based on this data
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        // Save or send the new token to your server
    }

    private fun isAppInForeground(): Boolean {
        // Implement logic to check if your app is in the foreground
        // You can use ActivityLifecycleCallbacks or check the app's current activity
        // This is just an example; adjust to your app's lifecycle management.
        return true // Assume it's in the foreground for simplicity
    }

    private fun showNotification(title: String?, body: String?) {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Create notification channel for Android 8.0 and above
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Default Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        val notification: Notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle(title)
            .setContentText(body)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .build()

        notificationManager.notify(notificationId, notification)
    }
}
*/

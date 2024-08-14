package com.example.rajpatel_assignment3

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class CarDetailActivity : AppCompatActivity() {

    companion object {
        private const val CHANNEL_ID = "CAR_DETAILS_CHANNEL"
        private const val NOTIFICATION_ID = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car_detail)

        // Retrieve the data sent from the MainActivity
        val carDetails = intent.getStringExtra("CAR_DETAILS") ?: "No details provided"

        // Find the TextView and set the retrieved data
        val textViewCarDetails = findViewById<TextView>(R.id.textViewCarDetails)
        textViewCarDetails.text = carDetails

        // Find the Go Back button and set its click listener
        val buttonGoBack = findViewById<Button>(R.id.buttonGoBack)
        buttonGoBack.setOnClickListener {
            finish() // Closes the current activity and returns to the previous one
        }

        // Check for notification permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (NotificationManagerCompat.from(this).areNotificationsEnabled()) {
                // Show notification
                showNotification(carDetails)
            } else {
                // Request permission to show notifications
                requestNotificationPermission()
            }
        } else {
            // Show notification for older versions
            showNotification(carDetails)
        }
    }

    private fun requestNotificationPermission() {
        val intent = Intent(android.provider.Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
            putExtra(android.provider.Settings.EXTRA_APP_PACKAGE, packageName)
        }
        startActivity(intent)
        Toast.makeText(this, "Please enable notifications for this app", Toast.LENGTH_LONG).show()
    }

    private fun showNotification(carDetails: String) {
        // Create a notification manager
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Create a notification channel if needed (required for Android 8.0 and higher)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelName = "Car Details Channel"
            val channelDescription = "Channel for car detail notifications"
            val channel = NotificationChannel(CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_DEFAULT).apply {
                description = channelDescription
            }
            notificationManager.createNotificationChannel(channel)
        }

        // Create a notification
        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.baseline_notifications_24) // Replace with your notification icon
            .setContentTitle("Car Details")
            .setContentText(carDetails)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        // Show the notification
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
    }
}

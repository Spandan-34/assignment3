package com.example.simplecontactmanager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class DisplayContactActivity : AppCompatActivity() {

    private val CHANNEL_ID = "contact_notification_channel"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_contact)

        val textViewName = findViewById<TextView>(R.id.textViewName)
        val textViewPhone = findViewById<TextView>(R.id.textViewPhone)
        val textViewEmail = findViewById<TextView>(R.id.textViewEmail)

        val name = intent.getStringExtra("EXTRA_NAME")
        val phone = intent.getStringExtra("EXTRA_PHONE")
        val email = intent.getStringExtra("EXTRA_EMAIL")

        textViewName.text = "Name: $name"
        textViewPhone.text = "Phone: $phone"
        textViewEmail.text = "Email: $email"

        createNotificationChannel()
        sendNotification(name, phone, email)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ExampleFragment())
                .commit()
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Contact Notification"
            val descriptionText = "Notification for new contact"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun sendNotification(name: String?, phone: String?, email: String?) {
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("New Contact Added")
            .setContentText("Name: $name, Phone: $phone, Email: $email")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(this)) {
            notify(0, builder.build())
        }
    }
}

package coder.bekhzod

import android.Manifest
import android.app.Activity
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.IBinder
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class Functions @Inject constructor(
    private val ctx:Context,
    private val notificationManager:NotificationManagerCompat,
    private val notificationBuilder:NotificationCompat.Builder
) :Service(){

    fun onStart(){
        if (ActivityCompat.checkSelfPermission(
                ctx,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED){
            notificationManager.notify(1,notificationBuilder.build())
        }
    }
    fun onStop(){
        notificationManager.cancel(1)
    }

    override fun onBind(intent: Intent?): IBinder? = null

}
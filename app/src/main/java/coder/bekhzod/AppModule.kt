package coder.bekhzod

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class,SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideFunctions(@ApplicationContext ctx: Context) = Functions(
        ctx,
        provideNotificationManager(ctx),
        provideNotificationBuilder(ctx)
    )

    @Provides
    @Singleton
    fun provideContext(@ApplicationContext ctx: Context) = ctx

    @Singleton
    @Provides
    fun provideNotificationBuilder(@ApplicationContext ctx: Context): NotificationCompat.Builder {

        val intent = Intent(ctx, Receiver::class.java).apply {
            putExtra("MESSAGE","THAT IS WORKING")
        }
        val stopMessageIntent = Intent(ctx,Receiver::class.java).apply {
            putExtra("STOP","STOP WORKING")
        }
        val contentIntent = Intent(ctx,MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        val flag =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                PendingIntent.FLAG_IMMUTABLE
            else
                0
        val actionPendingIntent = PendingIntent.getBroadcast(ctx,0,intent,flag)
        val contentIntentPendingIntent = PendingIntent.getActivity(ctx,2,contentIntent, PendingIntent.FLAG_IMMUTABLE)
        val stopPendingIntent = PendingIntent.getBroadcast(ctx,1,stopMessageIntent,flag)

        return NotificationCompat.Builder(ctx,"Main Channel ID")
            .setContentTitle("Hello")
            .setContentText("I am Behzod")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .addAction(0,"Action",actionPendingIntent)
            .setContentIntent(contentIntentPendingIntent)
            .addAction(0,"STOP",stopPendingIntent)
    }

    @Singleton
    @Provides
    fun provideNotificationManager(@ApplicationContext ctx: Context):NotificationManagerCompat{

        val notificationManager = NotificationManagerCompat.from(ctx)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(
                "Main Channel ID",
                "Main Channel",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }
        return notificationManager
    }
}

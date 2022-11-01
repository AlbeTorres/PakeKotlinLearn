package com.example.pakekotlinlearn.Notificaciones

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.lifecycle.lifecycleScope
import com.example.pakekotlinlearn.Aplication
import com.example.pakekotlinlearn.Database.Repository
import com.example.pakekotlinlearn.MainActivity
import com.example.pakekotlinlearn.PoJO.Cliente
import com.example.pakekotlinlearn.R
import com.example.pakekotlinlearn.pref.Prefs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.random.Random

class AlarmReciver: BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        val name = intent.getStringExtra(Constanst.C_NAME)
        val id = intent.getIntExtra(Constanst.idNoti,0)

        when(intent.action){
            Constanst.ACTION_SET_ALARM -> buildNoti(context,"Pake","Es tiempo de recogerle el disco a $name",id)

        }


    }

    fun buildNoti(context: Context, title:String, message:String, idNoti: Int){

        val intent= Intent(context, MainActivity::class.java)
        intent.flags= Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP

        val pendingIntent = PendingIntent.getActivity(context,0,intent, PendingIntent.FLAG_ONE_SHOT)




        val id = "EntregasF"
        val notificationManager= context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){

            val notificationChannel = NotificationChannel(id,"nuevo", NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.description= "Notification FCM"
            notificationChannel.setShowBadge(true)
            notificationChannel.enableVibration(true)
            notificationChannel.vibrationPattern = longArrayOf(100, 250, 100, 500)
            notificationManager.createNotificationChannel(notificationChannel)

        }


        val  builder = NotificationCompat.Builder(context,id)
        builder.setAutoCancel(true)
                .setWhen(System.currentTimeMillis())
                .setContentTitle(title)
                .setTicker("¡A trabajar!")
                .setSmallIcon(R.drawable.disco)
                .setContentText(message)
                .setContentIntent(pendingIntent)
                .setContentInfo("nuevo")
                .setVibrate(longArrayOf(100, 250, 100, 500))
                .setLargeIcon(BitmapFactory.decodeResource(context.resources,R.drawable.user))



        notificationManager.notify(idNoti,builder.build())






    }


}



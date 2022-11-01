package com.example.pakekotlinlearn.Notificaciones

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build

class AlarmService(private val context: Context) {


    private val alarmManager: AlarmManager? = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager?

    fun getExactTime(timeInMillis:Long, cname: String, requestCode: Int){
        setAlarm(

                timeInMillis,getPendingIntent(getIntent().apply {
            action= Constanst.ACTION_SET_ALARM
                putExtra(Constanst.EXTRA_EXACT_ALARM_TIME,timeInMillis)
                putExtra(Constanst.C_NAME,cname)
                putExtra(Constanst.idNoti,requestCode)

        },requestCode))


    }

    private fun setAlarm(timeInMillis: Long,pendingIntent: PendingIntent){

        alarmManager?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,timeInMillis,pendingIntent)

            }else{

                alarmManager.setExact(AlarmManager.RTC_WAKEUP,timeInMillis,pendingIntent)

            }
        }
    }

    private fun getIntent(): Intent = Intent(context,AlarmReciver::class.java)



    private fun getPendingIntent(intent: Intent, requestCode: Int): PendingIntent {

        val pendingIntent = PendingIntent.getBroadcast(context,requestCode,intent, PendingIntent.FLAG_UPDATE_CURRENT)


        return pendingIntent

    }

    fun cancelarAlarma(requestCode: Int){

        alarmManager?.cancel(getPendingIntent(getIntent(),requestCode))
    }
}
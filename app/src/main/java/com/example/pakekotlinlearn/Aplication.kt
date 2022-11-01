package com.example.pakekotlinlearn

import android.app.Application
import androidx.work.WorkManager
import com.example.pakekotlinlearn.Notificaciones.AlarmService
import com.example.pakekotlinlearn.pref.Prefs
import java.util.*

class Aplication: Application() {

    companion object{

        lateinit var prefs: Prefs



    }

    override fun onCreate() {
        super.onCreate()

        prefs= Prefs(applicationContext)





    }
}
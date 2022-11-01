package com.example.pakekotlinlearn.pref

import android.content.Context
import com.example.pakekotlinlearn.PoJO.Cliente

class Prefs(val context: Context) {
    private val SHARED_NAME = "PakePrefs"
    private val monto = "monto"
    private val tiempo = "tiempo"



    private val storange = context.getSharedPreferences(SHARED_NAME,0)

    fun saveMonto(money:Int)= storange.edit().putInt(monto, money ).apply()

    fun getMonto()= storange.getInt(monto, 50)

    fun saveTiempo(tie:Int)= storange.edit().putInt(tiempo, tie ).apply()

    fun getTiempo()= storange.getInt(tiempo, 3)
}
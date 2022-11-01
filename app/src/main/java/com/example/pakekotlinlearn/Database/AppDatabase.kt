package com.example.pakekotlinlearn.Database

import android.content.Context
import androidx.room.*
import com.example.pakekotlinlearn.PoJO.Cliente
import com.example.pakekotlinlearn.PoJO.Disco
import com.example.pakekotlinlearn.PoJO.Entrega


@Database(entities = [Cliente::class, Disco::class, Entrega::class], version = 1)
@TypeConverters(CalendarParse::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun AllDao(): AllDao

    companion object{

        private var INSTANCE: AppDatabase? = null

        fun getInstance(contex: Context):AppDatabase{
            if (INSTANCE==null){
                INSTANCE= Room.databaseBuilder(contex.applicationContext,
                        AppDatabase::class.java,"pakedatabase")
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()

            }

            return INSTANCE!!

        }

    }


}
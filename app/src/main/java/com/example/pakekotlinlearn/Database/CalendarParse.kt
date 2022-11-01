package com.example.pakekotlinlearn.Database

import androidx.room.TypeConverter
import java.util.*

 class CalendarParse {


    @TypeConverter
    fun toCalendar(value:Long?): Calendar? = if (value== null)null else Calendar.getInstance().apply { timeInMillis=value }

    @TypeConverter
    fun fromCalendar(c: Calendar?):Long?=c?.time?.time


}
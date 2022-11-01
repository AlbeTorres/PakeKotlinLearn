package com.example.pakekotlinlearn.PoJO

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Disco(@PrimaryKey(autoGenerate = true)
                 var id: Int = 0,
                 @ColumnInfo var nombre: String,
                 @ColumnInfo var vida: Int,
                 @ColumnInfo var fecha: Calendar,
                 @ColumnInfo var entregas: Int,
                 @ColumnInfo var estado: Int){

    override fun toString(): String {
        return nombre
    }
}
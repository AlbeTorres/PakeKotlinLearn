package com.example.pakekotlinlearn.PoJO

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Cliente(@PrimaryKey(autoGenerate = true)
                   var id: Int = 0,
                   @ColumnInfo var nombre: String,
                   @ColumnInfo var telefono: String,
                   @ColumnInfo var fecha: Calendar,
                   @ColumnInfo var dia: Int,
                   @ColumnInfo var ingreso: Int,
                   @ColumnInfo  var estado: Int)
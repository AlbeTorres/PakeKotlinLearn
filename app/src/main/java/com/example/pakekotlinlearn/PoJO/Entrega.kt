package com.example.pakekotlinlearn.PoJO

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Entrega(@PrimaryKey(autoGenerate = true)
                   var id: Int = 0,
                   @ForeignKey(entity = Cliente::class, parentColumns = ["id"],childColumns =["id_cliente"], onDelete = ForeignKey.CASCADE )
                   var id_cliente: Int,
                   @ForeignKey(entity = Disco::class, parentColumns = ["id"],childColumns =["id_disco"], onDelete = ForeignKey.CASCADE )
                   var id_disco: Int,
                   @ColumnInfo var nombre_cliente: String,
                   @ColumnInfo var nombre_disco: String,
                   @ColumnInfo var fecha: Calendar,
                   @ColumnInfo var hora_recogida: Calendar?,
                   @ColumnInfo var estado: Int,
                   @ColumnInfo var ingreso: Int)
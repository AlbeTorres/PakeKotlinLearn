package com.example.pakekotlinlearn.Notificaciones

import android.content.Context
import com.example.pakekotlinlearn.Database.Repository
import com.example.pakekotlinlearn.PoJO.Cliente
import com.example.pakekotlinlearn.PoJO.Entrega
import com.example.pakekotlinlearn.Validaciones.setCalendar
import com.example.pakekotlinlearn.Validaciones.testClienteEntrega
import com.example.pakekotlinlearn.pref.Prefs
import java.util.*
import java.util.concurrent.TimeUnit


fun freeAll(context: Context){
    val repository = Repository(context)


    var auxentre = ArrayList<Entrega>()
    repository.cargarEntregasFree(auxentre)

    var aux = ArrayList<Cliente>()
    repository.cargarClientesStop(aux)

    for (cliente in aux){

        if(!testClienteEntrega(cliente.id,auxentre)){
            cliente.estado= 1
            repository.modificarCliente(cliente)

        }
    }
}

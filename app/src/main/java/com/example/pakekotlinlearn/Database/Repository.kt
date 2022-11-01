package com.example.pakekotlinlearn.Database

import android.content.Context
import com.example.pakekotlinlearn.PoJO.Cliente
import com.example.pakekotlinlearn.PoJO.Disco
import com.example.pakekotlinlearn.PoJO.Entrega



class Repository( val context: Context) {

    val dao = AppDatabase.getInstance(context).AllDao()


    fun cargarClientes(clientes: ArrayList<Cliente>){
        clientes.clear()
        clientes.addAll(dao.getClientes())

    }

    fun cargarClientesFree(clientes: ArrayList<Cliente>){
        clientes.clear()
        clientes.addAll(dao.getClientesFree())

    }

    fun cargarClientesStop(clientes: ArrayList<Cliente>){
        clientes.clear()
        clientes.addAll(dao.getClientesStop())

    }

    fun insertarCliente(cliente: Cliente)= dao.insertarCliente(cliente)

    fun deleteCliente(id: Int)=  dao.deleteCliente(id)

    fun modificarCliente(cliente: Cliente)= dao.updateCliente(cliente)

    fun getClienteById(id: Int)= dao.getClienteById(id)




    fun cargarDiscos(discos: ArrayList<Disco>){
        discos.clear()
        discos.addAll(dao.getDiscos())
    }

    fun cargarNombreDisco(nomDisco: ArrayList<String>){

        nomDisco.clear()

        var aux :ArrayList<Disco> = ArrayList(dao.getDiscos())

        for (i in aux)
            nomDisco.add(i.nombre)

        if(nomDisco.isEmpty()){nomDisco.add("añade un disco")}
    }

    fun insertarDisco(disco: Disco) = dao.insertarDisco(disco)

    fun deleteDisco(id:Int) = dao.deleteDisco(id)

    fun modificarDisco(disco: Disco)= dao.updateDisco(disco)

    fun getDiscoById(id: Int)= dao.getDiscoById(id)

    fun getDiscoFree(discos: ArrayList<Disco>){
        discos.clear()
        discos.addAll(dao.getDiscoFree())
    }




    fun cargarEntregas(entregas:ArrayList<Entrega>){
        entregas.clear()
        entregas.addAll(dao.getEntregas())
    }
    fun cargarEntregasFree(entregas:ArrayList<Entrega>){
        entregas.clear()
        entregas.addAll(dao.getEntregasFree())
    }

    fun cargarEntregasStop(entregas:ArrayList<Entrega>){
        entregas.clear()
        entregas.addAll(dao.getEntregasStop())
    }

    fun insertarEntrega(entrega: Entrega)= dao.insertarEntrega(entrega)

    fun deleteEntrega(id: Int)= dao.deleteEntrega(id)

    fun getEntregaById(id: Int)= dao.getEntregaById(id)

    fun modificarEntrega(entrega: Entrega)= dao.updateEntrega(entrega)

}
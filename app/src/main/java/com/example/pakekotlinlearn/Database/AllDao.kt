package com.example.pakekotlinlearn.Database

import androidx.room.*
import androidx.room.Dao
import com.example.pakekotlinlearn.PoJO.Cliente
import com.example.pakekotlinlearn.PoJO.Disco
import com.example.pakekotlinlearn.PoJO.Entrega

@Dao
interface AllDao {

    //Dao Cliente

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertarCliente(cliente: Cliente)

    @Update
    fun updateCliente(cliente: Cliente)

    @Query("Delete FROM Cliente Where id = :id")
    fun deleteCliente(id: Int)

    @Query("SELECT * FROM Cliente")
    fun getClientes():List<Cliente>

    @Query("SELECT * FROM Cliente Where id=:id")
    fun getClienteById(id:Int):Cliente

    @Query("SELECT * FROM Cliente Where estado = 1")
    fun getClientesFree():List<Cliente>

    @Query("SELECT * FROM Cliente Where estado = 0")
    fun getClientesStop():List<Cliente>


    //Dao Disco

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertarDisco( disco : Disco)

    @Update
    fun updateDisco(disco : Disco)

    @Query("Delete FROM Disco Where id = :id")
    fun deleteDisco(id: Int)

    @Query("SELECT * FROM Disco")
    fun getDiscos():List<Disco>

    @Query("SELECT * FROM Disco Where id=:id")
    fun getDiscoById(id:Int):Disco

    @Query("SELECT * FROM Disco Where estado = 1")
    fun getDiscoFree():List<Disco>

    //Dao Entregas

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertarEntrega( entrega: Entrega)

    @Update
    fun updateEntrega(entrega: Entrega)

    @Query("Delete FROM Entrega Where id = :id")
    fun deleteEntrega(id: Int)

    @Query("SELECT * FROM Entrega")
    fun getEntregas():List<Entrega>

    @Query("SELECT * FROM Entrega Where id=:id")
    fun getEntregaById(id:Int):Entrega

    @Query("SELECT * FROM Entrega Where estado = 1")
    fun getEntregasFree():List<Entrega>


    @Query("SELECT * FROM Entrega Where estado = 0")
    fun getEntregasStop():List<Entrega>


}
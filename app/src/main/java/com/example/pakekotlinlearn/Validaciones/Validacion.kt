package com.example.pakekotlinlearn.Validaciones

import android.content.Context
import com.example.pakekotlinlearn.Notificaciones.AlarmService
import com.example.pakekotlinlearn.PoJO.Cliente
import com.example.pakekotlinlearn.PoJO.DiaSemana
import com.example.pakekotlinlearn.PoJO.Disco
import com.example.pakekotlinlearn.PoJO.Entrega
import java.util.*
import java.util.concurrent.atomic.AtomicInteger
import java.util.regex.Pattern
import kotlin.collections.ArrayList

fun validarNombre(nombre: String):String?{

    val patron = Pattern.compile("^[a-zA-Z 0-9]+$")


    if (nombre.length != 0){

        if (!patron.matcher(nombre).matches()){

            return "Contiene caracteres inválidos"
        }

        if (nombre.length >10){

            return "Solo se admiten 10 caracteres"
        }

        return null


    }

    return "Vacío"

}


fun validarNombreCliente(nombre: String):String?{

    val patron = Pattern.compile("^[a-zA-Z ]+$")


    if (nombre.length != 0){

        if (!patron.matcher(nombre).matches()){

            return "Contiene caracteres inválidos"
        }

        if (nombre.length >14){

            return "Solo se admiten 10 caracteres"
        }

        return null


    }

    return "Vacío"

}

fun validarTelefono(nombre: String):String?{

    val patron = Pattern.compile("^[0-9]+$")


    if (nombre.length != 0){

        if (!patron.matcher(nombre).matches()){

            return "Contiene caracteres inválidos"
        }

        if (nombre.length >8){

            return "Solo se admiten 8 caracteres"
        }

        return null


    }

    return "Vacío"

}

fun validarMonto(nombre: String):String?{

    val patron = Pattern.compile("^[0-9]+$")


    if (nombre.length != 0){

        if (!patron.matcher(nombre).matches()){

            return "Contiene caracteres inválidos"
        }

        if (nombre.length >4){

            return "Solo se admiten 4 caracteres"
        }

        return null


    }

    return "Vacío"

}

fun validarHora(nombre: String):String?{

    val patron = Pattern.compile("^[0-9]+$")


    if (nombre.length != 0){

        if ( nombre.toInt() > 24){ return  "Solo se admiten hasta 24 horas"}

        if (!patron.matcher(nombre).matches()){

            return "Contiene caracteres inválidos"
        }

        if (nombre.length >4){

            return "Solo se admiten 4 caracteres"
        }

        return null


    }

    return "Vacío"

}



    fun setDia(dia:Int):String{

        when(dia){
            1-> return "Sábado"
            2-> return "Domingo"
            3-> return "Lunes"
            4-> return "Martes"
            5-> return "Miécoles"
            6-> return "Jueves"
            7-> return "Viernes"
        }
        return "ok"

    }

fun CargarDia(list: ArrayList<DiaSemana>){

    list.add(DiaSemana("S",1))
    list.add(DiaSemana("D",2))
    list.add(DiaSemana("L",3))
    list.add(DiaSemana("M",4))
    list.add(DiaSemana("W",5))
    list.add(DiaSemana("J",6))
    list.add(DiaSemana("V",7))

}

object RandomUtil {
    private val seed = AtomicInteger()

    fun getRandomUtil() = seed.getAndIncrement() + System.currentTimeMillis().toInt()

}

fun activarAlarma(cliente: Cliente, c:Calendar,context:Context , disco:Disco){

    val alarm = AlarmService(context)

    alarm.getExactTime(c.timeInMillis,cliente.nombre, disco.id)
}

fun cancelarAlarma(disco: Disco, context: Context){
    val alarm = AlarmService(context)

    alarm.cancelarAlarma(disco.id)


}



fun setCalendar(dia:String):Int{

    when(dia){
        "Sábado"-> return Calendar.SATURDAY
        "Domingo"-> return Calendar.SUNDAY
        "Lunes"-> return Calendar.MONDAY
        "Martes"-> return Calendar.TUESDAY
        "Miécoles"-> return Calendar.WEDNESDAY
        "Jueves"-> return Calendar.THURSDAY
        "Viernes"-> return Calendar.FRIDAY
    }

    return 0
}

fun testClienteEntrega(id:Int, enproceso:ArrayList<Entrega>):Boolean{

    for (entrega in enproceso){

        if (entrega.id_cliente == id){

            return true
        }
    }


    return false


}







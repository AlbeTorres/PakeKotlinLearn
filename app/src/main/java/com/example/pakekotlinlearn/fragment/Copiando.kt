package com.example.pakekotlinlearn.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pakekotlinlearn.Adapters.RecyclerCopAdapter
import com.example.pakekotlinlearn.Database.Repository
import com.example.pakekotlinlearn.PoJO.Cliente
import com.example.pakekotlinlearn.PoJO.Entrega
import com.example.pakekotlinlearn.R
import com.example.pakekotlinlearn.Validaciones.cancelarAlarma
import com.example.pakekotlinlearn.databinding.FragmentCopiandoBinding
import com.example.pakekotlinlearn.fragment.crud_disco.Discos
import java.util.*
import kotlin.collections.ArrayList


class Copiando : Fragment() {
    private var _binding: FragmentCopiandoBinding?= null
    private val binding get() = _binding!!
    private lateinit var repository: Repository
    private  var entregasCopiando = ArrayList<Entrega>()
    private val fragment = Noclientes()
    var monto = 0




    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        _binding = FragmentCopiandoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        repository = Repository(requireActivity().baseContext)
        repository.cargarEntregasFree(entregasCopiando)

        cargarMonto()


        binding.recyclerViewcopiando.layoutManager = LinearLayoutManager(requireActivity().baseContext)
        val adapter = RecyclerCopAdapter(entregasCopiando,{listener(it)},{listener2(it)})

        setFragmentResultListener("requestkeyCopiando"){
            requestkey, bundle->
            val result = bundle.getString("bundleKeyCopiando")

            if (result== "okcopy"){

                repository.cargarEntregasFree(entregasCopiando)
                binding.recyclerViewcopiando.adapter = adapter

                if(!entregasCopiando.isEmpty()){
                    childFragmentManager.beginTransaction().hide(fragment).commit()

                }else{

                    childFragmentManager.beginTransaction().replace(R.id.framecopiando, fragment).commit()
                    childFragmentManager.beginTransaction().show(fragment).commit()
                }

            }
        }

        if(!entregasCopiando.isEmpty()){

            binding.recyclerViewcopiando.adapter = adapter


        }else{

           childFragmentManager.beginTransaction().replace(R.id.framecopiando, fragment).commit()
        }


    }

    fun listener(entrega: Entrega){

        val c = Calendar.getInstance()

        monto = monto + entrega.ingreso

        binding.textView4.text= monto.toString()

        entrega.estado= 0
        entrega.hora_recogida= c
        repository.modificarEntrega(entrega)
        entregasCopiando.remove(entrega)
        binding.recyclerViewcopiando.adapter!!.notifyDataSetChanged()

        val disco = repository.getDiscoById(entrega.id_disco)
        disco.estado= 1
        disco.entregas= disco.entregas+1
        repository.modificarDisco(disco)
        cancelarAlarma(disco,requireContext())
        setFragmentResult("requestkeypendDisco", bundleOf("bundleKeypendDisco" to "okpendDisco"))



        if (entregasCopiando.isEmpty()){
            childFragmentManager.beginTransaction().replace(R.id.framecopiando, fragment).commit()
            childFragmentManager.beginTransaction().show(fragment).commit()



        }


    }

    fun listener2(entrega: Entrega){

        var hora = entrega.hora_recogida?.get(Calendar.HOUR)
        var min = entrega.hora_recogida?.get(Calendar.MINUTE)

        val dialog = AlertDialog.Builder(requireContext())
                .setTitle(Html.fromHtml("<font color='#0000ff'>Hora de Recogida</font>"))
                .setMessage(Html.fromHtml("<font color='#000000'> Recoger Disco a las $hora : $min</font>"))
                .setCancelable(true)
                .setNegativeButton("Eliminar Entrega"){ View,_->

                    val cliente = repository.getClienteById(entrega.id_cliente)
                    cliente.estado=1
                    repository.modificarCliente(cliente)

                    val disco = repository.getDiscoById(entrega.id_disco)
                    disco.estado= 1
                    repository.modificarDisco(disco)

                    cancelarAlarma(disco,requireContext())

                    repository.deleteEntrega(entrega.id)
                    entregasCopiando.remove(entrega)
                    binding.recyclerViewcopiando.adapter!!.notifyDataSetChanged()
                    setFragmentResult("requestkeypend", bundleOf("bundleKeypend" to "okpend"))

                    if (entregasCopiando.isEmpty()){
                        childFragmentManager.beginTransaction().replace(R.id.framecopiando, fragment).commit()
                        childFragmentManager.beginTransaction().show(fragment).commit()

                    }

                }

               .create()

        dialog.window?.setBackgroundDrawable(resources.getDrawable(R.drawable.curvo15white,null))
        dialog.show()


    }

    fun cargarMonto(){
        val cale = Calendar.getInstance()

        var entregasStop = ArrayList<Entrega>()
        repository.cargarEntregasStop(entregasStop)

        if (entregasStop.isNotEmpty()){

        for ( entregait in entregasStop){

            if (entregait.hora_recogida!!.get(Calendar.DAY_OF_YEAR) == cale.get(Calendar.DAY_OF_YEAR) ){
                        monto= monto + entregait.ingreso
            }
        }}

        binding.textView4.text= monto.toString()

    }






}
package com.example.pakekotlinlearn.fragment.crud_usuario

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.get
import androidx.fragment.app.setFragmentResult
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pakekotlinlearn.Adapters.RecyclerSemanaAdapter
import com.example.pakekotlinlearn.Aplication.Companion.prefs
import com.example.pakekotlinlearn.Database.Repository
import com.example.pakekotlinlearn.PoJO.Cliente
import com.example.pakekotlinlearn.PoJO.DiaSemana
import com.example.pakekotlinlearn.R
import com.example.pakekotlinlearn.Validaciones.CargarDia
import com.example.pakekotlinlearn.Validaciones.validarMonto
import com.example.pakekotlinlearn.Validaciones.validarNombreCliente
import com.example.pakekotlinlearn.Validaciones.validarTelefono
import com.example.pakekotlinlearn.databinding.FragmentCrearClienteBinding
import com.example.pakekotlinlearn.fragment.crud_disco.Discos
import java.util.*
import kotlin.collections.ArrayList


class CrearCliente : Fragment() {
    private var _binding :FragmentCrearClienteBinding? = null
    private val binding get() = _binding!!
    private var dialist= ArrayList<DiaSemana>()
    private lateinit var repository: Repository


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        _binding= FragmentCrearClienteBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        CargarDia(dialist)
        val c = Calendar.getInstance()
        var dia = 0
        repository= Repository(requireContext())

        binding.textmontocliente.setText(prefs.getMonto().toString())


        binding.recyclerdiasemana.layoutManager = LinearLayoutManager(requireActivity().baseContext,LinearLayoutManager.HORIZONTAL,false)
        binding.recyclerdiasemana.adapter=RecyclerSemanaAdapter(dialist){

            Toast.makeText(requireContext(),it.dia,Toast.LENGTH_LONG).show()

            dia= it.valor
        }



        binding.buttonCrearCliente.setOnClickListener{

            val nombre = binding.textnombrecliente.text.toString()
            val telefono = binding.texttelefonocliente.text.toString()
            val monto = binding.textmontocliente.text.toString()


            if(validarNombreCliente(nombre)!= null ||validarTelefono(telefono)!= null || validarMonto(monto) != null || dia == 0 ){

                if(validarNombreCliente(nombre)!= null){ binding.layouttextclientenombre.error = validarNombreCliente(nombre) } else {binding.layouttextclientenombre.error = null}

                if (validarTelefono(telefono)!= null){ binding.layouttextclientetelefono.error = validarTelefono(telefono) } else{ binding.layouttextclientetelefono.error = null }

                if (validarMonto(monto) != null){ binding.layouttextclientemonto.error = validarMonto(monto)} else { binding.layouttextclientemonto.error = null }

                if (dia == 0){ Toast.makeText(requireContext(),"Selecciona un día de entrega",Toast.LENGTH_LONG).show() }

                 }else{

                repository.insertarCliente(Cliente(0,nombre,telefono,c,dia,monto.toInt(),1))
                setFragmentResult("requestkeyCliente", bundleOf("bundleKeyCliente" to "okcliente"))

                requireActivity().supportFragmentManager.beginTransaction().replace(R.id.frame,Clientes()).commit()

            } }





        binding.imageButtonCancelar.setOnClickListener{

            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.frame,Clientes()).commit()

        }






    }
}
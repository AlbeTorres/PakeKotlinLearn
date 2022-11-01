package com.example.pakekotlinlearn.fragment.crud_usuario

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pakekotlinlearn.Adapters.RecyclerSemanaAdapter
import com.example.pakekotlinlearn.Database.Repository
import com.example.pakekotlinlearn.PoJO.Cliente
import com.example.pakekotlinlearn.PoJO.DiaSemana
import com.example.pakekotlinlearn.R
import com.example.pakekotlinlearn.Validaciones.CargarDia
import com.example.pakekotlinlearn.Validaciones.validarMonto
import com.example.pakekotlinlearn.Validaciones.validarNombreCliente
import com.example.pakekotlinlearn.Validaciones.validarTelefono
import com.example.pakekotlinlearn.databinding.FragmentModificarUsuarioBinding



class ModificarUsuario : Fragment() {
    private var _binding: FragmentModificarUsuarioBinding? = null
    val binding get() = _binding!!
    private var dialist= ArrayList<DiaSemana>()
    private lateinit var repository: Repository



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        _binding= FragmentModificarUsuarioBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        repository= Repository(requireActivity().baseContext)

        lateinit var usuario: Cliente
        var id =0


        val nombre = binding.textnombreclienteM
        val telfono = binding.texttelefonoclienteM
        val monto = binding.textmontoclienteM

        setFragmentResultListener("requestClientemodikey"){
                requestkey, bundle->
            val result = bundle.getString("bundleKeyClienteM")

            if (result== "ok"){

                id = bundle.getInt("id")
                usuario= repository.getClienteById(id)
                nombre.setText(usuario.nombre)
                telfono.setText(usuario.telefono)
                monto.setText(usuario.ingreso.toString())
                usuario.dia= 0

            }
        }

        CargarDia(dialist)
        binding.recyclerdiasemanaM.layoutManager = LinearLayoutManager(requireActivity().baseContext, LinearLayoutManager.HORIZONTAL,false)
        binding.recyclerdiasemanaM.adapter= RecyclerSemanaAdapter(dialist){

            Toast.makeText(requireContext(),it.dia, Toast.LENGTH_LONG).show()

            usuario.dia = it.valor
        }





        binding.buttonModificarCliente.setOnClickListener {

            val nombre1 = binding.textnombreclienteM.text.toString()
            val telefono1 = binding.texttelefonoclienteM.text.toString()
            val monto1 = binding.textmontoclienteM.text.toString()


            if(validarNombreCliente(nombre1) != null || validarTelefono(telefono1) != null || validarMonto(monto1) != null || usuario.dia == 0 ){

                if(validarNombreCliente(nombre1) != null){ binding.layouttextclientenombreM.error = validarNombreCliente(nombre1) } else {binding.layouttextclientenombreM.error = null}

                if (validarTelefono(telefono1) != null){ binding.layouttextclientetelefonoM.error = validarTelefono(telefono1) } else{ binding.layouttextclientetelefonoM.error = null }

                if (validarMonto(monto1) != null){ binding.layouttextclientemontoM.error = validarMonto(monto1) } else { binding.layouttextclientemontoM.error = null }

                if (usuario.dia == 0){ Toast.makeText(requireContext(),"Selecciona un día de entrega",Toast.LENGTH_LONG).show() }

            }else{

                usuario.nombre = nombre1
                usuario.telefono = telefono1
                usuario.ingreso = monto1.toInt()

                repository.modificarCliente(usuario)

                setFragmentResult("requestkeyCliente", bundleOf("bundleKeyCliente" to "okcliente"))

                requireActivity().supportFragmentManager.beginTransaction().replace(R.id.frame,Clientes()).commit()

            }
        }


        binding.imageButtonCancelarM.setOnClickListener {

            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.frame,Clientes()).commit()
        }



        binding.imageViewMcliente.setOnClickListener {

            val dialog = AlertDialog.Builder(requireContext())
                .setTitle("Eliminar Cliente")
                .setMessage("Está seguro de eliminar el Cliente")
                .setCancelable(false)
                .setNegativeButton("Cancelar"){view,_->
                    view.dismiss()
                }
                .setPositiveButton("Aceptar"){view,_->

                    repository.deleteCliente(id)

                    setFragmentResult("requestkeyCliente", bundleOf("bundleKeyCliente" to "okcliente"))
                    requireActivity().supportFragmentManager.beginTransaction().replace(R.id.frame,Clientes()).commit()

                }.create()

            dialog.window?.setBackgroundDrawable(resources.getDrawable(R.drawable.curvo15white,null))
            dialog.show()




        }




    }
}
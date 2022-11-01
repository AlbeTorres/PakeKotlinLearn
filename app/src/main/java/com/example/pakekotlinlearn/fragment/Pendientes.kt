package com.example.pakekotlinlearn.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pakekotlinlearn.Adapters.RecyclerPendAdapter
import com.example.pakekotlinlearn.Aplication.Companion.prefs
import com.example.pakekotlinlearn.Database.Repository
import com.example.pakekotlinlearn.PoJO.Cliente
import com.example.pakekotlinlearn.PoJO.Disco
import com.example.pakekotlinlearn.PoJO.Entrega
import com.example.pakekotlinlearn.R
import com.example.pakekotlinlearn.Validaciones.activarAlarma
import com.example.pakekotlinlearn.Validaciones.cancelarAlarma
import com.example.pakekotlinlearn.databinding.FragmentPendientesBinding
import com.google.android.material.snackbar.Snackbar
import java.util.*
import kotlin.collections.ArrayList


class Pendientes : Fragment() {
    private var _binding: FragmentPendientesBinding? = null
    private val binding get()= _binding!!

    private  var nomDiscos= ArrayList<Disco>()
    private  var clientesPendientes= ArrayList<Cliente>()
    private lateinit var repository: Repository
    private lateinit var disco:Disco
    private lateinit var discoaux:Disco
    private lateinit var arrayAdapter: ArrayAdapter<Disco>
    private var pos = 0
    private var posaux = 0
    private var position = 0
    private val fragment = Noclientes()






    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding= FragmentPendientesBinding.inflate(inflater,container,false)
        return binding.root
    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        repository= Repository(requireActivity().baseContext)
        repository.getDiscoFree(nomDiscos)



        arrayAdapter = ArrayAdapter(requireActivity().baseContext,android.R.layout.simple_dropdown_item_1line,nomDiscos)
        binding.spinner.adapter = arrayAdapter

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                 disco = nomDiscos[p2]
                 pos = p2
            }


        }



        repository.cargarClientesFree(clientesPendientes)
        ordenarClientes(clientesPendientes)

        binding.recyclerViewpendientes.layoutManager = LinearLayoutManager(requireActivity().baseContext)

        val adapter= RecyclerPendAdapter(clientesPendientes,{listener2(it)})

        setFragmentResultListener("requestkeypend"){
            requestkey, bundle->
            val result = bundle.getString("bundleKeypend")

            if (result== "okpend"){


                repository.cargarClientesFree(clientesPendientes)
                ordenarClientes(clientesPendientes)
                binding.recyclerViewpendientes.adapter = adapter

                if (clientesPendientes.isNotEmpty()){
                    childFragmentManager.beginTransaction().hide(fragment).commit()
                    binding.cantpendientes.text = clientesPendientes.size.toString()

                    repository.getDiscoFree(nomDiscos)
                    if (nomDiscos.isNotEmpty()){
                        arrayAdapter.notifyDataSetChanged()
                        disco = nomDiscos[pos]

                    }
                }
            }
        }


        setFragmentResultListener("requestkeypendDisco"){
            requestkey, bundle->
            val result = bundle.getString("bundleKeypendDisco")

            if (result== "okpendDisco"){
               /* requireActivity().supportFragmentManager.beginTransaction().hide(fragment).commit()*/
                repository.getDiscoFree(nomDiscos)
                arrayAdapter.notifyDataSetChanged()
                disco = nomDiscos[pos]

            }
        }


        if(!clientesPendientes.isEmpty()){

            binding.cantpendientes.text = clientesPendientes.size.toString()

            binding.recyclerViewpendientes.adapter = adapter

        }else{

            childFragmentManager.beginTransaction().replace(R.id.framependientes, fragment).commit()
            binding.cantpendientes.text = "0"

        }

    }




    private fun listener2(cliente: Cliente){

        if (nomDiscos.isEmpty()){

            Toast.makeText(requireContext(),"no hay discos disponibles",Toast.LENGTH_LONG).show()

        }else{

            val c = Calendar.getInstance()
            val d = c
            d.add(Calendar.HOUR_OF_DAY, prefs.getTiempo())

            activarAlarma(cliente,d, requireContext(), disco)

            repository.insertarEntrega(Entrega(0, cliente.id, disco.id, cliente.nombre, disco.nombre, c, d, 1, cliente.ingreso))
            discoaux= disco
            disco.estado = 0
            repository.modificarDisco(disco)

            nomDiscos.remove(disco)

            arrayAdapter.notifyDataSetChanged()
            posaux = pos

            ValidarSpinner()


            cliente.estado = 0
            repository.modificarCliente(cliente)
            position = clientesPendientes.indexOf(cliente)
            clientesPendientes.remove(cliente)
            binding.recyclerViewpendientes.adapter!!.notifyDataSetChanged()
            binding.cantpendientes.text = clientesPendientes.size.toString()

            setFragmentResult("requestkeyCopiando", bundleOf("bundleKeyCopiando" to "okcopy"))

            if (clientesPendientes.isEmpty()){
                childFragmentManager.beginTransaction().replace(R.id.framependientes, fragment).commit()
                childFragmentManager.beginTransaction().show(fragment).commit()

            }



            Snackbar.make(binding.recyclerViewpendientes,"Entrega en proceso",Snackbar.LENGTH_LONG)
                    .setAction("Deshacer",object: View.OnClickListener{
                        override fun onClick(p0: View?) {
                            cliente.estado= 1
                            repository.modificarCliente(cliente)
                            clientesPendientes.add(position,cliente)
                            binding.recyclerViewpendientes.adapter!!.notifyDataSetChanged()
                            binding.cantpendientes.text = clientesPendientes.size.toString()

                            discoaux.estado = 1
                            repository.modificarDisco(discoaux)
                            nomDiscos.add(posaux,discoaux)
                            disco= nomDiscos[posaux]
                            arrayAdapter.notifyDataSetChanged()

                            cancelarAlarma(discoaux,requireContext())

                            var entregas= ArrayList<Entrega>()
                            repository.cargarEntregas(entregas)
                            repository.deleteEntrega(entregas[entregas.size-1].id)

                            childFragmentManager.beginTransaction().hide(fragment).commit()

                            setFragmentResult("requestkeyCopiando", bundleOf("bundleKeyCopiando" to "okcopy"))


                        }


                    }).show()



        }
    }


    private fun ValidarSpinner(){
        if (!nomDiscos.isEmpty()){
            if(pos == nomDiscos.size){
                pos= pos-1 }
            disco= nomDiscos[pos]
    }}

    private fun ordenarClientes(list:ArrayList<Cliente>){
        if (list.isNotEmpty()){
        list.sortBy { it.dia }


    } }

}
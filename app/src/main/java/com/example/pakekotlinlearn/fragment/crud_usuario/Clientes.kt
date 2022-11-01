package com.example.pakekotlinlearn.fragment.crud_usuario

import android.app.AlertDialog
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pakekotlinlearn.Adapters.RecyclerClienteAdapter
import com.example.pakekotlinlearn.Database.Repository
import com.example.pakekotlinlearn.PoJO.Cliente
import com.example.pakekotlinlearn.PoJO.Entrega
import com.example.pakekotlinlearn.R
import com.example.pakekotlinlearn.Validaciones.testClienteEntrega
import com.example.pakekotlinlearn.databinding.FragmentClientesBinding
import com.example.pakekotlinlearn.fragment.crud_disco.ModificarDisco
import java.util.*


class Clientes : Fragment() {
    private var _binding: FragmentClientesBinding? = null
    private val binding get() = _binding!!
    private  var clientesList= ArrayList<Cliente>()
    private lateinit var repository: Repository
    private lateinit var adapter: RecyclerClienteAdapter



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentClientesBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        repository= Repository(requireActivity().baseContext)
        repository.cargarClientes(clientesList)

        binding.recyclerViewcliente.layoutManager = LinearLayoutManager(requireContext())


        adapter= RecyclerClienteAdapter(clientesList){

            val nombre=it.nombre


            val dialog = AlertDialog.Builder(requireContext())
                    .setTitle(Html.fromHtml("<font color='#0000ff'>$nombre</font>"))
                    .setMessage(Html.fromHtml("<font color='#000000'>Acción a cometer:</font>"))
                    .setCancelable(true)
                    .setNegativeButton("Eliminar"){view,_->

                        repository.deleteCliente(it.id)
                        clientesList.remove(it)
                        binding.textViewClientesCant.text = clientesList.size.toString()
                        binding.recyclerViewcliente.adapter?.notifyDataSetChanged()

                    }
                    .setPositiveButton("Modificar"){view,_->

                        var result = Bundle()
                        result.putInt("id",it.id)
                        result.putString("bundleKeyClienteM","ok")

                        setFragmentResult("requestClientemodikey",result )

                        requireActivity().supportFragmentManager
                                .beginTransaction()
                                .replace(R.id.frame, ModificarUsuario())
                                .commit()

                    }
                    .setNeutralButton("Reactivar"){view,_->

                        var c= repository.getClienteById(it.id)

                        var auxentre = ArrayList<Entrega>()
                        repository.cargarEntregasFree(auxentre)

                        if(!testClienteEntrega(c.id,auxentre)){
                            c.estado= 1
                            repository.modificarCliente(c)
                        }


                    }.create()

            dialog.window?.setBackgroundDrawable(resources.getDrawable(R.drawable.curvo15white,null))
            dialog.show()


        }

        setFragmentResultListener("requestkeyCliente"){
            requestkey, bundle->
            val result = bundle.getString("bundleKeyCliente")

            if (result== "okcliente"){

                binding.recyclerViewcliente.adapter?.notifyDataSetChanged()
            }
        }


        if (!clientesList.isEmpty()){

            binding.textViewClientesCant.text = clientesList.size.toString()
            binding.recyclerViewcliente.adapter= adapter


            binding.searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    adapter.filter.filter(newText)
                    return false
                }
            })

        }else{ }



        binding.floatingActionButton2.setOnClickListener {

            requireActivity().supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.frame, CrearCliente())
                    .commit()

        }

    }
}

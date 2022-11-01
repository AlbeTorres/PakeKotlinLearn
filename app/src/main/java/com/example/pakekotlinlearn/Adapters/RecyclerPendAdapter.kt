package com.example.pakekotlinlearn.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pakekotlinlearn.PoJO.Cliente
import com.example.pakekotlinlearn.R
import com.example.pakekotlinlearn.Validaciones.setDia
import com.example.pakekotlinlearn.databinding.ItemClienteBinding


class RecyclerPendAdapter( private val clientes: ArrayList<Cliente> = ArrayList(),
                           private val listener2:(Cliente)->Unit): RecyclerView.Adapter<RecyclerPendAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cliente,parent,false)
        return ViewHolder(view)

    }

    override fun getItemCount() = clientes.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = clientes[position]
        holder.bind(item,listener2)
    }


    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

        val binding = ItemClienteBinding.bind(view)

        fun bind(item : Cliente,listener2: (Cliente) -> Unit){

            binding.nombreRecy.text= item.nombre
            binding.textdia.text= setDia(item.dia)

            binding.imageViewArchivar.setOnClickListener { listener2(item) }


        }

    }
}
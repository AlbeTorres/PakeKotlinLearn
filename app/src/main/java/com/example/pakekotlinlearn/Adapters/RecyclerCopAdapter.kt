package com.example.pakekotlinlearn.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pakekotlinlearn.PoJO.Cliente
import com.example.pakekotlinlearn.PoJO.Entrega
import com.example.pakekotlinlearn.R
import com.example.pakekotlinlearn.databinding.ItemEntregasCopiandoBinding

class RecyclerCopAdapter(private val entregas: ArrayList<Entrega> = ArrayList(),
                         private val listener:(Entrega)->Unit,
                         private val listener2:(Entrega)->Unit): RecyclerView.Adapter<RecyclerCopAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_entregas_copiando,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount()= entregas.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = entregas[position]
        holder.itemView.setOnClickListener { listener2( item ) }
        holder.bind(item,listener)

    }

    class ViewHolder( view: View): RecyclerView.ViewHolder(view){
        val binding = ItemEntregasCopiandoBinding.bind(view)

        fun bind(item: Entrega,listener: (Entrega) -> Unit){

            binding.copiandoRecy.text = item.nombre_cliente
            binding.textcopiando.text= item.nombre_disco

            binding.imagecopaindook.setOnClickListener { listener (item) }


        }

    }


}
package com.example.pakekotlinlearn.Adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getDrawable
import androidx.recyclerview.widget.RecyclerView
import com.example.pakekotlinlearn.PoJO.DiaSemana
import com.example.pakekotlinlearn.R
import com.example.pakekotlinlearn.databinding.ItemdiaentregaBinding

class RecyclerSemanaAdapter(private var semanaList: ArrayList<DiaSemana> = ArrayList(), private var listener:((DiaSemana)->Unit))
    :RecyclerView.Adapter<RecyclerSemanaAdapter.ViewHolder>() {

    private var item2: ViewHolder? = null



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.itemdiaentrega,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount()= semanaList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = semanaList[position]

        holder.bind(item)
        holder.itemView.setOnClickListener {

            if (item2!= null){
                item2!!.itemView.background = getDrawable(holder.itemView.context,R.drawable.curvo15white)
                item2!!.binding.textViewdiasemana.setTextColor(ContextCompat.getColor(holder.itemView.context,R.color.azulclaro))
            }


            holder.itemView.setBackground(getDrawable(holder.itemView.context,R.drawable.curco15colorfondoazulclaro))
            holder.binding.textViewdiasemana.setTextColor(ContextCompat.getColor(holder.itemView.context,R.color.white))

            item2= holder
            listener(item)
        }

    }

    class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        val binding = ItemdiaentregaBinding.bind(view)

        fun bind(item:DiaSemana){

            binding.textViewdiasemana.text= item.dia

        }



    }

}
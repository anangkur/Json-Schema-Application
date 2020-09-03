package com.anangkur.jsonschemeapplication.view.molecule

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.anangkur.jsonschemeapplication.R
import com.anangkur.jsonschemeapplication.databinding.ItemFilterBinding
import com.anangkur.jsonschemeapplication.model.DropDownValue
import com.anangkur.jsonschemeapplication.utils.extensions.visible

/**
 * Created by ilgaputra15
 * on Wednesday, 13/05/2020 23.38
 * Mobile Engineer - https://github.com/ilgaputra15
 **/
class DropDownRecyclerAdapter(private val data: List<DropDownValue>)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var dataShow = data

    fun showDataFilter(value: List<DropDownValue>) {
        dataShow = value
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return dataShow.size
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : RecyclerView.ViewHolder {
        val viewHolder =  PartViewHolder(
            ItemFilterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            val selected = dataShow[position]
            if (selected.isSelected) selected.isSelected = false
            else {
                data.forEach { it.isSelected = false }
                data.find { it == selected }?.isSelected = true
            }
            notifyDataSetChanged()
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as PartViewHolder).bind(dataShow[position])
    }

    class PartViewHolder(private val binding: ItemFilterBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(part: DropDownValue) {
            binding.textName.text = part.value
            setView(part.isSelected)
        }
        private fun setView(isSelected: Boolean) {
            val linearColor = if (isSelected) R.color.ice_blue else R.color.white
            val textColor = if (isSelected) R.color.blue else R.color.gun_metal
            binding.imageCheck.visible = isSelected
            binding.linearItem.setBackgroundColor(ContextCompat.getColor(itemView.context, linearColor))
            binding.textName.setTextColor(ContextCompat.getColor(itemView.context, textColor))
        }
    }
}
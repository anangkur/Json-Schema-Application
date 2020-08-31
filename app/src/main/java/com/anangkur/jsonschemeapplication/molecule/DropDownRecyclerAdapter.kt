package com.anangkur.jsonschemeapplication.molecule

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.anangkur.jsonschemeapplication.R
import com.anangkur.jsonschemeapplication.extensions.visible
import com.anangkur.jsonschemeapplication.model.DropDownValue
import kotlinx.android.synthetic.main.item_filter.view.*

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
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_filter,
                parent,
                false
            ))
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

    class PartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(part: DropDownValue) {
            itemView.textName.text = part.value
            setView(part.isSelected)
        }
        private fun setView(isSelected: Boolean) {
            val linearColor = if (isSelected) R.color.ice_blue else R.color.white
            val textColor = if (isSelected) R.color.blue else R.color.gun_metal
            itemView.imageCheck.visible = isSelected
            itemView.linearItem.setBackgroundColor(ContextCompat.getColor(itemView.context, linearColor))
            itemView.textName.setTextColor(ContextCompat.getColor(itemView.context, textColor))
        }
    }
}
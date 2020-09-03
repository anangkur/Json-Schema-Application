package com.anangkur.jsonschemeapplication.view.molecule

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anangkur.jsonschemeapplication.databinding.ItemDotTextBinding

/**
 * Created by ilgaputra15
 * on Friday, 20/03/2020 23.24
 * Mobile Engineer - https://github.com/ilgaputra15
 **/
class DotTextAdapter(private val data: List<String>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount(): Int {
        return data.size
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : RecyclerView.ViewHolder {
        return PartViewHolder(
            ItemDotTextBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as PartViewHolder).bind(data[position])
    }

    class PartViewHolder(private val binding: ItemDotTextBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(part: String) {
            binding.textValue.text = part
        }
    }
}
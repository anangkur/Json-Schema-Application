package com.anangkur.jsonschemeapplication.view.molecule

import androidx.core.content.ContextCompat
import com.anangkur.jsonschemeapplication.R
import com.anangkur.jsonschemeapplication.databinding.MoleculeFormLocationBinding
import com.anangkur.jsonschemeapplication.model.DynamicView
import com.anangkur.jsonschemeapplication.model.Location
import com.anangkur.jsonschemeapplication.utils.extensions.visible

/**
 * Created by ilgaputra15
 * on Sunday, 22/03/2020 16.04
 * Mobile Engineer - https://github.com/ilgaputra15
 **/
class FormLocationViewHolder (
    private val binding: MoleculeFormLocationBinding
) : BaseMoleculeViewHolder(binding.root) {

    fun bind(data: DynamicView, itemClickListener:(widget: String, key: String) -> Unit) {
        val title = if (data.isRequired) "${data.jsonSchema.title} *" else data.jsonSchema.title
        binding.textTitle.text = title
        binding.frameLocation.setOnClickListener {
            itemClickListener.invoke(data.uiSchemaRule.uiWidget!!, data.componentName)
        }

        if (data.preview != null) {
            when (data.preview) {
                is Location -> {
                    val result = data.preview as Location
                    data.value = "${result.lat},${result.long}"
                    binding.textValue.text = result.addressName
                }
                else -> binding.textValue.text = data.preview.toString()
            }
        }

        binding.frameLocation.isEnabled = data.isEnable
        setError(data)
        binding.buttonHelp.let {
            it.visible = data.uiSchemaRule.uiHelp != null
            it.setOnClickListener { criteriaDialog(data) }
        }
    }

    private fun setError(data: DynamicView) {
        val color = if (data.isError) R.color.reddish else R.color.very_light_grey
        binding.viewLine.setBackgroundColor(ContextCompat.getColor(itemView.context, color))

        if (data.isError && data.value == null)
            binding.textError.let {
                it.text = itemView.context.getString(R.string.text_can_not_empty, data.jsonSchema.title)
                it.visible = data.isError
            }
        else if (data.isError && data.value != null)
            binding.textError.let {
                it.text = data.errorValue
                it.visible = data.errorValue != null
            }
        else binding.textError.visible = false
    }
}
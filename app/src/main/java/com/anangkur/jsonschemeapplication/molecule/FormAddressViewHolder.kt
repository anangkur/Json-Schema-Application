package com.anangkur.jsonschemeapplication.molecule

import androidx.core.content.ContextCompat
import com.anangkur.jsonschemeapplication.R
import com.anangkur.jsonschemeapplication.databinding.MoleculeFormAddressBinding
import com.anangkur.jsonschemeapplication.extensions.afterTextChanged
import com.anangkur.jsonschemeapplication.extensions.visible
import com.anangkur.jsonschemeapplication.model.DynamicView

/**
 * Created by ilgaputra15
 * on Thursday, 07/05/2020 11.30
 * Mobile Engineer - https://github.com/ilgaputra15
 **/
class FormAddressViewHolder(
    private val binding: MoleculeFormAddressBinding
) : BaseMoleculeViewHolder(binding.root) {

    fun bind(data: DynamicView) {
        if (data.preview != null) {
            val preview = data.preview.toString().split(";")
            if (preview.size == 3) {
                with(binding) {
                    editTextPostalAddress.setText(preview[0])
                    editTextCity.setText(preview[1])
                    editTextPostalCode.setText(preview[2])
                }
            }
        }
        with(binding) {
            val title = if (data.isRequired) "${data.jsonSchema.title} *" else data.jsonSchema.title
            textTitle.text = title
            editTextPostalAddress.isEnabled = data.isEnable
            editTextCity.isEnabled = data.isEnable
            editTextPostalCode.isEnabled = data.isEnable
            editTextPostalAddress.afterTextChanged { setValue(data) }
            editTextCity.afterTextChanged { setValue(data) }
            editTextPostalCode.afterTextChanged { setValue(data) }
            buttonHelp.let {
                it.visible = data.uiSchemaRule.uiHelp != null
                it.setOnClickListener {  }
            }
        }
        setError(data)
    }

    fun setValue(data: DynamicView) {
        val address = binding.editTextPostalAddress.text
        val city = binding.editTextCity.text
        val postalCode = binding.editTextPostalCode.text
        val value =
            if (address.isNotEmpty() && city.isNotEmpty() && postalCode.isNotEmpty())
                "$address;$city;$postalCode"
            else null
        data.value = value
        data.preview = value
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
package com.anangkur.jsonschemeapplication.molecule

import android.view.View
import androidx.core.content.ContextCompat
import com.anangkur.jsonschemeapplication.R
import com.anangkur.jsonschemeapplication.extensions.afterTextChanged
import com.anangkur.jsonschemeapplication.extensions.visible
import com.anangkur.jsonschemeapplication.model.DynamicView
import kotlinx.android.synthetic.main.molecule_form_address.view.*

/**
 * Created by ilgaputra15
 * on Thursday, 07/05/2020 11.30
 * Mobile Engineer - https://github.com/ilgaputra15
 **/
class FormAddressViewHolder(itemView: View) : BaseMoleculeViewHolder(itemView) {

    companion object {
        const val layout = R.layout.molecule_form_address
    }

    fun bind(data: DynamicView) {
        if (data.preview != null) {
            val preview = data.preview.toString().split(";")
            if (preview.size == 3) {
                with(itemView) {
                    editTextPostalAddress.setText(preview[0])
                    editTextCity.setText(preview[1])
                    editTextPostalCode.setText(preview[2])
                }
            }
        }
        with(itemView) {
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
        val address = itemView.editTextPostalAddress.text
        val city = itemView.editTextCity.text
        val postalCode = itemView.editTextPostalCode.text
        val value =
            if (address.isNotEmpty() && city.isNotEmpty() && postalCode.isNotEmpty())
                "$address;$city;$postalCode"
            else null
        data.value = value
        data.preview = value
    }

    private fun setError(data: DynamicView) {
        val color = if (data.isError) R.color.reddish else R.color.very_light_grey
        itemView.viewLine.setBackgroundColor(ContextCompat.getColor(itemView.context, color))

        if (data.isError && data.value == null)
            itemView.textError.let {
                it.text = itemView.context.getString(R.string.text_can_not_empty, data.jsonSchema.title)
                it.visible = data.isError
            }
        else if (data.isError && data.value != null)
            itemView.textError.let {
                it.text = data.errorValue
                it.visible = data.errorValue != null
            }
        else itemView.textError.visible = false
    }
}
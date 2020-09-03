package com.anangkur.jsonschemeapplication.view.molecule

import androidx.core.content.ContextCompat
import com.anangkur.jsonschemeapplication.R
import com.anangkur.jsonschemeapplication.databinding.MoleculeFormDatePickerBinding
import com.anangkur.jsonschemeapplication.view.dialog.DateTimePickerDialog
import com.anangkur.jsonschemeapplication.model.DynamicView
import com.anangkur.jsonschemeapplication.utils.DateHelper
import com.anangkur.jsonschemeapplication.utils.extensions.visible
import java.util.*

/**
 * Created by ilgaputra15
 * on Sunday, 22/03/2020 16.04
 * Mobile Engineer - https://github.com/ilgaputra15
 **/
class FormDatePickerViewHolder(
    private val binding: MoleculeFormDatePickerBinding
) : BaseMoleculeViewHolder(binding.root) {

    private val calender = Calendar.getInstance()

    fun bind(data: DynamicView) {
        val title = if (data.isRequired) "${data.jsonSchema.title} *" else data.jsonSchema.title
        binding.textTitle.text = title
        val picker =
            DateTimePickerDialog(itemView.context)
        binding.frameAction.setOnClickListener {
            when (data.uiSchemaRule.uiWidget) {
                "date" -> {
                    picker.setValueOfDate(calender) {
                        setValue(data, "dd MMM yyyy")
                    }
                }
                "time" -> {
                    picker.setValueOfTime(calender) {
                        setValue(data, "HH:mm:ss")
                    }
                }
                else -> {
                    picker.setValueOfDate(calender) {
                        picker.setValueOfTime(calender) {
                            setValue(data, "dd MMM yyyy HH:mm:ss")
                        }
                    }
                }
            }
        }
        if (data.preview != null) binding.textValue.text = data.preview as String
        binding.frameAction.isEnabled = data.isEnable
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

    fun setValue(data: DynamicView, pattern: String) {
        binding.textValue.requestFocus()
        val preview = DateHelper.convertDateToString(calender.time, pattern)
        binding.textValue.text = preview
        data.preview = preview
        data.value = DateHelper.convertDateToString(calender.time, "yyyy-MM-dd'T'HH:mm:ss'Z'")
    }
}
package com.anangkur.jsonschemeapplication.view.molecule

import android.content.res.ColorStateList
import android.widget.CheckBox
import android.widget.TableRow
import androidx.core.content.ContextCompat
import com.anangkur.jsonschemeapplication.R
import com.anangkur.jsonschemeapplication.databinding.MoleculeFormCheckBoxBinding
import com.anangkur.jsonschemeapplication.model.DynamicView
import com.anangkur.jsonschemeapplication.utils.extensions.visible

/**
 * Created by ilgaputra15
 * on Thursday, 19/03/2020 22.00
 * Mobile Engineer - https://github.com/ilgaputra15
 **/
class FormCheckBoxViewHolder(
    private val binding: MoleculeFormCheckBoxBinding
) : BaseMoleculeViewHolder(binding.root) {

    private val datas = ArrayList<String>()

    fun bind(data: DynamicView) {
        val title = if (data.isRequired) "${data.jsonSchema.title} *" else data.jsonSchema.title
        if (data.preview != null) {
            val result = data.preview.toString().split(";")
            datas.addAll(result)
        }
        binding.textTitle.text = title
        binding.textDesc.let {
            it.visible = data.jsonSchema.description != ""
            it.text = data.jsonSchema.description
        }
        binding.linearLayoutCheckBox.removeAllViews()
        var index = 0
        val value = data.jsonSchema.items?.getAsJsonArray("enum")?.map { it.asString }
        value?.forEach {
            val checkBox = generateCheckBox(it, index++)
            if (data.isError)
                checkBox.buttonTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(itemView.context, R.color.reddish)
                )
            checkBox.setOnClickListener { _ ->
                setValue(it, checkBox.isChecked)
                val values = if (datas.isEmpty()) null else datas.joinToString(";")
                data.value = values
                data.preview = values
            }
            if (datas.any { data -> data == it }) checkBox.isChecked
            checkBox.isEnabled = data.isEnable
            binding.linearLayoutCheckBox.addView(checkBox)
        }
        setError(data)
        binding.buttonHelp.let {
            it.visible = data.uiSchemaRule.uiHelp != null
            it.setOnClickListener { criteriaDialog(data) }
        }
    }

    private fun generateCheckBox(value: String, index: Int) : CheckBox {
        val params = TableRow.LayoutParams(
            TableRow.LayoutParams.MATCH_PARENT,
            TableRow.LayoutParams.WRAP_CONTENT)
        params.setMargins(0, 24, 0, 0)
        val checkBox = CheckBox(itemView.context)
        checkBox.layoutParams = params
        checkBox.id = index
        checkBox.setPadding(36,0,0,0)
        checkBox.textSize = 12F
        checkBox.setTextColor(ContextCompat.getColor(itemView.context,R.color.brown_grey))
        checkBox.text = value
        checkBox.isChecked = datas.contains(value)
        return checkBox
    }

    private fun setError(data: DynamicView) {
        val color = if (data.isError && data.value == null) R.color.reddish else R.color.very_light_grey
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

    fun setValue(value: String, isSelected: Boolean) {
        if (isSelected) {
            datas.add(value)
        } else datas.remove(value)
    }
}
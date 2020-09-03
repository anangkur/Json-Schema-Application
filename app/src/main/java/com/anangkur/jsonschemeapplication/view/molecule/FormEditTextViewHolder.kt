package com.anangkur.jsonschemeapplication.view.molecule

import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import androidx.core.content.ContextCompat
import com.anangkur.jsonschemeapplication.R
import com.anangkur.jsonschemeapplication.databinding.MoleculeFormEditTextBinding
import com.anangkur.jsonschemeapplication.model.DynamicView
import com.anangkur.jsonschemeapplication.utils.Validation
import com.anangkur.jsonschemeapplication.utils.extensions.visible

/**
 * Created by ilgaputra15
 * on Sunday, 22/03/2020 16.04
 * Mobile Engineer - https://github.com/ilgaputra15
 **/
class FormEditTextViewHolder(
    private val binding: MoleculeFormEditTextBinding
) : BaseMoleculeViewHolder(binding.root) {

    fun bind(data: DynamicView) {
        val title = if (data.isRequired) "${data.jsonSchema.title} *" else data.jsonSchema.title
        binding.textTitle.text = title
        val inputType = when(data.jsonSchema.type) {
            "integer" -> InputType.TYPE_CLASS_NUMBER
            else -> InputType.TYPE_CLASS_TEXT
        }

        val prefix = when (data.uiSchemaRule.uiWidget) {
            "phone" -> "+62"
            "price" -> "Rp"
            else -> ""
        }
        binding.textPrefix.let {
            it.text = prefix
            it.visible = prefix.isNotEmpty()
        }
        if (data.preview != null) binding.editTextValue.setText(data.preview.toString())
        binding.editTextValue.isEnabled = data.isEnable
        binding.editTextValue.inputType = inputType
        binding.editTextValue.isSingleLine = false

        binding.editTextValue.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(string: Editable?) {
                if (binding.editTextValue.text.hashCode() == string.hashCode()) {
                    var value: String? = binding.editTextValue.text.toString()
                    value = if (value.isNullOrEmpty()) null else value.toString().trim()
                    val isValid = checkValidate(data.uiSchemaRule.uiWidget ?: "", value ?: "")
                    data.value = value
                    data.preview = value
                    data.isValidated = isValid
                    if (!isValid) data.errorValue = "Format ${data.jsonSchema.title} salah"
                }
            }
            override fun beforeTextChanged(string: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })
        setError(data)
        binding.buttonHelp.let {
            it.visible = data.uiSchemaRule.uiHelp != null
            it.setOnClickListener { criteriaDialog(data) }
        }
    }

    fun checkValidate(widget: String, value: String): Boolean {
        return when (widget) {
            "email" -> Validation.isEmailValid(value)
            else -> true
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
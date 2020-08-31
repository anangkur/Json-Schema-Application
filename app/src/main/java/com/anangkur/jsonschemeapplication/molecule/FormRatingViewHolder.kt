package com.anangkur.jsonschemeapplication.molecule

import androidx.core.content.ContextCompat
import com.anangkur.jsonschemeapplication.R
import com.anangkur.jsonschemeapplication.databinding.MoleculeFormRatingBinding
import com.anangkur.jsonschemeapplication.extensions.visible
import com.anangkur.jsonschemeapplication.model.DynamicView

/**
 * Created by ilgaputra15
 * on Tuesday, 28/04/2020 11.55
 * Mobile Engineer - https://github.com/ilgaputra15
 **/
class FormRatingViewHolder(
    private val binding: MoleculeFormRatingBinding
) : BaseMoleculeViewHolder(binding.root) {

    fun bind(data: DynamicView) {
        val title = if (data.isRequired) "${data.jsonSchema.title} *" else data.jsonSchema.title
        binding.textTitle.text = title
        setError(data)
        binding.buttonHelp.let {
            it.visible = data.uiSchemaRule.uiHelp != null
            it.setOnClickListener { criteriaDialog(data) }
        }
        if (data.preview is Float) binding.ratingBar.rating = data.preview as Float
        binding.ratingBar.let {
            it.numStars = data.jsonSchema.maximum ?: 5
            it.setOnRatingBarChangeListener { _, value, _ ->
                data.value = value
                data.preview = value
            }
            it.isEnabled = data.isEnable
        }
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
}
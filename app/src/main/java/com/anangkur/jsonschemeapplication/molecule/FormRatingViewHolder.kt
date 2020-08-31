package com.anangkur.jsonschemeapplication.molecule

import android.view.View
import androidx.core.content.ContextCompat
import com.anangkur.jsonschemeapplication.R
import com.anangkur.jsonschemeapplication.extensions.visible
import com.anangkur.jsonschemeapplication.model.DynamicView
import kotlinx.android.synthetic.main.molecule_form_rating.view.*

/**
 * Created by ilgaputra15
 * on Tuesday, 28/04/2020 11.55
 * Mobile Engineer - https://github.com/ilgaputra15
 **/
class FormRatingViewHolder(itemView: View) : BaseMoleculeViewHolder(itemView) {

    companion object {
        const val layout = R.layout.molecule_form_rating
    }

    fun bind(data: DynamicView) {
        val title = if (data.isRequired) "${data.jsonSchema.title} *" else data.jsonSchema.title
        itemView.textTitle.text = title
        setError(data)
        itemView.buttonHelp.let {
            it.visible = data.uiSchemaRule.uiHelp != null
            it.setOnClickListener { criteriaDialog(data) }
        }
        if (data.preview is Float) itemView.ratingBar.rating = data.preview as Float
        itemView.ratingBar.let {
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
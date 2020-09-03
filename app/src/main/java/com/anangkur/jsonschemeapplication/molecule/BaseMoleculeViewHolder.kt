package com.anangkur.jsonschemeapplication.molecule

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.anangkur.jsonschemeapplication.R
import com.anangkur.jsonschemeapplication.databinding.DialogCriteriaSubmissionBinding
import com.anangkur.jsonschemeapplication.extensions.fullExpanded
import com.anangkur.jsonschemeapplication.extensions.visible
import com.anangkur.jsonschemeapplication.model.DynamicView
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog

/**
 * Created by ilgaputra15
 * on Tuesday, 14/04/2020 10.56
 * Mobile Engineer - https://github.com/ilgaputra15
 **/

abstract class BaseMoleculeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), BaseMolecule {

    @SuppressLint("InflateParams")
    override fun criteriaDialog(data: DynamicView) {
        val bottomDialog = BottomSheetDialog(itemView.context)
        val layout = DialogCriteriaSubmissionBinding.inflate(LayoutInflater.from(itemView.context))
        bottomDialog.setContentView(layout.root)
        bottomDialog.show()
        bottomDialog.fullExpanded(layout.root)

        val title = data.jsonSchema.title
        val helpImage = data.uiSchemaRule.uiHelpImage
        val help = data.uiSchemaRule.uiHelp

        layout.textTitle.text = title
        layout.cardImageCriteria.visible = helpImage != null
        if(helpImage != null) {
            Glide.with(itemView)
                .load(helpImage)
                .error(R.mipmap.ic_launcher)
                .into(layout.imageCriteria)
        }

        val criteriaList = help?.split(";") ?: return
        if (criteriaList.size == 1) {
            layout.textCriteria.let{
                it.text = help
                it.visible = true
            }
        }
        if (criteriaList.size > 1) {
            val dotTextAdapter = DotTextAdapter(criteriaList)
            layout.listCriteria.visible = true
            layout.listCriteria.adapter = dotTextAdapter
        }
    }

}

interface BaseMolecule {
    fun criteriaDialog(data: DynamicView)
}
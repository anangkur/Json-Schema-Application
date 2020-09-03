package com.anangkur.jsonschemeapplication.molecule

import android.graphics.Bitmap
import android.net.Uri
import androidx.core.content.ContextCompat
import com.anangkur.jsonschemeapplication.R
import com.anangkur.jsonschemeapplication.databinding.MoleculeFormFilePhotoBinding
import com.anangkur.jsonschemeapplication.extensions.visible
import com.anangkur.jsonschemeapplication.model.DynamicView
import com.anangkur.jsonschemeapplication.utils.FileUtils
import com.bumptech.glide.Glide

/**
 * Created by ilgaputra15
 * on Thursday, 19/03/2020 21.58
 * Mobile Engineer - https://github.com/ilgaputra15
 **/
class FormFilePhotoViewHolder(
    private val binding: MoleculeFormFilePhotoBinding
) : BaseMoleculeViewHolder(binding.root) {

    fun bind(data: DynamicView, itemClickListener:(widget: String, key: String) -> Unit) {
        val title = if (data.isRequired) "${data.jsonSchema.title} *" else data.jsonSchema.title
        binding.textTitle.text = title
        binding.textDesc.text = data.jsonSchema.description
        binding.buttonAction.setOnClickListener {
            itemClickListener.invoke(data.uiSchemaRule.uiWidget!!, data.componentName)
        }

        if (data.preview != null) {
            when (data.preview) {
                is Bitmap -> {
                    Glide.with(itemView.context).load(data.preview as Bitmap).into(binding.imagePreview)
                }
                is String -> {
                    Glide.with(itemView.context).load(data.preview as String).into(binding.imagePreview)
                    binding.textFileName.let {
                        it.text = if (data.fileName == null) data.preview as String else data.fileName
                        it.visible = true
                    }
                    try {
                        val dataUri = Uri.parse(data.preview as String)
                        loadUriValue(data, dataUri)
                    } catch (e: Exception) {
                        // todo implement firebase analytics
                        e.printStackTrace()
                    }
                }
                is Uri -> {
                    loadUriValue(data, data.preview as Uri)
                }
            }
            binding.imagePreview.visible = true

        }
        if (data.fileName != null) {
            binding.textFileName.let {
                it.text = data.fileName
                it.visible = true
            }
        }
        binding.buttonAction.isEnabled = data.isEnable
        setError(data)
        binding.buttonHelp.let {
            it.visible = data.uiSchemaRule.uiHelp != null
            it.setOnClickListener { criteriaDialog(data) }
        }
    }

    private fun loadUriValue(data: DynamicView, uri: Uri) {
        try {
            val fileName: String?
            val selectedPath = FileUtils.getRealPath(itemView.context, uri) ?: return
            fileName = selectedPath.substring(selectedPath.lastIndexOf("/") + 1)
            data.fileName = fileName
            data.value = uri
            Glide.with(itemView.context).load(uri).into(binding.imagePreview)
            binding.textFileName.let {
                it.text = fileName
                it.visible = true
            }
        } catch (e: Exception) {
            // todo implement firebase analytics
            e.printStackTrace()
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
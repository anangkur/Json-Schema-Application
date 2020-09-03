package com.anangkur.jsonschemeapplication.view.molecule

import android.view.LayoutInflater
import androidx.core.content.ContextCompat
import com.anangkur.jsonschemeapplication.R
import com.anangkur.jsonschemeapplication.databinding.DialogDropDownBinding
import com.anangkur.jsonschemeapplication.databinding.MoleculeFormDropDownBinding
import com.anangkur.jsonschemeapplication.utils.extensions.fullExpanded
import com.anangkur.jsonschemeapplication.model.DropDownValue
import com.anangkur.jsonschemeapplication.model.DynamicView
import com.anangkur.jsonschemeapplication.utils.extensions.afterTextChanged
import com.anangkur.jsonschemeapplication.utils.extensions.visible
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by ilgaputra15
 * on Sunday, 22/03/2020 16.04
 * Mobile Engineer - https://github.com/ilgaputra15
 **/
class FormDropDownViewHolder(
    private val binding: MoleculeFormDropDownBinding
) : BaseMoleculeViewHolder(binding.root) {

    companion object {
        const val LIMIT_SHOW_SEARCH = 15
    }

    fun bind(data: DynamicView) {
        val title = if (data.isRequired) "${data.jsonSchema.title} *" else data.jsonSchema.title
        binding.textTitle.text = title
        binding.textDesc.let {
            it.text = data.jsonSchema.description
            it.visible = !data.jsonSchema.description.isNullOrEmpty()
        }
        val placeholder = data.uiSchemaRule.uiPlaceholder ?: data.jsonSchema.title

        var values = ArrayList<DropDownValue>()
        data.jsonSchema.enum?.map { DropDownValue(it.asString) }?.let { values.addAll(it) }
        if (data.preview != null) values.find { it.value == data.preview }?.isSelected = true
        showSelectedValue(placeholder, values)

        binding.frameValue.setOnClickListener {
            dropDownDialog(placeholder, values) { list ->
                values = list
                val selected = list.find { it.isSelected }?.value
                data.value = selected
                data.preview = selected
                showSelectedValue(placeholder, values)
            }
        }
        binding.buttonHelp.let {
            it.visible = data.uiSchemaRule.uiHelp != null
            it.setOnClickListener { criteriaDialog(data) }
        }
        setError(data)
    }

    private fun showSelectedValue(placeholder: String, list: ArrayList<DropDownValue>) {
        val selected = list.find { it.isSelected }
        binding.textValue.text = selected?.value ?: placeholder
    }

    private fun dropDownDialog(
        title: String,
        list: ArrayList<DropDownValue>,
        callback: (ArrayList<DropDownValue>) -> Unit
    ) {
        val listCopy = ArrayList(list.map { it.copy() })
        val bottomDialog = BottomSheetDialog(itemView.context, R.style.DialogWithKeyboardStyle)
        val layout = DialogDropDownBinding.inflate(LayoutInflater.from(itemView.context))
        bottomDialog.setContentView(layout.root)
        bottomDialog.fullExpanded(layout.root)
        val adapter = DropDownRecyclerAdapter(listCopy)
        with(layout) {
            editTextSearch.visible = list.size > LIMIT_SHOW_SEARCH
            textDialogTitle.text = title
            recyclerView.adapter = adapter
            imageClose.setOnClickListener { bottomDialog.dismiss() }
            buttonApply.setOnClickListener {
                callback.invoke(listCopy)
                bottomDialog.dismiss()
            }
            editTextSearch.afterTextChanged { query ->
                val filter = listCopy.filter {
                    it.value.toLowerCase(Locale.getDefault())
                        .contains(query.toString().toLowerCase(Locale.getDefault()))
                }
                adapter.showDataFilter(filter)
            }
        }
        bottomDialog.show()
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
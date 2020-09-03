package com.anangkur.jsonschemeapplication.view.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anangkur.jsonschemeapplication.databinding.*
import com.anangkur.jsonschemeapplication.model.DynamicView
import com.anangkur.jsonschemeapplication.view.molecule.*

/**
 * Created by ilgaputra15
 * on Thursday, 19/03/2020 21.48
 * Mobile Engineer - https://github.com/ilgaputra15
 **/

class QuestionAdapter(
    private val itemClickListener: (widget: String, key: String) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var datas: ArrayList<DynamicView> = ArrayList()

    companion object {
        const val EDIT_TEXT = 1
        const val FILE_PHOTO = 2
        const val DROPDOWN = 3
        const val CHECKBOX = 4
        const val RADIO = 5
        const val DATE_PICKER = 6
        const val LOCATION = 7
        const val RATING = 8
        const val ADDRESS = 9
    }

    fun setQuestions(list: ArrayList<DynamicView>) {
        datas = list
        notifyDataSetChanged()
    }

    fun updateItem(key: String) {
        val item = datas.find { it.componentName == key }
        notifyItemChanged(datas.indexOf(item))
    }

    fun refreshAdapter() {
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = when (viewType) {
            EDIT_TEXT -> FormEditTextViewHolder(MoleculeFormEditTextBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            FILE_PHOTO -> FormFilePhotoViewHolder(MoleculeFormFilePhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            DROPDOWN -> FormDropDownViewHolder(MoleculeFormDropDownBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            CHECKBOX -> FormCheckBoxViewHolder(MoleculeFormCheckBoxBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            RADIO -> FormRadioViewHolder(MoleculeFormRadioBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            DATE_PICKER -> FormDatePickerViewHolder(MoleculeFormDatePickerBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            LOCATION -> FormLocationViewHolder(MoleculeFormLocationBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            RATING -> FormRatingViewHolder(MoleculeFormRatingBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            ADDRESS -> FormAddressViewHolder(MoleculeFormAddressBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            else -> null
        }
        return view!!
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewType = holder.itemViewType
        val listData = datas[position]
        when (viewType) {
            EDIT_TEXT -> (holder as FormEditTextViewHolder).bind(listData )
            FILE_PHOTO -> (holder as FormFilePhotoViewHolder).bind(listData, itemClickListener)
            DROPDOWN -> (holder as FormDropDownViewHolder).bind(listData)
            CHECKBOX -> (holder as FormCheckBoxViewHolder).bind(listData)
            RADIO -> (holder as FormRadioViewHolder).bind(listData)
            DATE_PICKER -> (holder as FormDatePickerViewHolder).bind(listData)
            LOCATION -> (holder as FormLocationViewHolder).bind(listData, itemClickListener)
            RATING -> (holder as FormRatingViewHolder).bind(listData)
            ADDRESS -> (holder as FormAddressViewHolder).bind(listData)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (datas[position].uiSchemaRule.uiWidget) {
            "file", "photo", "camera" -> FILE_PHOTO
            "updown" -> EDIT_TEXT
            "select" -> DROPDOWN
            "checkboxes" -> CHECKBOX
            "radio" -> RADIO
            "fixedlocation", "dynamiclocation" -> LOCATION
            "date", "datetime", "time" -> DATE_PICKER
            "text", "textarea", "phone" -> EDIT_TEXT
            "rating" -> RATING
            "address" -> ADDRESS
            else -> EDIT_TEXT
        }
    }
}
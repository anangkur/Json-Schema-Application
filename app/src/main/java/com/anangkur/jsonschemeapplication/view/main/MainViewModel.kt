package com.anangkur.jsonschemeapplication.view.main

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anangkur.jsonschemeapplication.data.Repository
import com.anangkur.jsonschemeapplication.data.remote.model.Questions
import com.anangkur.jsonschemeapplication.utils.EncodeBased64Binary
import com.anangkur.jsonschemeapplication.utils.FileUtils
import com.anangkur.jsonschemeapplication.utils.ImageUtils
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    companion object {
        const val IMAGE_HEIGHT = 1200f
        const val IMAGE_WIDTH = 900f
    }

    lateinit var repository: Repository

    private val _loadingGetQuestion = MutableLiveData<Boolean>()
    val loadingGetQuestion : LiveData<Boolean> = _loadingGetQuestion

    private val _successGetQuestion = MutableLiveData<Questions>()
    val successGetQuestion: LiveData<Questions> = _successGetQuestion

    private val _errorGetQuestion = MutableLiveData<String>()
    val errorGetQuestion: LiveData<String> = _errorGetQuestion

    fun getQuestions() {
        _loadingGetQuestion.postValue(true)
        viewModelScope.launch {
            try {
                val response = repository.getQuestion()
                _loadingGetQuestion.postValue(false)
                _successGetQuestion.postValue(response)
            } catch (throwable: Throwable) {
                throwable.printStackTrace()
                _errorGetQuestion.postValue(throwable.message)
                _loadingGetQuestion.postValue(false)
            } catch (exception: Exception) {
                exception.printStackTrace()
                _errorGetQuestion.postValue(exception.message)
                _loadingGetQuestion.postValue(false)
            }
        }
    }

    fun convertUriToBase64(context: Context, uri: Uri): String? {
        val imageUtils = ImageUtils(context)
        var bitmap: Bitmap? = null
        val selectedPath = FileUtils.getRealPath(context, uri) ?: return null
        val fileType = context.contentResolver.getType(uri)
        if (!fileType.isNullOrBlank() && fileType.startsWith("image/")) {
            try {
                bitmap = imageUtils.compressImage(uri.toString(), IMAGE_HEIGHT, IMAGE_WIDTH)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return if (bitmap != null) {
            EncodeBased64Binary.bitmapToBase64(bitmap)
        } else EncodeBased64Binary.getBase64FromPath(selectedPath)
    }
}
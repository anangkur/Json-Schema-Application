package com.anangkur.jsonschemeapplication.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anangkur.jsonschemeapplication.data.Repository
import com.anangkur.jsonschemeapplication.data.remote.model.Questions
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

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
}
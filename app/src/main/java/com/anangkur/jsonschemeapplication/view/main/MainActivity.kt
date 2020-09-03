package com.anangkur.jsonschemeapplication.view.main

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.anangkur.jsonschemeapplication.data.DataSource
import com.anangkur.jsonschemeapplication.data.remote.model.Questions
import com.anangkur.jsonschemeapplication.databinding.ActivityMainBinding
import com.anangkur.jsonschemeapplication.utils.extensions.visible
import com.anangkur.jsonschemeapplication.view.dialog.ChooseFilePhotoDialog
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: QuestionAdapter
    private lateinit var viewModel: MainViewModel

    private lateinit var chooseFilePhotoDialog: ChooseFilePhotoDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupAdapter()
        setupFileDialog()
        setupViewModel()
        observeViewModel()

        viewModel.getQuestions()
    }

    private fun setupToolbar(){
        setSupportActionBar(binding.toolbar)
    }

    private fun setupAdapter() {
        adapter = QuestionAdapter() { widget, key -> itemClicked(widget, key)}
        binding.recyclerQuestion.apply {
            adapter = this@MainActivity.adapter
            layoutManager = LinearLayoutManager(this@MainActivity)
            itemAnimator = DefaultItemAnimator()
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.repository = DataSource.getInstance()
    }

    private fun setupFileDialog() {
        chooseFilePhotoDialog = ChooseFilePhotoDialog(this) { _, key, uri ->
            onFileSelected(key, uri)
        }
    }

    private fun observeViewModel() {
        viewModel.successGetQuestion.observe(this, { data ->  successGetQuestion(data)})
        viewModel.loadingGetQuestion.observe(this, { isLoading ->  loadingGetQuestion(isLoading)})
        viewModel.errorGetQuestion.observe(this, { errorMessage ->  errorGetQuestion(errorMessage)})
    }

    private fun loadingGetQuestion(isLoading: Boolean) {
        binding.pbQuestion.visible = isLoading
    }

    private fun successGetQuestion(data: Questions) {
        Log.d("MainActivity", "question: $data")
    }

    private fun errorGetQuestion(errorMessage: String) {
        Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_LONG).show()
    }

    private fun itemClicked(widget: String, key: String) {
        when (widget) {
            "file", "photo", "camera" -> showFileDialog(widget, key)
            "fixedlocation" -> showLocation(key, true)
            "dynamiclocation" -> showLocation(key, false)
        }
    }

    private fun showFileDialog(widget: String, key: String) {
        chooseFilePhotoDialog.show(widget, key)
    }

    private fun showLocation(key: String, isFixLocation: Boolean) {
        Toast.makeText(this, "Under Maintenance!", Toast.LENGTH_SHORT).show()
    }

    private fun onFileSelected(key: String, uri: Uri?) {
        val data = adapter.getData()
        data.find { it.componentName == key }?.let {
            val base64 = if (uri != null) viewModel.convertUriToBase64(this, uri) else null
            if (base64 == null)
                Toast.makeText(this, "File tidak dapat di proses", Toast.LENGTH_SHORT).show()
            else {
                it.value = uri
                it.preview = uri
            }
        }
        adapter.updateItem(key)
    }
}

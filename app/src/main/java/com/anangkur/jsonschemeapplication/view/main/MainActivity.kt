package com.anangkur.jsonschemeapplication.view.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.anangkur.jsonschemeapplication.data.DataSource
import com.anangkur.jsonschemeapplication.data.remote.model.Questions
import com.anangkur.jsonschemeapplication.databinding.ActivityMainBinding
import com.anangkur.jsonschemeapplication.utils.extensions.visible
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: QuestionAdapter
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupAdapter()
        setupViewModel()
        observeViewModel()

        viewModel.getQuestions()
    }

    private fun setupToolbar(){
        setSupportActionBar(binding.toolbar)
    }

    private fun setupAdapter() {
        adapter = QuestionAdapter() { widget, key -> }
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
}

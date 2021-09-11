package com.demirli.a41flashcards.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.demirli.a41flashcards.model.Question

class MainViewModel(application: Application): AndroidViewModel(application){

    private val repository: MainRepository by lazy { MainRepository(application.applicationContext) }

    fun insertQuestion(question: Question) = repository.insertQuestion(question)

    fun deleteAllQuestions() = repository.deleteAllQuestions()

    fun getAllQuestions() = repository.getAllQuestions()

    fun createQuestion() = repository.createQuestion()

}
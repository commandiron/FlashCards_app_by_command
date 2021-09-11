package com.demirli.a41flashcards.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.demirli.a41flashcards.model.Question

@Dao
interface QuizDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertQuestion(question: Question?)

    @Query("DELETE FROM questions")
    fun deleteAllQuestions()

    @Query("SELECT * FROM questions")
    fun gelAllQuestions(): LiveData<List<Question>>


}
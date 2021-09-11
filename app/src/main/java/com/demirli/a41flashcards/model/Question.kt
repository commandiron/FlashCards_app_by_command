package com.demirli.a41flashcards.model

import androidx.room.*

@Entity(tableName = "questions")
data class Question(

    @PrimaryKey(autoGenerate = true)
    var qId: Int = 0,

    var questionFirstDigit: Int,
    var questionSecondDigit: Int,

    var operator: String,

    var optionA: String,

    var optionB: String,

    var optionC: String,

    var optionD: String,

    var correctAnswer: Float = 0f
)
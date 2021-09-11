package com.demirli.a41flashcards.ui

import android.content.Context
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.demirli.a41flashcards.data.QuizDao
import com.demirli.a41flashcards.data.QuizDatabase
import com.demirli.a41flashcards.model.Question
import java.text.DecimalFormat

class MainRepository(context: Context) {

    private val db: QuizDatabase by lazy { QuizDatabase.getInstance(context) }
    private val dao: QuizDao by lazy { db.quizDao() }

    fun insertQuestion(question: Question?){
        InsertAsyncTask(dao).execute(question)
    }

    fun deleteAllQuestions(){
        DeleteAsyncTask(dao).execute()
    }

    fun getAllQuestions(): LiveData<List<Question>>{
        return dao.gelAllQuestions()
    }

    private class InsertAsyncTask(val dao: QuizDao): AsyncTask<Question, Void, Void>() {
        override fun doInBackground(vararg params: Question?): Void? {
            dao.insertQuestion(params[0])
            return null
        }
    }

    private class DeleteAsyncTask(val dao: QuizDao): AsyncTask<Void, Void, Void>() {
        override fun doInBackground(vararg params: Void?): Void? {
            dao.deleteAllQuestions()
            return null
        }
    }

    fun createQuestion(): Question{

        val randomForFirstDigit = (0..10).random()
        val randomForSecondDigit = (1..10).random()

        val randomForOperator = (0..3).random()

        var operator = ""

        when(randomForOperator){
            0 -> operator = "*"
            1 -> operator = "/"
            2 -> operator = "+"
            3 -> operator = "-"
        }

        var correctAnswer = 0f

        when(operator){
            "*" -> {correctAnswer = randomForFirstDigit * randomForSecondDigit.toFloat()}
            "/" -> {correctAnswer = randomForFirstDigit / randomForSecondDigit.toFloat()}
            "+" -> {correctAnswer = randomForFirstDigit + randomForSecondDigit.toFloat()}
            "-" -> {correctAnswer = randomForFirstDigit - randomForSecondDigit.toFloat()}
        }

        val randomForCorrectoption = (0..3).random()

        var optionA = ""
        var optionB = ""
        var optionC = ""
        var optionD = ""

        val decimalFormat = DecimalFormat("0.0")

        when(randomForCorrectoption){
            0 -> optionA = decimalFormat.format(correctAnswer)
            1 -> optionB = decimalFormat.format(correctAnswer)
            2 -> optionC = decimalFormat.format(correctAnswer)
            3 -> optionD = decimalFormat.format(correctAnswer)
        }

        val listOfOptions = arrayListOf<String>(optionA,optionB,optionC,optionD)

        var wrongAnswer1 = decimalFormat.format(correctAnswer+1)
        var wrongAnswer2 = decimalFormat.format(correctAnswer-1)
        var wrongAnswer3 = decimalFormat.format(correctAnswer+2)
        var wrongAnswer4 = decimalFormat.format(correctAnswer-2)
        var wrongAnswer5 = decimalFormat.format(correctAnswer+3)
        var wrongAnswer6 = decimalFormat.format(correctAnswer-3)

        val listOfWrongAnswers = arrayListOf<String>(
            wrongAnswer1,
            wrongAnswer2,
            wrongAnswer3,
            wrongAnswer4,
            wrongAnswer5,
            wrongAnswer6
        )

        listOfWrongAnswers.shuffled()

        for (i in 0 until listOfOptions.size){
            if(listOfOptions[i] == ""){
                listOfOptions.set(i,listOfWrongAnswers[i])
            }
        }

        val question = Question(
            questionFirstDigit = randomForFirstDigit,
            questionSecondDigit = randomForSecondDigit,
            operator = operator,
            optionA = listOfOptions[0],
            optionB = listOfOptions[1],
            optionC = listOfOptions[2],
            optionD = listOfOptions[3],
            correctAnswer = correctAnswer
        )

        return question
    }

}
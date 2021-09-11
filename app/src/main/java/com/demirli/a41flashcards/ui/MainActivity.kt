package com.demirli.a41flashcards.ui

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.GridLayout
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.demirli.a41flashcards.R
import com.demirli.a41flashcards.model.Question
import kotlinx.android.synthetic.main.activity_main.*
import java.text.DecimalFormat

class MainActivity : AppCompatActivity(), CardPagerAdapter.OnOptionClickListener  {

    private lateinit var viewModel: MainViewModel

    private lateinit var quizViewPager: ViewPager

    private var quizSize = 0

    private var numberOfCorrectAnswer = 0
    private var numberOfWrongAnswer = 0

    private var flagForGameEnd = false

    private lateinit var runnableForNextItem: Runnable
    private lateinit var handlerForNextItem: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        runnableForNextItem = Runnable {}
        handlerForNextItem = Handler()

        viewModel =  MainViewModel(application)
        viewModel.deleteAllQuestions()

        quizViewPager = quiz_viewPager

        viewModel.getAllQuestions().observe(this, Observer {
            quizViewPager.adapter = CardPagerAdapter(this,it,this)
        })

        start_btn.setOnClickListener {
            setUiForStartGame()
        }
    }

    fun setUiForStartGame(){
        start_btn.visibility = View.INVISIBLE
        viewModel.deleteAllQuestions()

        try {
            quizSize = quiz_size_ed.text.toString().toInt()

            if(quizSize > 100 ){
                Toast.makeText(this,"Lütfen 101'den küçük bir sayı giriniz", Toast.LENGTH_LONG).show()
            }else if(quizSize < 1){
                Toast.makeText(this,"Lütfen 0'dan büyük bir sayı giriniz", Toast.LENGTH_LONG).show()
            }else{
                repeat(quizSize){
                    viewModel.insertQuestion(viewModel.createQuestion())
                }
                flagForGameEnd = false
                numberOfCorrectAnswer = 0
                numberOfWrongAnswer = 0
            }
        }catch (e:Exception){
        }
    }

    override fun onOptionClick(answerIsCorrect: Boolean) {

        runnableForNextItem = Runnable {
            val currentItem = quizViewPager.currentItem
            quizViewPager.currentItem = currentItem + 1
        }
        handlerForNextItem.postDelayed(runnableForNextItem, 1000)

        if(answerIsCorrect == true){
            numberOfCorrectAnswer++
        }else{
            numberOfWrongAnswer++
        }

        if(quizViewPager.currentItem == quizSize-1){
            Toast.makeText(this,"${numberOfCorrectAnswer + numberOfWrongAnswer} sorudan ${numberOfCorrectAnswer} soruya doğru cevap verdiniz.", Toast.LENGTH_LONG).show()
            start_btn.visibility = View.VISIBLE
        }
    }
}

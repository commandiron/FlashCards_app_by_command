package com.demirli.a41flashcards.ui

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.viewpager.widget.PagerAdapter
import com.demirli.a41flashcards.R
import com.demirli.a41flashcards.model.Question
import java.text.DecimalFormat

class CardPagerAdapter(var context: Context, var questionList: List<Question>, var onOptionClickListener: OnOptionClickListener): PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        val question = questionList[position]
        val view = LayoutInflater.from(context).inflate(R.layout.card_item, container, false)

        val firstDigit =view.findViewById<TextView>(R.id.first_digit)
        val operator =view.findViewById<TextView>(R.id.operator)
        val secondDigit =view.findViewById<TextView>(R.id.second_digit)
        val optionA = view.findViewById<TextView>(R.id.option_a)
        val optionB = view.findViewById<TextView>(R.id.option_b)
        val optionC = view.findViewById<TextView>(R.id.option_c)
        val optionD = view.findViewById<TextView>(R.id.option_d)

        val questionNumber = view.findViewById<TextView>(R.id.question_number)

        val listOfOptionTextView = listOf<TextView>(
            optionA,
            optionB,
            optionC,
            optionD
        )

        firstDigit.text = question.questionFirstDigit.toString()
        operator.text = question.operator
        secondDigit.text = question.questionSecondDigit.toString()
        optionA.text = question.optionA
        optionB.text = question.optionB
        optionC.text = question.optionC
        optionD.text = question.optionD

        questionNumber.setText("${position+1}. soru(${questionList.size})")

        view.tag = question
        container.addView(view)

        val optionA_ll = view.findViewById<LinearLayout>(R.id.option_a_ll)
        val optionB_ll = view.findViewById<LinearLayout>(R.id.option_b_ll)
        val optionC_ll = view.findViewById<LinearLayout>(R.id.option_c_ll)
        val optionD_ll = view.findViewById<LinearLayout>(R.id.option_d_ll)

        val listOfOptionLinearLayout = listOf<LinearLayout>(
            optionA_ll,
            optionB_ll,
            optionC_ll,
            optionD_ll
        )

        val decimalFormat = DecimalFormat("0.0")
        for (i in 0 until listOfOptionLinearLayout.size){
            listOfOptionLinearLayout[i].setOnClickListener {

                lockOptionButtons(listOfOptionLinearLayout)

                if(listOfOptionTextView[i].text.toString().toFloat() == decimalFormat.format(questionList[position].correctAnswer).toFloat()){
                    listOfOptionLinearLayout[i].setBackgroundColor(Color.GREEN)
                    Toast.makeText(context,"Cevap Doğru", Toast.LENGTH_SHORT).show()

                    onOptionClickListener.onOptionClick(true)
                }else{
                    listOfOptionLinearLayout[i].setBackgroundColor(Color.RED)
                    Toast.makeText(context,"Cevap Yanlış", Toast.LENGTH_SHORT).show()

                    onOptionClickListener.onOptionClick(false)
                }
            }
        }

        return view
    }

    override fun getCount(): Int = questionList.size

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) =
        container.removeView(obj as View)

    override fun isViewFromObject(view: View, obj: Any): Boolean = (view == obj)

    fun lockOptionButtons(listOfOptionLinearLayout: List<LinearLayout>){
        for(i in listOfOptionLinearLayout){
            i.isEnabled = false
        }
    }

    interface OnOptionClickListener{
        fun onOptionClick(answerIsCorrect: Boolean)
    }
}
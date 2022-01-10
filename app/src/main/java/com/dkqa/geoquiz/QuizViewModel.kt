package com.dkqa.geoquiz

import android.util.Log
import androidx.lifecycle.ViewModel

private const val TAG = "QuizViewModel"

class QuizViewModel : ViewModel() {

    var currentIndex = 0
    val isCheater: Boolean
        get() = questionBank[currentIndex] in questionCheats

    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americans, true),
        Question(R.string.question_asia, true)
    )

    private var questionCheats = mutableSetOf<Question>()

    val currentQuestionAnswer: Boolean
        get() = questionBank[currentIndex].answer

    val currentQuestionText: Int
        get() = questionBank[currentIndex].textResId

    fun moveToNext() {
        currentIndex = (currentIndex + 1) % questionBank.size
    }

    fun setQuestionCheat() {
        questionCheats.add(questionBank[currentIndex])
    }
}
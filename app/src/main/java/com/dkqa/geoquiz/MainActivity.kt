package com.dkqa.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: Button
    private lateinit var questionTextView: TextView

    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americans, true),
        Question(R.string.question_asia, true)
    )

    private var currentIndex = 0

    private var completedQuestions = mutableMapOf<Int, Boolean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        setContentView(R.layout.activity_main)

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        questionTextView = findViewById(R.id.question_text_view)

        trueButton.setOnClickListener { view: View ->
            completedQuestions[currentIndex] = checkAnswer(true)
            answerButtonsSetEnabled(false)
            checkCompletedAll()
        }

        falseButton.setOnClickListener { view: View ->
            completedQuestions[currentIndex] = checkAnswer(false)
            answerButtonsSetEnabled(false)
            checkCompletedAll()
        }

        nextButton.setOnClickListener {
            currentIndex = (currentIndex + 1) % questionBank.size
            updateQuestion()
            checkCompletedQuestions(currentIndex)
        }

        updateQuestion()
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }

    private fun updateQuestion() {
        val questionTextViewResId = questionBank[currentIndex].textResId
        questionTextView.setText(questionTextViewResId)
    }

    private fun checkAnswer(userAnswer: Boolean): Boolean {
        val correctAnswer = questionBank[currentIndex].answer
        val result: Boolean

        val messageResId = if (userAnswer == correctAnswer) {
            result = true
            R.string.correct_toast
        } else {
            result = false
            R.string.incorrect_toast
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
        return result
    }

    private fun answerButtonsSetEnabled(enabled: Boolean) {
        falseButton.isEnabled = enabled
        trueButton.isEnabled = enabled
    }

    private fun checkCompletedQuestions(index: Int) {
        if (index in completedQuestions.keys) {
            answerButtonsSetEnabled(false)
        } else {
            answerButtonsSetEnabled(true)
        }
    }

    private fun checkCompletedAll() {
        if (completedQuestions.size == questionBank.size) {
            nextButton.isEnabled = false
            val correctCount = completedQuestions.filter { it.value }.size
            val correctPercent = correctCount / (completedQuestions.size.toDouble() / 100)
            questionTextView.text = "Correct $correctPercent%"
        }
    }
}
package com.dkqa.geoquiz

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

private const val EXTRA_ANSWER_SHOWN = "com.dkqa.geoquiz.answer_shown"
private const val EXTRA_ANSWER_IS_TRUE = "com.dkqa.geoquiz.answer_is_true"
private const val EXTRA_CHEATS_COUNT = "com.dkqa.geoquiz.cheats_count"

class CheatActivity : AppCompatActivity() {

    private lateinit var answerTextView: TextView
    private lateinit var showAnswerButton: Button
    private lateinit var cheatsCountTextView: TextView

    private var answerIsTrue = false
    private var cheatsCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheat)

        answerIsTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)
        cheatsCount = intent.getIntExtra(EXTRA_CHEATS_COUNT, 3)
        answerTextView = findViewById(R.id.answer_text_view)
        showAnswerButton = findViewById(R.id.show_answer_button)
        cheatsCountTextView = findViewById(R.id.cheats_count_text_view)

        if (cheatsCount <= 0) {
            showAnswerButton.isEnabled = false
        }

        cheatsCountTextView.text = "$cheatsCount"

        showAnswerButton.setOnClickListener {
            val answerText = when {
                answerIsTrue -> R.string.true_button
                else -> R.string.false_button
            }
            cheatsCount--
            showAnswerButton.isEnabled = false
            cheatsCountTextView.text = "$cheatsCount"
            answerTextView.setText(answerText)
            setAnswerShownResult(true)
        }
    }

    private fun setAnswerShownResult(isAnswerShown: Boolean) {
        val data = Intent().apply {
            putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown)
        }
        setResult(Activity.RESULT_OK, data)
    }

    companion object {
        fun newIntent(packageContext: Context, answerIsTrue: Boolean, cheatsCount: Int): Intent {
            return Intent(packageContext, CheatActivity::class.java).apply {
                putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue)
                putExtra(EXTRA_CHEATS_COUNT, cheatsCount)
            }
        }
    }
}
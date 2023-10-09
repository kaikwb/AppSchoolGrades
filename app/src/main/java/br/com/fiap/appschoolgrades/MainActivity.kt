package br.com.fiap.appschoolgrades

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun calcFinalGrade(view: View) {
        hideKeyboard()

        val grade1 = findViewById<EditText>(R.id.grade1)
        val grade2 = findViewById<EditText>(R.id.grade2)
        val grade3 = findViewById<EditText>(R.id.grade3)
        val grade4 = findViewById<EditText>(R.id.grade4)

        if (grade1.text.isEmpty() or grade2.text.isEmpty() or grade3.text.isEmpty() or grade4.text.isEmpty()) {
            showInvalidGradeMessage()
            return
        }

        val finalGrade = (grade1.text.toString().toDouble() + grade2.text.toString()
            .toDouble() + grade3.text.toString().toDouble() + grade4.text.toString().toDouble()) / 4

        showFinalGrade(finalGrade.toInt())
    }

    fun resetGrades(view: View) {
        hideKeyboard()

        val grade1 = findViewById<EditText>(R.id.grade1)
        val grade2 = findViewById<EditText>(R.id.grade2)
        val grade3 = findViewById<EditText>(R.id.grade3)
        val grade4 = findViewById<EditText>(R.id.grade4)
        val finalGradeTextView = findViewById<TextView>(R.id.finalGradesTextView)
        val finalGradeImageView = findViewById<ImageView>(R.id.finalGradeImageView)

        grade1.text.clear()
        grade2.text.clear()
        grade3.text.clear()
        grade4.text.clear()
        finalGradeTextView.visibility = View.INVISIBLE
        finalGradeImageView.visibility = View.INVISIBLE
    }

    private fun showInvalidGradeMessage() {
        val finalGradeTextView = findViewById<TextView>(R.id.finalGradesTextView)
        val finalGradeImageView = findViewById<ImageView>(R.id.finalGradeImageView)

        finalGradeTextView.visibility = View.INVISIBLE
        finalGradeImageView.visibility = View.INVISIBLE

        Toast.makeText(this, getString(R.string.toast_invalid_grade), Toast.LENGTH_LONG).show()
    }

    private fun showFinalGrade(finalGrade: Int) {
        val finalGradeTextView = findViewById<TextView>(R.id.finalGradesTextView)
        val finalGradeImageView = findViewById<ImageView>(R.id.finalGradeImageView)

        finalGradeTextView.text = getString(R.string.message_final_grade) + finalGrade.toString()

        if (finalGrade >= resources.getInteger(R.integer.grade_to_approve)) {
            finalGradeImageView.setImageResource(R.drawable.approved)
            finalGradeImageView.contentDescription = getString(R.string.message_approved)
        } else {
            finalGradeImageView.setImageResource(R.drawable.reproved)
            finalGradeImageView.contentDescription = getString(R.string.message_reproved)
        }

        finalGradeTextView.visibility = View.VISIBLE
        finalGradeImageView.visibility = View.VISIBLE
    }

    fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    fun AppCompatActivity.hideKeyboard() {
        hideKeyboard(currentFocus ?: View(this))
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
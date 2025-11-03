package com.example.caculator

import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout

class MainActivity : AppCompatActivity() {

    private lateinit var firstNameText: EditText
    private lateinit var lastNameText: EditText
    private lateinit var birthdayText: EditText
    private lateinit var addressText: EditText
    private lateinit var emailText: EditText

    private lateinit var genderGroup: RadioGroup
    private lateinit var genderLabel: TextView
    private lateinit var checkBox: CheckBox

    private lateinit var birthdaySelectButton: Button
    private lateinit var registerButton: Button

    private lateinit var rootLayout: ConstraintLayout
    private lateinit var calendarView: CalendarView

    private lateinit var normalBackground: Drawable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main2)

        rootLayout = findViewById(R.id.main)

        firstNameText = findViewById(R.id.firstNameText)
        lastNameText = findViewById(R.id.lastNameText)
        birthdayText = findViewById(R.id.birthdayText)
        addressText = findViewById(R.id.addressText)
        emailText = findViewById(R.id.emailText)

        genderGroup = findViewById(R.id.genderGroup)
        genderLabel = findViewById(R.id.genderLabel)
        checkBox = findViewById(R.id.checkBox)

        birthdaySelectButton = findViewById(R.id.birthdaySelectButton)
        registerButton = findViewById(R.id.registerButton)

        normalBackground = firstNameText.background

        calendarView = CalendarView(this)
        calendarView.id = View.generateViewId()
        calendarView.visibility = View.GONE

        val params = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.MATCH_CONSTRAINT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT
        )
        params.startToStart = ConstraintLayout.LayoutParams.PARENT_ID
        params.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
        params.topToBottom = R.id.birthdayText
        rootLayout.addView(calendarView, params)

        birthdaySelectButton.setOnClickListener {
            calendarView.visibility =
                if (calendarView.visibility == View.GONE) View.VISIBLE else View.GONE
        }

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val date = String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year)
            birthdayText.setText(date)
            calendarView.visibility = View.GONE
        }

        registerButton.setOnClickListener {
            validateForm()
        }
    }

    private fun resetErrorStyles() {
        firstNameText.background = normalBackground
        lastNameText.background = normalBackground
        birthdayText.background = normalBackground
        addressText.background = normalBackground
        emailText.background = normalBackground

        genderLabel.setTextColor(Color.BLACK)
        checkBox.setTextColor(Color.BLACK)
    }

    private fun validateForm() {
        resetErrorStyles()
        var isValid = true

        if (firstNameText.text.toString().trim().isEmpty()) {
            firstNameText.setBackgroundColor(Color.RED)
            isValid = false
        }

        if (lastNameText.text.toString().trim().isEmpty()) {
            lastNameText.setBackgroundColor(Color.RED)
            isValid = false
        }

        if (birthdayText.text.toString().trim().isEmpty()) {
            birthdayText.setBackgroundColor(Color.RED)
            isValid = false
        }

        if (addressText.text.toString().trim().isEmpty()) {
            addressText.setBackgroundColor(Color.RED)
            isValid = false
        }

        if (emailText.text.toString().trim().isEmpty()) {
            emailText.setBackgroundColor(Color.RED)
            isValid = false
        }

        if (genderGroup.checkedRadioButtonId == -1) {
            genderLabel.setTextColor(Color.RED)
            isValid = false
        }

        if (!checkBox.isChecked) {
            checkBox.setTextColor(Color.RED)
            isValid = false
        }

        if (isValid) {
            Toast.makeText(this, "Register success!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Please fill all required fields.", Toast.LENGTH_SHORT).show()
        }
    }
}
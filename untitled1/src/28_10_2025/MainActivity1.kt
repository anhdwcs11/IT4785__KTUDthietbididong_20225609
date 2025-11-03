package com.example.caculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity1 : AppCompatActivity() {

    private lateinit var txtHistory: TextView
    private lateinit var txtDisplay: TextView

    private var operand1: Long? = null
    private var pendingOp: Char? = null
    private var currentText: String = "0"
    private var isNewOperand: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txtHistory = findViewById(R.id.txtHistory)
        txtDisplay = findViewById(R.id.txtDisplay)

        fun setDigit(buttonId: Int, d: String) {
            findViewById<Button>(buttonId).setOnClickListener { addDigit(d) }
        }

        setDigit(R.id.btn0, "0")
        setDigit(R.id.btn1, "1")
        setDigit(R.id.btn2, "2")
        setDigit(R.id.btn3, "3")
        setDigit(R.id.btn4, "4")
        setDigit(R.id.btn5, "5")
        setDigit(R.id.btn6, "6")
        setDigit(R.id.btn7, "7")
        setDigit(R.id.btn8, "8")
        setDigit(R.id.btn9, "9")

        findViewById<Button>(R.id.btnAdd).setOnClickListener { setOperator('+') }
        findViewById<Button>(R.id.btnSub).setOnClickListener { setOperator('-') }
        findViewById<Button>(R.id.btnMul).setOnClickListener { setOperator('*') }
        findViewById<Button>(R.id.btnDiv).setOnClickListener { setOperator('/') }

        findViewById<Button>(R.id.btnEqual).setOnClickListener { calculateResult() }
        findViewById<Button>(R.id.btnC).setOnClickListener { clearAll() }
        findViewById<Button>(R.id.btnCE).setOnClickListener { clearEntry() }
        findViewById<Button>(R.id.btnBS).setOnClickListener { backspace() }
        findViewById<Button>(R.id.btnSign).setOnClickListener { toggleSign() }

        findViewById<Button>(R.id.btnDot).setOnClickListener {
            Toast.makeText(this, "Chỉ hỗ trợ số nguyên", Toast.LENGTH_SHORT).show()
        }

        updateDisplay()
        updateHistory("")
    }

    private fun addDigit(d: String) {
        if (isNewOperand || currentText == "0") currentText = d
        else currentText += d
        isNewOperand = false
        updateDisplay()
    }

    private fun setOperator(op: Char) {
        val value = currentText.toLong()
        if (operand1 == null) {
            operand1 = value
        } else if (!isNewOperand && pendingOp != null) {
            operand1 = applyOp(operand1!!, value, pendingOp!!)
        }
        pendingOp = op
        isNewOperand = true
        currentText = operand1.toString()
        updateDisplay()
        updateHistory("${operand1} ${opToSymbol(op)}")
    }

    private fun calculateResult() {
        val op = pendingOp ?: return
        val a = operand1 ?: return
        val b = currentText.toLong()
        val result = applyOp(a, b, op)
        txtHistory.text = "${a} ${opToSymbol(op)} ${b} ="
        operand1 = null
        pendingOp = null
        currentText = result.toString()
        isNewOperand = true
        updateDisplay()
    }

    private fun applyOp(a: Long, b: Long, op: Char): Long {
        return when (op) {
            '+' -> a + b
            '-' -> a - b
            '*' -> a * b
            '/' -> {
                if (b == 0L) {
                    Toast.makeText(this, "Không thể chia cho 0", Toast.LENGTH_SHORT).show()
                    0L
                } else a / b
            }
            else -> b
        }
    }

    private fun clearAll() {
        operand1 = null
        pendingOp = null
        currentText = "0"
        isNewOperand = true
        updateDisplay()
        updateHistory("")
    }

    private fun clearEntry() {
        currentText = "0"
        isNewOperand = true
        updateDisplay()
    }

    private fun backspace() {
        if (isNewOperand) return
        currentText = if (currentText.length <= 1 || (currentText.length == 2 && currentText.startsWith("-"))) {
            isNewOperand = true
            "0"
        } else {
            currentText.dropLast(1)
        }
        updateDisplay()
    }

    private fun toggleSign() {
        if (currentText == "0") return
        currentText = if (currentText.startsWith("-")) currentText.substring(1) else "-$currentText"
        updateDisplay()
    }

    private fun opToSymbol(op: Char): String = when (op) {
        '*' -> "*"
        '/' -> "÷"
        else -> op.toString()
    }

    private fun updateDisplay() {
        txtDisplay.text = currentText
    }

    private fun updateHistory(s: String) {
        txtHistory.text = s
    }
}

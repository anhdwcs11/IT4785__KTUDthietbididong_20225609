package com.example.it4785_11_10_2025

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ArrayAdapter
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import com.example.it4785_11_10_2025.databinding.ActivityIntegerListBinding
import kotlin.math.sqrt

class IntegerListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityIntegerListBinding
    private val results = ArrayList<Int>()
    private lateinit var adapter: ArrayAdapter<Int>

    // Gom toàn bộ RadioButton để chủ động bật/tắt
    private val radios by lazy {
        listOf(
            binding.rbOdd, binding.rbEven, binding.rbPrime,
            binding.rbPerfect, binding.rbSquare, binding.rbFibonacci
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntegerListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, results)
        binding.lvResults.adapter = adapter

        setupListeners()
        updateList()
    }

    private fun setupListeners() {
        // Cập nhật ngay khi người dùng nhập/chỉnh sửa N
        binding.etNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, st: Int, c: Int, a: Int) {}
            override fun onTextChanged(s: CharSequence?, st: Int, b: Int, c: Int) = updateList()
            override fun afterTextChanged(s: Editable?) {}
        })

        // Tự lập trình "chỉ 1 lựa chọn" cho 6 RadioButton
        listOf(
            binding.rbOdd, binding.rbEven, binding.rbPrime,
            binding.rbPerfect, binding.rbSquare, binding.rbFibonacci
        ).forEach { rb ->
            rb.setOnClickListener { clicked ->
                // tắt tất cả
                radios.forEach { it.isChecked = false }
                // bật lại cái vừa bấm
                (clicked as RadioButton).isChecked = true
                updateList()
            }
        }
    }

    private fun updateList() {
        results.clear()

        val n = binding.etNumber.text.toString().toIntOrNull() ?: 0
        if (n <= 0) {
            toggleEmpty(true)
            adapter.notifyDataSetChanged()
            return
        }

        if (binding.rbFibonacci.isChecked) {
            // Dãy Fibonacci < n (bắt đầu 0, 1, 1, 2, 3, ...)
            var a = 0
            var b = 1
            while (a < n) {
                results.add(a)
                val next = a + b
                a = b
                b = next
            }
        } else {
            // Dò từ 0..(n-1)
            for (i in 0 until n) {
                val ok = when {
                    binding.rbOdd.isChecked -> isOdd(i)
                    binding.rbEven.isChecked -> isEven(i)
                    binding.rbPrime.isChecked -> isPrime(i)
                    binding.rbPerfect.isChecked -> isPerfect(i)
                    binding.rbSquare.isChecked -> isSquare(i)
                    else -> false
                }
                if (ok) results.add(i)
            }
        }

        toggleEmpty(results.isEmpty())
        adapter.notifyDataSetChanged()
    }

    private fun toggleEmpty(empty: Boolean) {
        binding.lvResults.visibility = if (empty) View.GONE else View.VISIBLE
        binding.tvEmpty.visibility = if (empty) View.VISIBLE else View.GONE
    }

    // ===== Các hàm kiểm tra =====
    private fun isOdd(x: Int) = x % 2 != 0
    private fun isEven(x: Int) = x % 2 == 0

    private fun isPrime(x: Int): Boolean {
        if (x < 2) return false
        var d = 2
        val r = sqrt(x.toDouble()).toInt()
        while (d <= r) {
            if (x % d == 0) return false
            d++
        }
        return true
    }

    private fun isPerfect(x: Int): Boolean {
        if (x <= 1) return false
        var sum = 1
        val r = sqrt(x.toDouble()).toInt()
        for (d in 2..r) {
            if (x % d == 0) {
                sum += d
                val other = x / d
                if (other != d) sum += other
            }
        }
        return sum == x
    }

    private fun isSquare(x: Int): Boolean {
        if (x < 0) return false
        val r = sqrt(x.toDouble()).toInt()
        return r * r == x
    }
}

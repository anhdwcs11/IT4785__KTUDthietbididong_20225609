package com.example.it4785_11_10_2025

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.it4785_11_10_2025.databinding.ActivityCurrencyBinding
import java.text.DecimalFormat

class CurrencyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCurrencyBinding
    private var isUpdating = false

    // Tỷ giá cố định (chuẩn hóa theo 1 USD)
    private val exchangeRates = mapOf(
        "USD" to 1.0,       // Đơn vị chuẩn
        "VND" to 25454.50,
        "EUR" to 0.92,
        "JPY" to 157.50,
        "GBP" to 0.79,
        "AUD" to 1.50,
        "CAD" to 1.37,
        "CHF" to 0.89,
        "CNY" to 7.25,
        "HKD" to 7.81,
        "KRW" to 1380.00
    )

    // Hiển thị có group hàng nghìn, tối đa 6 chữ số thập phân
    private val decimalFormat = DecimalFormat("#,##0.######")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCurrencyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupSpinners()
        setupTextWatchers()

        // Mặc định: From = USD, To = VND, Amount = 1
        val currencies = exchangeRates.keys.toList()
        binding.spinnerFrom.setSelection(currencies.indexOf("USD"))
        binding.spinnerTo.setSelection(currencies.indexOf("VND"))
        binding.editTextFrom.setText("1")
    }

    private fun setupSpinners() {
        // Giữ đúng thứ tự keys trong map (như trên)
        val currencies = exchangeRates.keys.toList()
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, currencies)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerFrom.adapter = adapter
        binding.spinnerTo.adapter = adapter

        val onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                // Mỗi lần đổi loại tiền, tính lại dựa trên ô From
                performConversion(fromIsSource = true)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        binding.spinnerFrom.onItemSelectedListener = onItemSelectedListener
        binding.spinnerTo.onItemSelectedListener = onItemSelectedListener
    }

    private fun setupTextWatchers() {
        // Gõ ở ô From -> cập nhật ô To
        binding.editTextFrom.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!isUpdating && binding.editTextFrom.isFocused) {
                    performConversion(fromIsSource = true)
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        // Gõ ở ô To -> cập nhật ngược về ô From
        binding.editTextTo.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!isUpdating && binding.editTextTo.isFocused) {
                    performConversion(fromIsSource = false)
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun performConversion(fromIsSource: Boolean) {
        isUpdating = true
        try {
            val fromCurrency = binding.spinnerFrom.selectedItem?.toString() ?: return
            val toCurrency = binding.spinnerTo.selectedItem?.toString() ?: return

            val rateFrom = exchangeRates[fromCurrency] ?: return
            val rateTo = exchangeRates[toCurrency] ?: return

            val source = if (fromIsSource) binding.editTextFrom else binding.editTextTo
            val target = if (fromIsSource) binding.editTextTo else binding.editTextFrom

            val txt = source.text?.toString()?.trim().orEmpty()

            if (txt.isEmpty() || txt == "." || txt == ",") {
                target.setText("")
                isUpdating = false
                return
            }

            val amount = txt.replace(",", "").toDouble()

            // Đổi tiền qua "chuẩn" USD: value_in_target = (amount / rate_source) * rate_target
            val result = if (fromIsSource) {
                (amount / rateFrom) * rateTo
            } else {
                (amount / rateTo) * rateFrom
            }

            target.setText(decimalFormat.format(result))
        } catch (_: Exception) {
            if (fromIsSource) binding.editTextTo.setText("") else binding.editTextFrom.setText("")
        }
        isUpdating = false
    }
}

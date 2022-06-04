package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {

    private var display: TextView? = null
    var lastNumeric: Boolean = false
    var lastDot: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        display = findViewById(R.id.display)
    }

    fun onDigit(view: View) {
        display?.append((view as Button).text)
        lastNumeric = true
        lastDot = false
    }

    fun onClear(view: View) {
        display?.text = ""
    }

    fun onDecimalPoint(view: View) {
        if (lastNumeric && !lastDot) {
            display?.append(".")
            lastNumeric = false
            lastDot = true
        }
    }

    fun onEqual(view: View) {
        if (lastNumeric) {
            var displayValue = display?.text.toString()
            var prefix = ""
            try {
                if (displayValue.startsWith("-")) {
                    prefix = "-"
                    displayValue = displayValue.substring(1)
                }
                if (displayValue.contains("-")) {
                    val splitValue = displayValue.split("-")
                    var one = splitValue[0]
                    var two = splitValue[1]
                    if (prefix.isNotEmpty()) {
                        one = prefix + one
                    }
                    display?.text = removeZeroAfterDot((one.toDouble() - two.toDouble()).toString())
                } else if (displayValue.contains("+")) {
                    val splitValue = displayValue.split("+")
                    var one = splitValue[0]
                    var two = splitValue[1]
                    if (prefix.isNotEmpty()) {
                        one = prefix + one
                    }
                    display?.text = removeZeroAfterDot((one.toDouble() + two.toDouble()).toString())
                } else if (displayValue.contains("/")) {
                    val splitValue = displayValue.split("/")
                    var one = splitValue[0]
                    var two = splitValue[1]
                    if (prefix.isNotEmpty()) {
                        one = prefix + one
                    }
                    display?.text = removeZeroAfterDot((one.toDouble() / two.toDouble()).toString())
                } else if (displayValue.contains("*")) {
                    val splitValue = displayValue.split("*")
                    var one = splitValue[0]
                    var two = splitValue[1]
                    if (prefix.isNotEmpty()) {
                        one = prefix + one
                    }
                    display?.text = removeZeroAfterDot((one.toDouble() * two.toDouble()).toString())
                }
            } catch (e: ArithmeticException) {
                e.printStackTrace()
            }
        }
    }

    fun onOperator(view: View) {
        display?.text?.let {
            if (lastNumeric && !isOperatorAdded(it.toString())) {
                display?.append((view as Button).text)
                lastNumeric = false
                lastDot = false
            }
        }
    }

    private fun removeZeroAfterDot(result: String) : String {
        var value = result
        if (result.contains(".0"))
            value = result.substring(0, result.length - 2)

        return value
    }

    private fun isOperatorAdded(value: String) : Boolean {
        return if (value.startsWith("-")) {
            false
        } else {
            value.contains("+")
                    || value.contains("-")
                    || value.contains("/")
                    || value.contains("*")
        }
    }
}
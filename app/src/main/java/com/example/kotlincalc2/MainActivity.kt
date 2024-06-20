package com.example.kotlincalc2

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

import java.util.regex.Pattern
import java.util.regex.Matcher

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val textView = findViewById<TextView>(R.id.myTextView)

        findViewById<Button>(R.id.button_0).setOnClickListener { textView.text = textView.text.toString() + "0" }
        findViewById<Button>(R.id.button_1).setOnClickListener { textView.text = textView.text.toString() + "1" }
        findViewById<Button>(R.id.button_2).setOnClickListener { textView.text = textView.text.toString() + "2" }
        findViewById<Button>(R.id.button_3).setOnClickListener { textView.text = textView.text.toString() + "3" }
        findViewById<Button>(R.id.button_4).setOnClickListener { textView.text = textView.text.toString() + "4" }
        findViewById<Button>(R.id.button_5).setOnClickListener { textView.text = textView.text.toString() + "5" }
        findViewById<Button>(R.id.button_6).setOnClickListener { textView.text = textView.text.toString() + "6" }
        findViewById<Button>(R.id.button_7).setOnClickListener { textView.text = textView.text.toString() + "7" }
        findViewById<Button>(R.id.button_8).setOnClickListener { textView.text = textView.text.toString() + "8" }
        findViewById<Button>(R.id.button_9).setOnClickListener { textView.text = textView.text.toString() + "9" }

        findViewById<Button>(R.id.button_Plus).setOnClickListener { textView.text = textView.text.toString() + "+" }
        findViewById<Button>(R.id.button_Minus).setOnClickListener { textView.text = textView.text.toString() + "-" }
        findViewById<Button>(R.id.button_Prod).setOnClickListener { textView.text = textView.text.toString() + "*" }
        findViewById<Button>(R.id.button_Div).setOnClickListener { textView.text = textView.text.toString() + "/" }
        findViewById<Button>(R.id.button_Mod).setOnClickListener {
            var s= textView.text.toString()
            if (s.length > 0) {
                s = s.substring(0, s.length - 1)
            }
            textView.text = s
        }
        findViewById<Button>(R.id.button_Dot).setOnClickListener { textView.text = textView.text.toString() + "." }
        findViewById<Button>(R.id.button_Clear).setOnClickListener { textView.text = "" }

        fun replaceMulAndDiv(s: String): String {
            val pattern = Pattern.compile("(-?\\d+\\.\\d+|-?\\d+)([*/])(-?\\d+\\.\\d+|-?\\d+)")
            val matcher = pattern.matcher(s)
            if (matcher.find()) {
                val n1 = matcher.group(1).toDouble()
                val n2 = matcher.group(3).toDouble()
                val result = if (matcher.group(2) == "*") {
                    String.format("%.5f", n1 * n2)
                } else {
                    String.format("%.5f", n1 / n2)
                }
                return s.replace(matcher.group(0), result)
            } else {
                return s
            }
        }

        fun replaceSumAndDiff(s: String): String {
            val pattern = Pattern.compile("(-?\\d+\\.\\d+|-?\\d+)([+-])(-?\\d+\\.\\d+|-?\\d+)")
            val matcher = pattern.matcher(s)
            if (matcher.find()) {
                val n1 = matcher.group(1).toDouble()
                val n2 = matcher.group(3).toDouble()
                val result = if (matcher.group(2) == "+") {
                    String.format("%.5f", n1 + n2)
                } else {
                    String.format("%.5f", n1 - n2)
                }
                return s.replace(matcher.group(0), result)
            } else {
                return s
            }
        }


        findViewById<Button>(R.id.button_Equal).setOnClickListener{
            var s = textView.text.toString()
            var k = 0
            while ("*" in s || "/" in s) {
                s = s.replace(",", ".")
                s = replaceMulAndDiv(s)
                s = s.replace(",", ".")
                k++
                if (k >100)
                    break
            }
            //Log.d("DEBUG", s)
            k = 0
            while ("+" in s || "-" in s) {
                s = s.replace(",", ".")
                s = replaceSumAndDiff(s)
                s = s.replace(",", ".")
                k++
                if (k >100)
                    break
            }
            s = s.replace(",", ".")
            s = s.replace(".00000", "")
            s = s.replace(",00000", "")
            if (s.endsWith("0000") and (s.contains(".") or s.contains(",")))
                s = s.substring(0, s.length - 4)
            if (s.endsWith("000") and (s.contains(".") or s.contains(",")))
                s = s.substring(0, s.length - 3)
            if (s.endsWith("00") and (s.contains(".") or s.contains(",")))
                s = s.substring(0, s.length - 2)
            if (s.endsWith("0") and (s.contains(".") or s.contains(",")))
                s = s.substring(0, s.length - 1)

            textView.text = s
        }
    }
}
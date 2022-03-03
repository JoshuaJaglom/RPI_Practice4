package com.example.rpi_practice4

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MotionEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.rpi_practice4.databinding.ActivityMainBinding
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var clickCounter: Float = 1.0F

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.inspect?.setOnClickListener {
            when {
                (binding.km?.text?.trim()?.isNotEmpty() == true) and
                        (binding.m?.text?.trim()?.isNotEmpty() == true) and
                        (binding.dm?.text?.trim()?.isNotEmpty() == true) and
                        (binding.sm?.text?.trim()?.isNotEmpty() == true) and
                        (binding.mm?.text?.trim()?.isNotEmpty() == true) -> {
                    val kmIntoMm = binding.km?.text.toString().toDouble().kmToMm()
                    val mIntoMm = binding.m?.text.toString().toDouble().mToMm()
                    val dmIntoMm = binding.dm?.text.toString().toDouble().dmToMm()
                    val smIntoMm = binding.sm?.text.toString().toDouble().smToMm()
                    val mm = binding.mm?.text.toString().toDouble()
                    when {
                        ((kmIntoMm == mIntoMm) and (mIntoMm == dmIntoMm) and
                                (dmIntoMm == smIntoMm) and (smIntoMm == mm)) -> {
                            binding.result?.text = getString(R.string.good)
                            binding.result?.setTextColor(resources.getColor(R.color.blue))
                            binding.answer?.setImageResource(R.drawable.cool)
                        }
                        else -> {
                            binding.result?.text = getString(R.string.bad)
                            binding.result?.setTextColor(resources.getColor(R.color.red))
                            binding.answer?.setImageResource(R.drawable.bad)
                        }
                    }
                    clickCounter = 1.0F
                    binding.answer?.alpha = clickCounter
                }
                else -> {
                    Toast.makeText(
                        applicationContext, "Не все поля заполнены",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        }

        binding.answer?.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_UP -> {
                    if ((clickCounter * 10F).roundToInt() / 10F == 0.1F) {
                        clickCounter = 1.0F
                        binding.answer?.alpha = clickCounter
                    } else {
                        clickCounter -= 0.1F
                        binding.answer?.alpha = clickCounter
                    }
                }
            }
            true
        }
        setContentView(binding.root)
    }

    private fun Double.kmToMm() = this * 1000000

    private fun Double.mToMm() = this * 1000

    private fun Double.dmToMm() = this * 100

    private fun Double.smToMm() = this * 10
}

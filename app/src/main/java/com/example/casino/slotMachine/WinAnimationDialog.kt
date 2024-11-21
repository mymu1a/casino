package com.example.casino.slotMachine

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.casino.R

class WinAnimationDialog : DialogFragment() {

    private lateinit var closeButton: Button
    private lateinit var countTextView: TextView
    private var count = 0
    private val targetScore = 300
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_win_animation_dialog, container, false)

        countTextView = view.findViewById(R.id.count_text)
        closeButton = view.findViewById(R.id.close_button)

        // Изначально скрываем кнопку закрытия
        closeButton.visibility = View.GONE
        closeButton.setOnClickListener { dismiss() }

        startCountAnimation()

        return view
    }

    private fun startCountAnimation() {
        handler.postDelayed(object : Runnable {
            override fun run() {
                count++
                countTextView.text = count.toString()

                if (count < targetScore) {
                    handler.postDelayed(this, 20) // Скорость увеличения
                } else {
                    // Показываем кнопку после достижения целевого значения
                    closeButton.visibility = View.VISIBLE
                }
            }
        }, 20)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacksAndMessages(null) // Очищаем коллбэки, чтобы избежать утечек памяти
    }
}

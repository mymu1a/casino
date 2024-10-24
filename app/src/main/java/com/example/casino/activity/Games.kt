package com.example.casino.activity

import GridItemDecoration
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.casino.GamesAdapter
import com.example.casino.R

class Games : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_games)

        // Получаем RecyclerView и устанавливаем адаптер
        val gameRecyclerView: RecyclerView = findViewById(R.id.game_recycler_view)

        // Список изображений игр
        val gameImages = listOf(
            R.drawable.keno,
            R.drawable.baccarat,
            R.drawable.craps,
            R.drawable.poker,
            R.drawable.roulette,
            R.drawable.blackjack,
            R.drawable.slot_machine,
            R.drawable.slot_machine2

        )

        // Устанавливаем адаптер с GridLayoutManager для адаптивного количества колонок
        setupRecyclerView(gameRecyclerView, gameImages)
    }

    private fun setupRecyclerView(recyclerView: RecyclerView, gameImages: List<Int>) {
        // Получаем ширину экрана
        val displayMetrics = resources.displayMetrics
        val screenWidth = displayMetrics.widthPixels

        // Указываем ширину элемента (например, 120dp) в пикселях
        val itemWidth = (120 * displayMetrics.density).toInt()

        // Рассчитываем количество колонок на основе ширины экрана
        val spanCount = screenWidth / itemWidth

        // Устанавливаем GridLayoutManager
        recyclerView.layoutManager = GridLayoutManager(this, spanCount)

        // Добавляем отступы между элементами
        val spacing = (8 * displayMetrics.density).toInt() // 8dp отступ
        recyclerView.addItemDecoration(GridItemDecoration(spacing))

        // Устанавливаем адаптер
        recyclerView.adapter = GamesAdapter(gameImages) { imageResId ->
            // Действие по нажатию на кнопку игры
        }
    }

    fun toMainActivity(v: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    fun toSettings(v: View) {
        val intent = Intent(this, Settings::class.java)
        startActivity(intent)
    }
}

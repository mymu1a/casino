package com.example.casino

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class GridItemDecoration(private val space: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        // Отступы для всех элементов
        outRect.left = space
        outRect.right = space
        outRect.bottom = space

        // Убираем отступ сверху для первого ряда
        if (parent.getChildAdapterPosition(view) < 3) { // Здесь 3 - это количество колонок
            outRect.top = space
        }
    }
}

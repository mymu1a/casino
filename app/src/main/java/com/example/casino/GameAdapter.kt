package com.example.casino

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageButton
import androidx.recyclerview.widget.RecyclerView

class GamesAdapter(private val gameImages: List<Int>, private val onClick: (Int) -> Unit) :
    RecyclerView.Adapter<GamesAdapter.GameViewHolder>() {

    class GameViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val gameImageButton: AppCompatImageButton = view.findViewById(R.id.game_image_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.game_item, parent, false)
        return GameViewHolder(view)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val imageResId = gameImages[position]
        holder.gameImageButton.setImageResource(imageResId)
        holder.gameImageButton.setOnClickListener {
            onClick(imageResId)
        }
    }

    override fun getItemCount(): Int = gameImages.size
}

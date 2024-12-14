import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageButton
import androidx.recyclerview.widget.RecyclerView
import com.example.casino.R
import com.example.casino.baccarat.BaccaratActivity
import com.example.casino.blackJack.BlackJackActivity
import com.example.casino.roulette.RouletteActivity
import com.example.casino.slotMachine.*

class GamesAdapter(
    private val gameImages: List<Int>,
    private val context: Context
) : RecyclerView.Adapter<GamesAdapter.GameViewHolder>() {

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
            val intent = when (imageResId) {
                R.drawable.slot_machine -> Intent(context, SlotMachine::class.java)
                R.drawable.blackjack -> Intent(context, BlackJackActivity::class.java)
                R.drawable.roulette -> Intent(context, RouletteActivity::class.java)
                R.drawable.baccarat -> Intent(context, BaccaratActivity::class.java)

                else -> {
                    Toast.makeText(context, "Game not available", Toast.LENGTH_SHORT).show()
                    null
                }
            }
            intent?.let { context.startActivity(it) }
        }
    }

    override fun getItemCount(): Int = gameImages.size
}
package fr.lololoulou.chucknorrisapp

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class JokeTouchHelper(
    private val adapter: JokeAdapter
) : ItemTouchHelper(
    object : ItemTouchHelper.SimpleCallback(
        UP or DOWN,
        LEFT or RIGHT
    ) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            adapter.swapJoke(viewHolder.bindingAdapterPosition, target.bindingAdapterPosition)
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
            adapter.removeJoke(viewHolder.bindingAdapterPosition)

        }
    }
)

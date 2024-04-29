package com.example.instruisto.util

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.instruisto.R
import com.example.instruisto.databinding.DeckItemBinding
import com.example.instruisto.model.Deck

class DecksListAdapter(val decks: List<Deck>, val nav: (Deck) -> Unit): RecyclerView.Adapter<DecksListAdapter.ViewHolder>() {
    class ViewHolder(val binding: DeckItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: Deck, nav: (Deck) -> Unit) {
            binding.card.setOnClickListener {
                val TAG = "aaa"
                Log.e(TAG, "bind: $item")
                nav(item)
            }
            binding.deckName.text = item.name
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DeckItemBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding)
    }
    override fun getItemCount() = decks.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(decks[position], nav)
}
package com.example.instruisto.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.instruisto.databinding.LayoutFlashcardInListBinding
import com.example.instruisto.model.Flashcard

class FlashcardListAdapter(val nav: (Flashcard) -> Unit) : ListAdapter<Flashcard, FlashcardListAdapter.ViewHolder>(DIFF){
    class ViewHolder(val binding: LayoutFlashcardInListBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: Flashcard, nav: (Flashcard) -> Unit){
            binding.apply {
                root.setOnClickListener {
                    nav(item)
                }
                cardFront.text = item.front
                cardBack.text = item.back
                // TODO: add cardImage.setImage
            }
        }
    }
    object DIFF : DiffUtil.ItemCallback<Flashcard>(){
        override fun areItemsTheSame(oldItem: Flashcard, newItem: Flashcard) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Flashcard, newItem: Flashcard) = oldItem.id == newItem.id
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LayoutFlashcardInListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), nav)
    }
}
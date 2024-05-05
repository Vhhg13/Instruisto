package com.example.instruisto.ui.lessonlist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.instruisto.R
import com.example.instruisto.databinding.LayoutLessonCardBinding
import com.example.instruisto.model.Lesson


class LessonsListAdapter(val data: List<Lesson>, val ctx: Context, val nav: (Int) -> Unit) : RecyclerView.Adapter<LessonsListAdapter.ViewHolder>() {
    class ViewHolder(val binding: LayoutLessonCardBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(ctx: Context, item: Lesson, isLast: Boolean, nav: (Int) -> Unit) =
            binding.apply {
                practiceCard.setOnClickListener {
                    nav(item.id)
                }
                lessonNumber.text = ctx.getString(R.string.word_lesson_number_x, item.number)
                tv.text = ctx.getString(R.string.word_practice)
                tv2.text = ctx.getString(R.string.word_grammar)
                if(isLast) divider.visibility = View.INVISIBLE
                else divider.visibility = View.VISIBLE
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LayoutLessonCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(ctx, data[position], position==data.size-1, nav)
    }
    override fun getItemCount() = data.size
}
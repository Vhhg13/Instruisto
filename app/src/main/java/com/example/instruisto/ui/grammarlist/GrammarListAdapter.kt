package com.example.instruisto.ui.grammarlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.instruisto.R
import com.example.instruisto.model.Lesson

typealias GrammarPoint = Lesson.GrammarPoint

class GrammarListAdapter(val nav: (Long) -> Unit): ListAdapter<GrammarPoint, GrammarListAdapter.ViewHolder>(DIFF) {
    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        val textView: TextView = view.findViewById(R.id.point_name)
    }
    object DIFF : DiffUtil.ItemCallback<GrammarPoint>(){
        override fun areItemsTheSame(oldItem: GrammarPoint, newItem: GrammarPoint) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: GrammarPoint, newItem: GrammarPoint) = oldItem.id == newItem.id
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_grammar_point, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = getItem(position).name
        holder.view.setOnClickListener {
            nav(getItem(position).id)
        }
    }
}
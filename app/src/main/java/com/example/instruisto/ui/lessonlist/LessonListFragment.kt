package com.example.instruisto.ui.lessonlist

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.instruisto.R
import com.example.instruisto.databinding.FragmentLessonListBinding
import com.example.instruisto.model.Lesson
import com.example.instruisto.ui.lesson.LessonActivity
import com.example.instruisto.util.LessonsListAdapter

class LessonListFragment : Fragment() {
    lateinit var binding: FragmentLessonListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentLessonListBinding.inflate(inflater)
        val recycler = binding.recycler
        recycler.adapter = LessonsListAdapter(listOf(
            Lesson(
                id = 1,
                number = 1,
                steps = listOf(),
                status = true
            ),
            Lesson(
                id = 2,
                number = 2,
                steps = listOf(),
                status = true
            ),
            Lesson(
                id = 3,
                number = 3,
                steps = listOf(),
                status = true
            ),
            Lesson(
                id = 4,
                number = 4,
                steps = listOf(),
                status = true
            )
        ), requireActivity().applicationContext){
            val intent = Intent(requireActivity(), LessonActivity::class.java).putExtra("lesson", it)
            startActivity(intent)
        }
        return binding.root
    }
}
package com.example.instruisto.ui.lessonlist

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.instruisto.data.repo.LessonRepository
import com.example.instruisto.databinding.FragmentLessonListBinding
import com.example.instruisto.ui.lesson.LessonActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LessonListFragment : Fragment() {
    lateinit var binding: FragmentLessonListBinding
    @Inject lateinit var lessons: LessonRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        updateList()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentLessonListBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun updateList(){
        lifecycleScope.launch {
            binding.recycler.adapter = LessonsListAdapter(lessons.all(), requireActivity().applicationContext){
                val intent = Intent(requireActivity(), LessonActivity::class.java).putExtra(LessonActivity.LESSON_EXTRA, it)
                startActivity(intent)
            }
        }
    }
}
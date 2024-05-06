package com.example.instruisto.ui.lesson

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.instruisto.R
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.withCreationCallback

@AndroidEntryPoint
class LessonActivity : AppCompatActivity() {
    companion object{
        const val LESSON_EXTRA = "tk.vhhg.instruisto.lessonId"
    }
    private val viewModel: LessonViewModel by viewModels<LessonViewModel>(
        extrasProducer = {
            defaultViewModelCreationExtras.withCreationCallback<LessonViewModel.Factory> {
                it.create(intent.extras!!.getInt(LESSON_EXTRA))
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lesson)
    }
}
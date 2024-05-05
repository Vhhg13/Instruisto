package com.example.instruisto.ui.lesson

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.instruisto.R
import com.example.instruisto.databinding.ActivityLessonBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LessonActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lesson)
        val lessonId = intent.getIntExtra("lesson", -1)

    }
}
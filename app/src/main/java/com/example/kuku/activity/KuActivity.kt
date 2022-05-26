package com.example.kuku.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kuku.R

/* original Activity class to be extends */

class KuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
package com.example.kuku.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.kuku.R

class KuIntroLogoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ku_intro_logo)

        var handler = Handler()
        handler.postDelayed({
            var intent = Intent(this, KuIntroActivity::class.java)
            startActivity(intent)
        }, 3000)

    }

    override fun onPause() {
        super.onPause()
        finish()
    }
}
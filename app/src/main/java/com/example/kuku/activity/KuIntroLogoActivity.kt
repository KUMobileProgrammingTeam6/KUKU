package com.example.kuku.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.content.ContextCompat.startActivity
import com.example.kuku.R
import com.example.kuku.databinding.ActivityKuIntroBinding
import com.example.kuku.databinding.ActivityKuIntroLogoBinding

class KuIntroLogoActivity : KuActivity<ActivityKuIntroLogoBinding>(ActivityKuIntroLogoBinding::inflate) {
    private val WAIT_TIME = 3000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun init() {
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            val intent = Intent(this, KuIntroActivity::class.java)
            startActivity(intent)
        }, WAIT_TIME)
    }

    override fun onPause() {
        super.onPause()
        finish()
    }
}
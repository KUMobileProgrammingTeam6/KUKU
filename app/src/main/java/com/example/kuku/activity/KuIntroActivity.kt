package com.example.kuku.activity

import android.content.Intent
import android.os.Bundle
import com.example.kuku.databinding.ActivityKuIntroBinding

class KuIntroActivity : KuActivity<ActivityKuIntroBinding>(ActivityKuIntroBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun init() {
        initLayout()
    }

    private fun initLayout() {
        binding.itemSearchEntry.setOnClickListener {
            val intent = Intent(this, KuSearchActivity::class.java)
            startActivity(intent)
        }
    }
}
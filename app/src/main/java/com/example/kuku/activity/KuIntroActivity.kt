package com.example.kuku.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
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

        binding.basketEntry.setOnClickListener {
            val intent = Intent(this, KuBasketActivity::class.java)
            startActivity(intent)
        }

    }

    var backKeyPressedTime: Long = 0

    override fun onBackPressed() {
        //super.onBackPressed()
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            Toast.makeText(this, "앱을 종료하려면 뒤로가기 버튼을 한 번 더 눌러주세요.", Toast.LENGTH_SHORT).show()
            return;
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            finishAffinity()
        }
    }
}
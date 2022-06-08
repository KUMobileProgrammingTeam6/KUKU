package com.example.kuku.activity

import android.content.Intent
import android.widget.Toast
import com.example.kuku.app.KuToastMessage
import com.example.kuku.databinding.ActivityKuIntroBinding

class KuIntroActivity : KuActivity<ActivityKuIntroBinding>(ActivityKuIntroBinding::inflate) {
    var backKeyPressedTime = 0L
    val WAIT_TIME = 2000L

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

    override fun onBackPressed() {
        //super.onBackPressed()
        if (System.currentTimeMillis() > backKeyPressedTime + WAIT_TIME) {
            backKeyPressedTime = System.currentTimeMillis()
            KuToastMessage(this, "앱을 종료하려면 뒤로가기 버튼을 한 번 더 눌러주세요.")
            return
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + WAIT_TIME) {
            finishAffinity()
        }
    }
}
package com.example.kuku.activity

import android.content.Intent
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.kuku.data.KuData
import com.example.kuku.databinding.ActivityKuItemShowBinding

class KuItemShowActivity : KuActivity<ActivityKuItemShowBinding>(ActivityKuItemShowBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun init() {
        initLayout()
    }

    private fun initLayout() {
        val data = intent.getSerializableExtra("data") as KuData
        binding.productProductNameTv.text = data.name // 제품명
        binding.productDetailTv.text = data.description //제품설명
        binding.productPriceTv.text = data.price.toString() + "원" //제품가격
        binding.productStockTv.text = "재고: " + data.stock.toString() + "개" //제품재고
        Glide.with(binding.root).load(data.imgUrl).into(binding.productIv) //이미지

        binding.productBackIv.setOnClickListener {
            val intent = Intent(this, KuSearchActivity::class.java)
            startActivity(intent)
        }

        binding.searchLocBtn.setOnClickListener {
//            val intent = Intent(this, KuLocationActivity::class.java)
//            startActivity(intent)
        }

        binding.addBasketBtn.setOnClickListener {
//            val intent = Intent(this, KuBasketActivity::class.java)
//            startActivity(intent)
        }
    }
}
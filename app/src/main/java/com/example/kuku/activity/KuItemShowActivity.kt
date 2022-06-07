package com.example.kuku.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.kuku.data.KuData
import com.example.kuku.databinding.ActivityKuItemShowBinding
import com.example.kuku.db.KuDbHelper
import com.example.kuku.db.KuDbHelperBasket

class KuItemShowActivity : KuActivity<ActivityKuItemShowBinding>(ActivityKuItemShowBinding::inflate) {
    lateinit var kuDbHelperBasket: KuDbHelperBasket
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
        binding.productTagTv.text = data.tag.toString().replace("[", "").replace("]", "").replace(",", "") // 제품 태그
        binding.productPriceTv.text = data.price.toString() + "원" //제품가격
        binding.productStockTv.text = "재고: " + data.stock.toString() + "개" //제품재고
        Glide.with(binding.root).load(data.imgUrl).into(binding.productIv) //이미지

        binding.productBackIv.setOnClickListener {
            onBackPressed()
        }

        binding.productBasketIv.setOnClickListener {
            val intent = Intent(this, KuBasketActivity::class.java)
            startActivity(intent)
        }

        binding.productHomeIv.setOnClickListener {
            val intent = Intent(this, KuIntroActivity::class.java)
            finishAffinity()
            startActivity(intent)
        }

        binding.searchLocBtn.setOnClickListener {
//            val intent = Intent(this, KuLocationActivity::class.java)
//            startActivity(intent)
        }

        kuDbHelperBasket = KuDbHelperBasket(this)
        binding.addBasketBtn.setOnClickListener {
            val result = kuDbHelperBasket.insertProduct(data)
            if (result) {
                toastMessage(this@KuItemShowActivity, "장바구니에 추가되었습니다.")
                //Toast.makeText(this@KuItemShowActivity, "장바구니에 추가되었습니다.", Toast.LENGTH_SHORT).show()
            } else {
                toastMessage(this@KuItemShowActivity, "이미 장바구니에 있습니다.")
                //Toast.makeText(this@KuItemShowActivity, "이미 장바구니에 있습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

}
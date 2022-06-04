package com.example.kuku.activity

import android.content.Intent
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.kuku.activity.KuActivity
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
        //인텐트 받아옴(searchActivity에 클릭한 상품의 데이터를 받아와서)
        val data = intent.getSerializableExtra("keyP") as KuData
        binding.productProductNameTv.text = data.name // 제품명
        binding.productDetailTv.text=data.description //제품설명
        binding.productPriceTv.text = data.price.toString()+"원" //제품가격
        binding.productStockTv.text = "재고: "+data.stock.toString()+"개" //제품재고
        Glide.with(binding.root).load(data.imgUrl).into(binding.productIv) //이미지

        // <클릭이벤트>
        binding.procuctBackIv.setOnClickListener { //뒤로가기버튼
            val i = Intent(this, KuSearchActivity::class.java)
            startActivity(i)
        }

        binding.searchLocBtn.setOnClickListener { //위치검색버튼
            val i = Intent(this, KuLocationActivity::class.java)
            //일단 데이터 없이 화면 전환만 한 상태
            startActivity(i)
        }

        binding.searchLocBtn.setOnClickListener { //장바구니버튼
            val i = Intent(this, KuLocationActivity::class.java)
            //일단 데이터 없이 화면 전환만 한 상태
            startActivity(i)
        }
    }
}
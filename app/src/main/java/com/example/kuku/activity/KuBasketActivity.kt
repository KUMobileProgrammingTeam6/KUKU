package com.example.kuku.activity

import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kuku.app.KuToastMessage
import com.example.kuku.data.KuData
import com.example.kuku.databinding.ActivityKuBasketBinding
import com.example.kuku.db.KuDbHelperBasket
import com.example.kuku.recyclerview.KuAdapterBasket

class KuBasketActivity : KuActivity<ActivityKuBasketBinding>(ActivityKuBasketBinding::inflate) {
    private lateinit var adapter: KuAdapterBasket
    private lateinit var kuDbHelperBasket: KuDbHelperBasket
    private var data: ArrayList<KuData> = ArrayList()

    override fun init() {
        initData()
        initRecyclerView()
        initLayout()
    }

    private fun initData() {
        kuDbHelperBasket = KuDbHelperBasket(this)
        readDatabase(kuDbHelperBasket, data)
    }

    private fun readDatabase(kuDbHelperBasket: KuDbHelperBasket, data: ArrayList<KuData>) {
        kuDbHelperBasket.getAllRecord(data)
    }

    private fun initRecyclerView() {
        adapter = KuAdapterBasket(data)
        kuDbHelperBasket = KuDbHelperBasket(this)

        adapter.itemClickListener = object : KuAdapterBasket.OnItemClickListener {
            override fun onItemClick(item: KuData) {
                val id = item.id.toString()

                val result = kuDbHelperBasket.deleteProduct(id)
                if (result) {
                    KuToastMessage(this@KuBasketActivity, "장바구니에서 삭제되었습니다.")
                } else {
                    KuToastMessage(this@KuBasketActivity, "삭제 실패하였습니다.\n다시 시도해주세요.")
                }
                data = ArrayList()
                readDatabase(kuDbHelperBasket, data)
                adapter.changeItems(data)
            }
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.adapter = adapter
    }

    private fun initLayout() {
        binding.searchLocBtn.setOnClickListener {
            if (data.isEmpty()) {
                KuToastMessage(this@KuBasketActivity, "장바구니에 물건이 없습니다.")
                return@setOnClickListener
            }
            val intent = Intent(this, KuLocationActivity::class.java)
            intent.putExtra("items", data)
            startActivity(intent)
        }
    }
}
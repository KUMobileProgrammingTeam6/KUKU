package com.example.kuku.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kuku.data.KuData
import com.example.kuku.databinding.ActivityKuBasketBinding
import com.example.kuku.db.KuDbHelper
import com.example.kuku.db.KuDbHelperBasket
import com.example.kuku.recyclerview.KuAdapter
import com.example.kuku.recyclerview.KuAdapterBasket
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList

class KuBasketActivity : KuActivity<ActivityKuBasketBinding>(ActivityKuBasketBinding::inflate) {
    private lateinit var adapter: KuAdapterBasket
    private lateinit var kuDbHelperBasket: KuDbHelperBasket
    private var data: ArrayList<KuData> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun init() {
        initData()
        initRecyclerView()
        initLayout()
    }

    // 데이터 베이스로 옮겼기 때문에 readFile()과 writeDatabase()는 주석처리.
    // 어플에서 처음 실행 시에는 readDatabase()는 주석 처리하고 readFile()과 writeDatabase()만 호출하여 DB 채워주고 다시 주석 처리하시면 됩니다.
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
            override fun onItemClick(data2: KuData) {
                val id = data2.id.toString()

                val result = kuDbHelperBasket.deleteProduct(id)
                if (result) {
                    Toast.makeText(this@KuBasketActivity, "삭제 성공", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@KuBasketActivity, "삭제 실패", Toast.LENGTH_SHORT).show()
                }
                data = ArrayList()
                init()
            }
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.adapter = adapter
    }

    private fun initLayout() {
        /*binding.searchBtn.setOnClickListener {
            // get searchBar's text
            val inputText = binding.editText1.text.toString()
            if (inputText.isEmpty()) {
                Toast.makeText(this, "검색어를 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val newData = kuDbHelper.searchProductName(inputText)
            if (newData.isEmpty()) {
                Toast.makeText(this, "조회된 상품이 없습니다.", Toast.LENGTH_SHORT).show()
            }
            adapter.changeItems(newData)
            Toast.makeText(this, "총 ${newData.size} 개의 상품이 검색되었습니다.", Toast.LENGTH_SHORT).show()
        }*/
    }

}
package com.example.kuku.activity

import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kuku.R
import com.example.kuku.app.KuToastMessage
import com.example.kuku.data.KuData
import com.example.kuku.databinding.ActivityKuSearchBinding
import com.example.kuku.db.KuDbHelper
import com.example.kuku.recyclerview.KuAdapter
import org.json.JSONObject
import java.util.*

class KuSearchActivity : KuActivity<ActivityKuSearchBinding>(ActivityKuSearchBinding::inflate) {
    private lateinit var adapter: KuAdapter
    private lateinit var kuDbHelper: KuDbHelper
    private val data: ArrayList<KuData> = ArrayList()

    override fun init() {
        initData()
        initRecyclerView()
        initLayout()
    }

    // APK 배포시 kuku.db를 제공할 수 있는 방안 찾기
    private fun initData() {
        kuDbHelper = KuDbHelper(this)
        readDatabase(kuDbHelper, data)
        assert(data.isNotEmpty())
        /*
        if (data.isEmpty()) {
            readFile(Scanner(resources.openRawResource(R.raw.items1)), data, 100)
            readFile(Scanner(resources.openRawResource(R.raw.items2)), data, 200)
            readFile(Scanner(resources.openRawResource(R.raw.items3)), data, 300)
            readFile(Scanner(resources.openRawResource(R.raw.items4)), data, 400)
            readFile(Scanner(resources.openRawResource(R.raw.items5)), data, 500)
            readFile(Scanner(resources.openRawResource(R.raw.items6)), data, 600)
            readFile(Scanner(resources.openRawResource(R.raw.items7)), data, 700)
            println(String.format("data's size = %d", data.size))
            writeDatabase(kuDbHelper, data)
        }
         */
    }

    private fun readFile(sc: Scanner, data: ArrayList<KuData>, location: Int) {
        while (sc.hasNextLine()) {
            val line = sc.nextLine()!!
            val jsonObject = JSONObject(line)
            val id = jsonObject.getInt("id")
            val name = jsonObject.getString("text")
            val price = jsonObject.getString("price").replace(",", "").toInt()
            val description = jsonObject.getString("explain")
            val keywords = jsonObject.getJSONArray("tags").toString().split(", ").map { it.replace("[", "").replace("]", "").replace("\"", "") }
            val imgUrl = jsonObject.getString("imgUrl")
            data.add(KuData(id, name, price, description, keywords, imgUrl, 1, location))
//            System.out.println(data.get(data.size - 1))
        }
    }

    private fun readDatabase(kuDbHelper: KuDbHelper, data: ArrayList<KuData>) {
        kuDbHelper.getAllRecord(data)
    }

    private fun writeDatabase(kuDbHelper: KuDbHelper, data: ArrayList<KuData>) {
        data.forEach { kuDbHelper.insertProduct(it) }
    }

    private fun initRecyclerView() {
        adapter = KuAdapter(data)
        adapter.itemClickListener = object : KuAdapter.OnItemClickListener {
            override fun onItemClick(data: KuData) {
                val intent = Intent(this@KuSearchActivity, KuItemShowActivity::class.java)
                intent.putExtra("data", data)
                startActivity(intent)
            }
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.adapter = adapter
        KuToastMessage(this@KuSearchActivity, "총 ${data.size} 개의 상품이 조회되었습니다.")
    }

    private fun initLayout() {
        binding.searchBtn.setOnClickListener {
            // get searchBar's text
            val inputText = binding.editText1.text.toString()
            if (inputText.isEmpty()) {
                KuToastMessage(this@KuSearchActivity, "검색어를 입력해주세요.")
                return@setOnClickListener
            }
            val newData = kuDbHelper.searchProductName(inputText)
            if (newData.isEmpty()) {
                KuToastMessage(this@KuSearchActivity, "조회된 상품이 없습니다.")
            }
            adapter.changeItems(newData)
            KuToastMessage(this@KuSearchActivity, "총 ${newData.size} 개의 상품이 검색되었습니다.")
        }
    }
}
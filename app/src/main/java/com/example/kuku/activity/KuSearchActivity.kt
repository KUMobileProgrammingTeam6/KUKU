package com.example.kuku.activity

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kuku.R
import com.example.kuku.data.KuData
import com.example.kuku.databinding.ActivityKuSearchBinding
import com.example.kuku.db.KuDbHelper
import com.example.kuku.recyclerview.KuAdapter
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList

class KuSearchActivity : KuActivity<ActivityKuSearchBinding>(ActivityKuSearchBinding::inflate) {
    private lateinit var adapter: KuAdapter
    private lateinit var kuDbHelper: KuDbHelper
    private val data: ArrayList<KuData> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun init() {
        initData()
        initRecyclerView()
    }

    // 데이터 베이스로 옮겼기 때문에 readFile()과 writeDatabase()는 주석처리.
    // 어플에서 처음 실행 시에는 readDatabase()는 주석 처리하고 readFile()과 writeDatabase()만 호출하여 DB 채워주고 다시 주석 처리하시면 됩니다.
    private fun initData() {
//        readFile(Scanner(resources.openRawResource(R.raw.items)), data)
        kuDbHelper = KuDbHelper(this)
//        writeDatabase(kuDbHelper, data)
        readDatabase(kuDbHelper, data)
    }

    private fun readFile(sc: Scanner, data: ArrayList<KuData>) {
        while (sc.hasNextLine()) {
            val line = sc.nextLine()!!
            val jsonObject = JSONObject(line)
            val id = jsonObject.getInt("id")
            val name = jsonObject.getString("text")
            val price = jsonObject.getString("price").replace(",", "").toInt()
            val description = jsonObject.getString("explain")
            val keywords = jsonObject.getJSONArray("tags").toString().split(", ").map { it.replace("[", "").replace("]", "").replace("\"", "") }
            val imgUrl = jsonObject.getString("imgUrl")
            data.add(KuData(id, name, price, description, keywords, imgUrl, 1, 1))
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
    }
}
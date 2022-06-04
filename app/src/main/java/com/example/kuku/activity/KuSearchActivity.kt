package com.example.kuku.activity

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kuku.R
import com.example.kuku.data.KuData
import com.example.kuku.databinding.ActivityKuSearchBinding
import com.example.kuku.recyclerview.KuAdapter
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList

class KuSearchActivity : KuActivity<ActivityKuSearchBinding>(ActivityKuSearchBinding::inflate) {
    private lateinit var adapter: KuAdapter
    private val data: ArrayList<KuData> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun init() {
        loadData()
        initRecyclerView()
    }

    private fun readFile(sc: Scanner) {
        while (sc.hasNextLine()) {
            val line = sc.nextLine()
            val jsonObject = JSONObject(line)
            val id = jsonObject.getInt("id")
            val name = jsonObject.getString("text")
            val price = jsonObject.getString("price").replace(",", "").toInt()
            val description = jsonObject.getString("explain")
            val keywords = jsonObject.getJSONArray("tags").toString().split(", ").map { it.replace("[", "").replace("]", "").replace("\"", "") }
            val imgUrl = jsonObject.getString("imgUrl")
//            System.out.println(id.toString() + ", " + name+ ", " + price+ ", " + description+ ", " + keywords+ ", " + imgUrl)
            data.add(KuData(id, name, price, description, keywords, imgUrl, 1, 1))
            System.out.println(data.get(data.size - 1))
        }
    }

    private fun loadData() {
        readFile(Scanner(resources.openRawResource(R.raw.items)))
    }

    private fun initRecyclerView() {
        adapter = KuAdapter(data)
        adapter.itemClickListener = object : KuAdapter.OnItemClickListener {
            override fun onItemClick(data: KuData) {
                TODO("Not yet implemented")
            }
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.adapter = adapter
    }
}
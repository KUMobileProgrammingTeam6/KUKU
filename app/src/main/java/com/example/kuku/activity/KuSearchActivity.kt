package com.example.kuku.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kuku.R
import com.example.kuku.data.KuData
import com.example.kuku.databinding.ActivityKuSearchBinding
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList

class KuSearchActivity : KuActivity<ActivityKuSearchBinding>(ActivityKuSearchBinding::inflate) {

    val data: ArrayList<KuData> = ArrayList()
    lateinit var adapter: KuItemShowRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun init() {
        initData()
        initRecyclerView()
    }

    fun readFileScan(scan: Scanner){
        while(scan.hasNextLine()){
/*            val name = scan.nextLine()
            val price = scan.nextLine()
            val stock = scan.nextLine().toInt()*/
            val line = scan.nextLine()
            val jsonObject = JSONObject(line)
            val id = jsonObject.getInt("id")
            val name = jsonObject.getString("text")
            val price = jsonObject.getString("price").replace(",", "").toInt()
            val description = jsonObject.getString("explain")
            val keywords = jsonObject.getJSONArray("tags").toString().split(", ").map { it.replace("[", "").replace("]", "").replace("\"", "") }
            val imgUrl = jsonObject.getString("imgUrl")
            /*val img = scan.nextLine().toString().toInt()*/
            data.add(KuData(id, name, price, description, keywords, imgUrl, 1, 1))
/*            data.add(KuData(name,price,stock))*/
        }
    }

    private fun initData() {
/*        try {
            val scan2 = Scanner(openFileInput("db.itemList.txt"))
            readFileScan(scan2)
        }catch (e:Exception){
            Toast.makeText(this,"추가된 상품 없음", Toast.LENGTH_SHORT).show()
        }*/
        val scan = Scanner(resources.openRawResource(R.raw.products))
       /* while(scan.hasNextLine()){
            val name = scan.nextLine()
            val price = scan.nextLine()
            val stock = scan.nextLine().toInt()
            *//*val img = scan.nextLine().toString().toInt()*//*
            data.add(KuData(name,price,stock))
        }*/
        readFileScan(scan)
    }


    private fun initRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter = KuItemShowRVAdapter(data)

        //▼ 아이템 클릭 리스너
        val intent = Intent(this, KuItemShowActivity::class.java) //onItemClick함수 안에 넣고 싶었는데 그러면 context어떻게 얻는지 모르겠어서 빼둠

        adapter.myItemClickListener  = object: KuItemShowRVAdapter.OnItemClickListener{
            override fun OnItemClick(product: KuData) {
                intent.putExtra("keyP", product) //정보를 넣음: serializable이라 넣을 수 있음.
                startActivity(intent)
            }
        }

        binding.recyclerView.adapter=adapter

    }
}
package com.example.kuku.activity

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kuku.R
import com.example.kuku.data.KuData
import com.example.kuku.databinding.ActivityKuSearchBinding
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
            val name = scan.nextLine()
            val price = scan.nextLine()
            val stock = scan.nextLine().toInt()
            /*val img = scan.nextLine().toString().toInt()*/
            data.add(KuData(name,price,stock))
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
        while(scan.hasNextLine()){
            val name = scan.nextLine()
            val price = scan.nextLine()
            val stock = scan.nextLine().toInt()
            /*val img = scan.nextLine().toString().toInt()*/
            data.add(KuData(name,price,stock))
        }
    }

    private fun initRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter = KuItemShowRVAdapter(data)
        binding.recyclerView.adapter=adapter
    }
}
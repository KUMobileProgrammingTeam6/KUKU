package com.example.kuku.activity

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.Toast
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
        initLayout()
    }

    // 데이터 베이스로 옮겼기 때문에 readFile()과 writeDatabase()는 주석처리.
    // 어플에서 처음 실행 시에는 readDatabase()는 주석 처리하고 readFile()과 writeDatabase()만 호출하여 DB 채워주고 다시 주석 처리하시면 됩니다.
    private fun initData() {

        //readFile(Scanner(resources.openRawResource(R.raw.items7)), data, 7)
        kuDbHelper = KuDbHelper(this)
        /*writeDatabase(kuDbHelper, data)*/
        readDatabase(kuDbHelper, data)
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
        toastMessage(this@KuSearchActivity, "총 ${data.size} 개의 상품이 조회되었습니다.")
        //Toast.makeText(this, "총 ${data.size} 개의 상품이 조회되었습니다.", Toast.LENGTH_SHORT).show()
    }

    private fun initLayout() {
        binding.searchBtn.setOnClickListener {
            // get searchBar's text

            val inputText = binding.editText1.text.toString()
            if (inputText.isEmpty()) {
                toastMessage(this@KuSearchActivity, "검색어를 입력해주세요.")
                //Toast.makeText(this, "검색어를 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val newData = kuDbHelper.searchProductName(inputText)
            if (newData.isEmpty()) {
                toastMessage(this@KuSearchActivity, "조회된 상품이 없습니다.")
                //Toast.makeText(this, "조회된 상품이 없습니다.", Toast.LENGTH_SHORT).show()
            }
            adapter.changeItems(newData)
            toastMessage(this@KuSearchActivity, "총 ${newData.size} 개의 상품이 검색되었습니다.")
            //Toast.makeText(this, "총 ${newData.size} 개의 상품이 검색되었습니다.", Toast.LENGTH_SHORT).show()
        }
    }
}
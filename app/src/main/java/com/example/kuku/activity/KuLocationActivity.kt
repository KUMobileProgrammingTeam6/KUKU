package com.example.kuku.activity

import android.content.Intent
import android.view.View
import android.widget.ImageView
import com.example.kuku.data.KuData
import com.example.kuku.databinding.ActivityKuLocationBinding

class KuLocationActivity : KuActivity<ActivityKuLocationBinding>(ActivityKuLocationBinding::inflate) {
    private lateinit var imageViews: Array<ImageView>

    override fun init() {
        initLayout()
    }

    private fun initLayout() {
        imageViews = with(binding) {arrayOf(imageViewA, imageViewB, imageViewC, imageViewD, imageViewE, imageViewF, imageViewG)}
        val items = intent.getSerializableExtra("items") as ArrayList<KuData>
        for (item in items) {
            val pos = locToPos(item.location)
            imageViews[pos].visibility = View.VISIBLE
        }

        binding.productBackIv.setOnClickListener {
            onBackPressed()
        }
    }

    private fun locToPos(location: Int) = location / 100 - 1
}
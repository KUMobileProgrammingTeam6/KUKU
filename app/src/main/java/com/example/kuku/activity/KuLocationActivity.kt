package com.example.kuku.activity

import android.content.Intent
import android.view.MotionEvent
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

        var textA:String = ""
        var textB:String = ""
        var textC:String = ""
        var textD:String = ""
        var textE:String = ""
        var textF:String = ""
        var textG:String = ""
        var text:String=""

        for (item in items) {
            val pos = locToPos(item.location)
            imageViews[pos].visibility = View.VISIBLE
            when(item.location){
                100->textA += item.name +"\n     "
                200->textB += item.name +"\n     "
                300->textC += item.name +"\n     "
                400->textD += item.name +"\n     "
                500->textE += item.name +"\n     "
                600->textF += item.name +"\n     "
                700->textG += item.name +"\n     "
            }
        }


        binding.imageViewA.setOnClickListener {
            binding.maptext.setText("A : " + textA)
        }
        binding.imageViewB.setOnClickListener {
            binding.maptext.setText("B : " +textB)
        }
        binding.imageViewC.setOnClickListener {
            binding.maptext.setText("C : " +textC)
        }
        binding.imageViewD.setOnClickListener {
            binding.maptext.setText("D : " +textD)
        }
        binding.imageViewE.setOnClickListener {
            binding.maptext.setText("E : " +textE)
        }
        binding.imageViewF.setOnClickListener {
            binding.maptext.setText("F : " +textF)
        }
        binding.imageViewG.setOnClickListener {
            binding.maptext.setText("G : " +textG)
        }
        binding.productIv.setOnClickListener {
            binding.maptext.setText("")
        }

        binding.productBackIv.setOnClickListener {
            onBackPressed()
        }
    }

    private fun locToPos(location: Int) = location / 100 - 1
}
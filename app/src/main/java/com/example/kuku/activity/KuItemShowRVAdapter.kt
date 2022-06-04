package com.example.kuku.activity

import android.util.Log.w
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kuku.data.KuData
import com.example.kuku.databinding.ItemShowRowBinding

class KuItemShowRVAdapter(val products:ArrayList<KuData>) : RecyclerView.Adapter<KuItemShowRVAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemShowRowBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(product: KuData){
            binding.productName.text = product.name // 제품명
            binding.productPrice.text = product.price.toString()+"원" //제품가격
            binding.productStock.text = product.stock.toString()+"개" //제품재고
            Glide.with(binding.root).load(product.imgUrl).into(binding.productImage) //이미지
        }
    }

    interface OnItemClickListener{
        fun OnItemClick(product: KuData)
    }

    lateinit var myItemClickListener: OnItemClickListener


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemShowRowBinding = ItemShowRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(products[position])

        holder.itemView.setOnClickListener { //클릭리스너 달아줌
            myItemClickListener.OnItemClick(products[position])
        }

    }

    override fun getItemCount(): Int {
        return products.size
    }
}
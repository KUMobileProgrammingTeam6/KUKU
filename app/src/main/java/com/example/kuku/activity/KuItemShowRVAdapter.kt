package com.example.kuku.activity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kuku.data.KuData
import com.example.kuku.databinding.ItemShowRowBinding

class KuItemShowRVAdapter(val products:ArrayList<KuData>) : RecyclerView.Adapter<KuItemShowRVAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemShowRowBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(product: KuData){
            binding.productName.text = product.name // 제품명
            binding.productPrice.text = product.price.toString() //제품가격
            binding.productStock.text = product.stock.toString() //제품재고
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding: ItemShowRowBinding = ItemShowRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(products[position])

    }

    override fun getItemCount(): Int {
        return products.size
    }
}
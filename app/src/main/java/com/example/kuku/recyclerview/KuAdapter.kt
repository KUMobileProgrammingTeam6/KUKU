package com.example.kuku.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kuku.data.KuData
import com.example.kuku.databinding.ItemShowRowBinding

class KuAdapter(private var items: ArrayList<KuData>) : RecyclerView.Adapter<KuAdapter.ViewHolder>() {
    var itemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(data: KuData)
    }

    // Provide a reference to the type of views
    inner class ViewHolder(val binding: ItemShowRowBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            // Define click listener for the ViewHolder's View.
            binding.itemLayout.setOnClickListener {
                itemClickListener?.onItemClick(items[adapterPosition])
            }
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val binding = ItemShowRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        val data = items[position]
        holder.binding.productName.text = data.name
        holder.binding.productPrice.text = data.price.toString() + "원"
        holder.binding.productStock.text = data.stock.toString() + "개"
        Glide.with(holder.binding.root).load(data.imgUrl).into(holder.binding.productImage)
        holder.binding.deleteBtn.visibility = View.GONE
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun changeItems(newData: ArrayList<KuData>) {
        items = newData
        notifyDataSetChanged()
    }
}
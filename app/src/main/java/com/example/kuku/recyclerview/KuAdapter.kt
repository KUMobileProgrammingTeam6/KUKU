package com.example.kuku.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kuku.data.KuData
import com.example.kuku.databinding.ItemShowRowBinding

class KuAdapter(private val items: ArrayList<KuData>) : RecyclerView.Adapter<KuAdapter.ViewHolder>() {
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
    }

    override fun getItemCount(): Int {
        return items.size
    }
}
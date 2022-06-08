package com.example.kuku.data

import java.io.Serializable

data class KuData(var id: Int, var name: String, var price: Int, var description: String, var tag: List<String>, var imgUrl: String, var stock: Int, var location: Int) : Serializable {
    override fun toString() = "KuData($id, $name, $price, $description, $tag, $imgUrl, $stock, $location)"
}
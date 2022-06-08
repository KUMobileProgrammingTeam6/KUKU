package com.example.kuku.app

import android.content.Context
import android.widget.Toast

class KuToastMessage(context: Context, msg: String?): Toast(context) {
    companion object {
        private var toast: Toast? = null
    }

    init {
        if (toast == null) {
            toast = makeText(context, msg, LENGTH_SHORT)
        } else {
            toast?.setText(msg)
        }
        toast?.show()
    }
}
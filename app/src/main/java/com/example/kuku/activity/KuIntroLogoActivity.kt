package com.example.kuku.activity

import android.content.Intent
import android.os.Handler
import android.os.Looper
import com.example.kuku.databinding.ActivityKuIntroLogoBinding
import java.io.File
import java.io.FileOutputStream

class KuIntroLogoActivity : KuActivity<ActivityKuIntroLogoBinding>(ActivityKuIntroLogoBinding::inflate) {
    private val WAIT_TIME = 2000L

    override fun init() {
        loadDB("kuku.db")
        logoDelay()
    }

    private fun logoDelay() {
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            val intent = Intent(this, KuIntroActivity::class.java)
            startActivity(intent)
        }, WAIT_TIME)
    }

    private fun loadDB(DB_NAME: String) {
        val DB_PATH = "/data/data/" + applicationContext.packageName + "/databases/"
        try {
            val fileDir = File(DB_PATH)
            if (!fileDir.exists()) {
                fileDir.mkdir()
            }
            val strOutFile = DB_PATH + DB_NAME
            val dbFile = File(strOutFile)
            if (dbFile.exists()) {
//                System.out.println("dbFile exists")
                return
            }
//            System.out.println("dbFile doesn't exist")
            val inputStream = applicationContext.assets.open(DB_NAME)
            val outputStream = FileOutputStream(strOutFile)

            val mBuffer = ByteArray(1024)
            var mLength: Int
            while ((inputStream.read(mBuffer).also { mLength = it }) > 0) {
                outputStream.write(mBuffer,0, mLength)
            }
            outputStream.flush()
            outputStream.close()
            inputStream.close()
        } catch(e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onPause() {
        super.onPause()
        finish()
    }
}
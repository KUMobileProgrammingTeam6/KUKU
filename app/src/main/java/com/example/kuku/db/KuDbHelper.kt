package com.example.kuku.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import com.example.kuku.data.KuData
import com.example.kuku.db.KuDbHelper.Companion.COL_ID
import com.example.kuku.db.KuDbHelper.Companion.COL_NAME

class KuDbHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME,null, DB_VERSION) {
    companion object {
        const val DB_NAME = "kuku.db"
        const val DB_VERSION = 1

        const val TABLE_NAME = "entry"

        const val COL_ID = "id"
        const val COL_NAME = "name"
        const val COL_PRICE = "price"
        const val COL_DESCRIPTION = "description"
        const val COL_TAG = "tag"
        const val COL_IMG_URL = "img_url"

        private const val SQL_CREATE_ENTRIES =
            "CREATE TABLE IF NOT EXISTS ${TABLE_NAME}(" +
                    "${COL_ID} INTEGER PRIMARY KEY, " +
                    "${COL_NAME} TEXT, " +
                    "${COL_PRICE} INTEGER, " +
                    "${COL_DESCRIPTION} TEXT, " +
                    "${COL_TAG} JSON, " +
                    "${COL_IMG_URL} TEXT);"
        private const val SQL_DROP_TABLE =
            "DROP TABLE IF EXISTS ${TABLE_NAME};"
        private const val SQL_GET_ALL =
            "SELECT * FROM ${TABLE_NAME} ORDER BY ${COL_ID} DESC;"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL(SQL_DROP_TABLE)
        onCreate(db)
    }

    fun getAllRecord(data: ArrayList<KuData>) {
        val db = this.readableDatabase
        val cursor = db.rawQuery(SQL_GET_ALL, null)
        readCursor(cursor, data)
        cursor.close()
        db.close()
    }

    fun insertProduct(data: KuData): Boolean {
        val values = ContentValues()
        values.put(COL_ID, data.id)
        values.put(COL_NAME, data.name)
        values.put(COL_PRICE, data.price)
        values.put(COL_DESCRIPTION, data.description)
        values.put(COL_TAG, data.tag.toString())
        values.put(COL_IMG_URL, data.imgUrl)
        val db = this.writableDatabase
        val ok = db.insert(TABLE_NAME, null, values) > 0
        if (!ok) {
            System.out.println("values = " + values)
        }
        db.close()
        return ok
    }

    private fun readCursor(cursor: Cursor, data: ArrayList<KuData>) {
        with(cursor) {
            if (count == 0) {
                System.out.println("the cursor is empty")
                return
            }
            while (moveToNext()) {
                val id = getInt(0)
                val name = getString(1)
                val price = getInt(2)
                val description = getString(3)
                val tag = getString(4).split(", ").map { it.replace("[", "").replace("]", "").replace("\"", "") }
                val imgUrl = getString(5).replace("http:", "")
                val curData = KuData(id, name, price, description, tag, imgUrl, 1, 1)
//                System.out.println(curData)
                data.add(curData)
            }
        }
    }

    // search text in db by like '%text%'
    fun searchProductName(targetText: String): ArrayList<KuData> {
        val db = this.readableDatabase
        val selection = "${COL_NAME} LIKE ?"
        val selectionArgs = arrayOf("%" + targetText + "%")
        val sortOrder = "${COL_ID} DESC"
        val cursor = db.query(TABLE_NAME, null, selection, selectionArgs, null, null, sortOrder)
        val data: ArrayList<KuData> = ArrayList()
        readCursor(cursor, data)
        cursor.close()
        db.close()
        return data
    }
}
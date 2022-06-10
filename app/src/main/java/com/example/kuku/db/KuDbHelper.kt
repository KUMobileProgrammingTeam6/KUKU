package com.example.kuku.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.kuku.data.KuData

class KuDbHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME,null, DB_VERSION) {
    companion object {
        const val DB_NAME = "kuku.db"
        const val DB_VERSION = 1

        const val TABLE_NAME = "product"

        const val COL_ID = "id"
        const val COL_NAME = "name"
        const val COL_PRICE = "price"
        const val COL_DESCRIPTION = "description"
        const val COL_TAG = "tag"
        const val COL_IMG_URL = "img_url"
        const val COL_LOCATION = "location"

        const val SQL_CREATE_ENTRIES =
            "CREATE TABLE IF NOT EXISTS ${TABLE_NAME}(" +
                    "$COL_ID INTEGER PRIMARY KEY, " +
                    "$COL_NAME TEXT, " +
                    "$COL_PRICE INTEGER, " +
                    "$COL_DESCRIPTION TEXT, " +
                    "$COL_TAG JSON, " +
                    "$COL_IMG_URL TEXT, " +
                    "$COL_LOCATION INTEGER);"

        const val SQL_DROP_TABLE =
            "DROP TABLE IF EXISTS ${TABLE_NAME};"

        const val SQL_GET_ALL =
            "SELECT * FROM $TABLE_NAME ORDER BY $COL_ID DESC;"

        internal const val DEFAULT_STOCK = 1
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

    private fun readCursor(cursor: Cursor, data: ArrayList<KuData>) {
        with(cursor) {
            if (count == 0) {
                println("the cursor is empty")
                return
            }
            while (moveToNext()) {
                val id = getInt(0)
                val name = getString(1)
                val price = getInt(2)
                val description = getString(3)
                val tag = getString(4).split(", ").map { it.replace("[", "").replace("]", "").replace("\"", "") }
                val imgUrl = getString(5).replace("http:", "")
                val location = getInt(6)
                val curData = KuData(id, name, price, description, tag, imgUrl, DEFAULT_STOCK, location)
//                System.out.println(curData)
                data.add(curData)
            }
        }
    }

    // search text in db by like '%text%'
    fun searchProductName(targetText: String): ArrayList<KuData> {
        val db = this.readableDatabase
        val selection = "$COL_NAME LIKE ?"
        val selectionArgs = arrayOf("%$targetText%")
        val sortOrder = "$COL_ID DESC"
        val cursor = db.query(TABLE_NAME, null, selection, selectionArgs, null, null, sortOrder)
        val data: ArrayList<KuData> = ArrayList()
        readCursor(cursor, data)
        cursor.close()
        db.close()
        return data
    }
}
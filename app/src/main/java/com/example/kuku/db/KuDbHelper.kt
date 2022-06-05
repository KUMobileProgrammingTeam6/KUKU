package com.example.kuku.db

import android.content.Context
import android.database.sqlite.SQLiteOpenHelper

class KuDbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME,null, DATABASE_VERSION){
}
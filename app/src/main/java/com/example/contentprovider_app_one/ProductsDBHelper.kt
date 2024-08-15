package com.example.contentprovider_app_one

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class ProductsDBHelper(context: Context?,
                        dbName: String,
                        factory : SQLiteDatabase.CursorFactory?,
                        version: Int) : SQLiteOpenHelper(context,dbName,factory,version) {

    override fun onCreate(db: SQLiteDatabase?) {
        Log.e("tag","SQLiteOpenHelper.onCreate Called")
        db!!.execSQL("create table products(id integer primary key, title text,price integer);")
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        var values = ContentValues()
        values.put("id",101)
        values.put("title","mackbook")
        values.put("price",100000)

        var rowNum = db!!.insert("products",null,values)

        var values1 = ContentValues()
        values1.put("id",102)
        values1.put("title","masus")
        values1.put("price",150155)

        var rowNum1 = db!!.insert("products",null,values)

        var values2 = ContentValues()
        values2.put("id",103)
        values2.put("title","msi")
        values2.put("price",1075800)

        var rowNum2 = db!!.insert("products",null,values)
    }

    override fun onDowngrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        super.onDowngrade(db, oldVersion, newVersion)
    }
                        }
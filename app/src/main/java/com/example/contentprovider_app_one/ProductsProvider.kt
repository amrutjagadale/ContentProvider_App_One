package com.example.contentprovider_app_one

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
import android.util.Log

class ProductsProvider() : ContentProvider() {

    private lateinit var db : SQLiteDatabase
    private var uriMatcher = UriMatcher(-1)



    override fun onCreate(): Boolean {
        db = ProductsDBHelper(context,"db_products",null,1).writableDatabase
        initUriMatcher()
        return true
    }

    private fun initUriMatcher(){
        uriMatcher.addURI("in.bitcode.products","products",1)
        uriMatcher.addURI("in.bitcode.products","products/#",2)
        uriMatcher.addURI("in.bitcode.products","products/electronics",3)

    }
    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection : String?,
        selectionArgs: Array<out String>?,
        sortOrder : String?
    ): Cursor? {
        when (uriMatcher.match(uri)){
            1 ->
                    return db.query("products",projection,null,null,null,null,sortOrder)

            2 -> return  db.query("products",projection,"id = ?", arrayOf(uri.pathSegments.get(1)),null,null,sortOrder)
        }
        return null
    }

    override fun getType(p0: Uri): String? {
        TODO("Not yet implemented")
    }

    override fun insert(uri: Uri, contentValues: ContentValues?): Uri? {
        Log.e("tag","insert method of provider called")
        when(uriMatcher.match(uri)){
            1 ->{
                var rowNum = db.insert("products",null,contentValues)
                Log.e("tag","${rowNum}")

                if (rowNum.toInt() == -1){
                    return Uri.withAppendedPath(uri,"${contentValues!!.getAsInteger("id")}")
                }
            }
        }
        return null
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
       when(uriMatcher.match(uri)){
           1 ->{
               return db.delete("products","id = ?", arrayOf("${uri.pathSegments.get(1)}"))
           }
       }
        return 0
    }

    override fun update(uri: Uri, contentValues: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int {
        when(uriMatcher.match(uri)){
            1 -> return  db.update(
                "products",
                contentValues,
                "id = ?",
                arrayOf("${uri.pathSegments.get(1)}")
            )
        }
        return 0
    }
}
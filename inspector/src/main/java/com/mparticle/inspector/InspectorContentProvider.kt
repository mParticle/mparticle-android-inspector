package com.mparticle.inspector

import android.app.Application
import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri

class InspectorContentProvider: ContentProvider() {


    override fun insert(uri: Uri?, values: ContentValues?): Uri? { return null }

    override fun query(uri: Uri?, projection: Array<out String>?, selection: String?, selectionArgs: Array<out String>?, sortOrder: String?): Cursor? { return null }

    override fun onCreate(): Boolean {
        Inspector.startWidget(context?.applicationContext as Application, false)
        return false
    }

    override fun update(uri: Uri?, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int { return 0 }

    override fun delete(uri: Uri?, selection: String?, selectionArgs: Array<out String>?): Int { return 0 }

    override fun getType(uri: Uri?): String? { return null }

}
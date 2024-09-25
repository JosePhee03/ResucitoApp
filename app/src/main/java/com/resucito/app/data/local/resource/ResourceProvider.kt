package com.resucito.app.data.local.resource

import android.content.Context

interface ResourceProvider {
    fun getString(resId: Int): String
}

class AndroidResourceProvider(private val context: Context) : ResourceProvider {
    override fun getString(resId: Int): String {
        return context.getString(resId)
    }
}
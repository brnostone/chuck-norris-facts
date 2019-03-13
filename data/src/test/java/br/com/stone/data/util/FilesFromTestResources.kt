package br.com.stone.data.util

import java.io.File

object FilesFromTestResources {

    fun getJson(path : String) : String {
        val uri = this.javaClass.classLoader!!.getResource("json/$path.json")
        val file = File(uri.path)
        return String(file.readBytes())
    }

}
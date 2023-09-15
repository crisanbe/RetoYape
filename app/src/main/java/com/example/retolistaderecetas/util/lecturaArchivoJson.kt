package com.example.retolistaderecetas.util

import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

object Util {
    fun lecturaArchivoJson(nombreArchivo: String): String {
        var flujoEntrada: InputStream? = null
        try {
            flujoEntrada = javaClass.classLoader?.getResourceAsStream(nombreArchivo)
            val builder = StringBuilder()
            val lectorCache = BufferedReader(InputStreamReader(flujoEntrada))
            var str: String? = lectorCache.readLine()
            while (str != null) {
                builder.append(str)
                str = lectorCache.readLine()
            }
            return builder.toString()
        } finally {
            flujoEntrada?.close()
        }
    }
}
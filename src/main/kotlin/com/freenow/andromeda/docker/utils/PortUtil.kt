package com.freenow.andromeda.docker.utils

import java.io.IOException

import java.net.ServerSocket

object PortUtil {

    private fun create(ports: IntArray) : Int {
        for (port in ports) {
             try {
                 val socket =  ServerSocket(port)
                 val returnPort  = socket.localPort
                 socket.close()
                 return returnPort
            } catch (ex: IOException) {
                continue  // try next port
            }
        }
        throw IOException("no free port found")
    }

    fun getAdbPort() =create((5055..5600).toList().toIntArray())

    fun getWebPort() =create((7000..7600).toList().toIntArray())

}
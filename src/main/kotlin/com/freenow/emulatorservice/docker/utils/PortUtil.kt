package com.freenow.emulatorservice.docker.utils

import java.io.IOException

import java.net.ServerSocket

object PortUtil {

    private fun create(ports: IntArray): ServerSocket {
        for (port in ports) {
            return try {
                ServerSocket(port)
            } catch (ex: IOException) {
                continue  // try next port
            }
        }
        throw IOException("no free port found")
    }

    fun getAdbPort() =create((5000..5600).toList().toIntArray()).localPort

    fun getWebPort() =create((7000..7600).toList().toIntArray()).localPort

}
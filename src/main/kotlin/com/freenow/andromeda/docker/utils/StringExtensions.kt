package com.freenow.andromeda.docker.utils

object StringExtensions {

    fun String.trimQuotes() = this.replace("\"","")

    fun String.trimSlash() = this.replace("/", "")

}
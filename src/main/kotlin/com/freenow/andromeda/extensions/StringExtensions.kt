package com.freenow.andromeda.extensions

object StringExtensions {

    fun String.trimQuotes() = this.replace("\"","")

    fun String.trimSlash() = this.replace("/", "")

}
package se.plilja.swaggerapiclient

import khttp.get

fun main(args: Array<String>) {
    //val url = args[0]
    val url = "http://localhost:8080/v2/api-docs"
    val r = get(url).jsonObject
    println(r)
}
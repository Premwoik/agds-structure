package com.prw.association.agds

import com.prw.association.agds.data.Iris

fun main(args: Array<String>) {
    val iris = Iris()
    val data = iris.readDefaultData()
    val agds = iris.agds
    data.forEach { agds.addObject(it) }
    println("Give result objects limit (ENTER to skip): ")
    val limit = readLine()?.toIntOrNull() ?: -1

    println("Give number of the object <0-150)")
    readLine()?.toIntOrNull()?.let {
        if (it >= 0 && it < data.size) {
            val obj = data[it]
            print("Selected object: ")
            println(obj)
            val res = agds.findSimilar(obj)
            (if (limit > -1) res.take(limit) else res).forEach { println(it) }
        } else {
            print("index out of the range")
        }
    }
}
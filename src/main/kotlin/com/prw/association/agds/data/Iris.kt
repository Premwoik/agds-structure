package com.prw.association.agds.data

import com.prw.association.agds.AGDS
import com.prw.association.agds.AttributeNode
import com.prw.association.agds.ClassAttributeNode
import com.prw.association.agds.NumberAttributeNode
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader

class Iris {

    val agds: AGDS = AGDS(
        listOf(
            NumberAttributeNode("sepal length")
            , NumberAttributeNode("sepal width")
            , NumberAttributeNode("petal length")
            , NumberAttributeNode("petal width")
            , ClassAttributeNode("class")
        ) as List<AttributeNode<Any>>
    )


    fun readDefaultData(): List<List<Any>> {
        val path = javaClass.classLoader.getResourceAsStream("iris.data")
        return BufferedReader(InputStreamReader(path)).useLines { it.toList() }.map { it.split(",").map { parse(it) } }
    }

    fun readData(path: String): List<List<Any>> {
        return File(path).useLines { it.toList() }.map { it.split(",").map { parse(it) } }
    }

    private fun parse(line: String): Any {
        return line.toFloatOrNull() ?: line
    }

}
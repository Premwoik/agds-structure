package com.prw.association.agds.classifier

import com.prw.association.agds.ClassAttributeValue
import com.prw.association.agds.FloatAttributeValue
import com.prw.association.agds.ObjectNode
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

class Classifier(val k: Int, private val attributes: List<FloatAttributeValue>) {
    private val ranks: MutableList<Pair<Float, ObjectNode>> = mutableListOf()

    private var smallerIndex = 0
    private var greaterIndex = 0
    private var reprIndex = 0
    private var i = 0
    private var index = -1


    fun addIfBetter(v: Pair<Float, ObjectNode>) {
        val index = ranks.indexOfFirst { v.first < it.first }
        if (index >= 0 ) {
            ranks.add(index, v)
            if (ranks.size > k) ranks.removeAt(k)
        }
        else if(ranks.size < k){
            ranks.add(v)
        }
    }

    fun classify(values: List<Float>): String {
        val repr = findRepresentationValue(values.first())

        reprIndex = attributes.indexOf(repr)
        smallerIndex = reprIndex - 1
        greaterIndex = reprIndex + 1

        while (nextIterationCondition(repr)) {
            attributeObjectsDistance(values, attributes[i])
        }

        return ranks.map { it.second.values.last() as ClassAttributeValue }.groupBy { it.value }.maxBy { e -> e.value.size }!!.key
    }

    private fun getNextIndex(): Int {
        index++
        return when {
            index == 0 -> reprIndex
            index % 2 == 0 && greaterIndex < attributes.size-> greaterIndex++
            smallerIndex >= 0-> smallerIndex--
            else -> -1
        }

    }

    private fun nextIterationCondition(repr: FloatAttributeValue): Boolean {
        i = getNextIndex()
        return i != -1 && !(ranks.size >= k && ranks.last().first < repr.value - attributes[i].value)
    }


    private fun attributeObjectsDistance(values: List<Float>, a: FloatAttributeValue) {
        a.objects.forEach {
            var sum = 0f
            for (i in 1 until values.size)
                sum += (values[i] - it.values[i].value as Float).pow(2)
            addIfBetter(Pair(sqrt(sum), it))
        }
    }


    private fun findRepresentationValue(value: Float): FloatAttributeValue {
        return attributes.find { it.value == value } ?: run {
            attributes
                .fold(attributes[0], { acc, v ->
                    if (abs(value - v.value) < abs(value - acc.value)) v
                    else acc
                })
        }
    }
}
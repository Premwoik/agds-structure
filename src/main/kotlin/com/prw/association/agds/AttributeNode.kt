package com.prw.association.agds

import kotlin.math.abs


class NumberAttributeNode(name: String) : AttributeNode<Float>(name) {
    override fun wrapValue(v: Float): AttributeValue<Float> {
        return FloatAttributeValue(v, this)
    }

    fun computeSimilarity(value: AttributeValue<Float>, precision: Int) {
        val pos = values.indexOf(value)
        for (i in pos until getHighRange(pos, precision)) {
            computeNeighborSimilarity(i, i + 1)
        }
        for (i in pos downTo getLowRange(pos, precision) + 1) {
            computeNeighborSimilarity(i, i - 1)
        }

    }

    private fun getHighRange(pos: Int, precision: Int): Int {
        if (precision == -1) return values.size - 1
        return if (pos + precision < values.size) pos + precision else values.size - 1

    }

    private fun getLowRange(pos: Int, precision: Int): Int {
        if (precision == -1) return 0
        return if (pos - precision < 0) 0 else pos - precision
    }


    private fun computeNeighborSimilarity(main: Int, neigh: Int) {
        values[neigh].similarity = values[main].similarity * computeWeight(main, neigh)
    }


    private fun computeWeight(v1: Int, v2: Int): Float {
        return 1 - abs(values[v1].value - values[v2].value) / (values.last().value - values.first().value)
    }


}

class ClassAttributeNode(name: String) : AttributeNode<String>(name) {
    override fun wrapValue(v: String): AttributeValue<String> {
        return ClassAttributeValue(v, this)
    }
}

abstract class AttributeNode<T>(val name: String) : Node() {

    var values: MutableList<AttributeValue<T>> = mutableListOf()

    abstract fun wrapValue(v: T): AttributeValue<T>


    fun addValue(value: T, obj: ObjectNode): AttributeValue<T> {
        return values.find { it.value == value }
            ?.apply {
                this.count++
                this.objects.add(obj)
            }
            ?: run {
                val newValue = wrapValue(value)
                newValue.objects.add(obj)
                values.add(newValue)
                if (newValue.isNumerical()) {
                    values.sortBy { it.value as Float }
                }
                newValue
            }
    }

    fun removeValue(value: T, obj: ObjectNode): AttributeValue<T>? {
        return values.find { it.value == value }
            ?.apply {
                if (this.count > 1) {
                    this.count--
                    this.objects.remove(obj)
                } else {
                    values.remove(this)
                }
            }
    }

    fun hasValue(value: T): Boolean {
        return values.any { it.value == value }
    }
}
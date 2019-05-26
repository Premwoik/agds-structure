package com.prw.association.agds


class ObjectNode(var values: List<AttributeValue<*>>): Node() {

    fun sumUpSimilarity(): Float{
        val prt =  1f / values.size
        this.similarity = values.map { it.similarity * prt }.sum()
        return similarity
    }

    fun isThisObject(values: List<Any>): Boolean{
        return this.values.zip(values).sumBy { (attr, value) -> if (attr.value == value) 1 else 0} == values.size
    }

}
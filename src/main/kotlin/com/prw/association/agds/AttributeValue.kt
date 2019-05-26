package com.prw.association.agds


class FloatAttributeValue(t: Float, attrNode: NumberAttributeNode) : AttributeValue<Float>(t, attrNode){
    override val attributeNode: NumberAttributeNode = attrNode
}


class ClassAttributeValue(t: String, attrNode: ClassAttributeNode) : AttributeValue<String>(t, attrNode){
   override val attributeNode: ClassAttributeNode = attrNode
}

abstract class AttributeValue<T>(t: T, attrNode: AttributeNode<T>): Node() {
    var count: Int = 0
    val value: T = t
    open val attributeNode: AttributeNode<T> = attrNode
    var objects: MutableList<ObjectNode> = mutableListOf()

    fun isNumerical(): Boolean{
        return value is Float
    }

    fun computeNeighborSimilarity(){

    }


}
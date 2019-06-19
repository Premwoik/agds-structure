package com.prw.association.agds

import com.prw.association.agds.classifier.Classifier

class AGDS(attributes: List<AttributeNode<Any>>) {

    val attributes: List<AttributeNode<Any>> = attributes
    var objects: MutableList<ObjectNode> = mutableListOf()


    fun addObject(values: List<Any>): ObjectNode {
        val obj = ObjectNode(mutableListOf())
        val attrs = attributes
            .zip(values)
            .map { (attr, value) -> attr.addValue(value, obj) }
        obj.values = attrs
        objects.add(obj)
        return obj

    }

    fun removeObject(values: List<Any>): Boolean {
        getObjectIfExist(values)?.apply {
            attributes
                .zip(values)
                .map { (attr, value) -> attr.removeValue(value, this) }
            return objects.remove(this)
        }
        return false
    }

    fun removeObject(obj: ObjectNode): Boolean {
        return objects.remove(obj)
    }

    fun findSimilar(obj: ObjectNode): ObjectNode {
        return findSimilar(obj, 1).first()
    }

    fun findSimilar(obj: ObjectNode, size: Int): List<ObjectNode> {
        obj.values.onEach { it.similarity = 1f }
            .filter { it is FloatAttributeValue }.map { it as FloatAttributeValue }
            .forEach {
                it.attributeNode.computeSimilarity(it, -1)
            }
        val result = objects.onEach { it.sumUpSimilarity() }.sortedBy { it.similarity }.reversed()
        return if (size > -1) result.take(size) else result
    }


    fun findSimilar(values: List<Any>): List<ObjectNode> {
        val obj = addObject(values)
        val res = findSimilar(obj, -1).subList(1, objects.size)
        removeObject(obj)
        return res
    }


    fun classify(values: List<Float>, k: Int = 3): String {
        val firstAttrValues = attributes[0].values as List<FloatAttributeValue>
        val classifier = Classifier(k, firstAttrValues)
        return classifier.classify(values)
    }


    private fun getObjectIfExist(values: List<Any>): ObjectNode? {
        val hasItems = attributes.zip(values)
            .map { (attr, value) -> attr.hasValue(value) }
            .sumBy { if (it) 1 else 0 }
        if (hasItems == attributes.size) {
            return objects.find { it.isThisObject(values) }
        }
        return null
    }


}
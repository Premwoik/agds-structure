package com.prw.association.agds


class NeighborhoodEdge(override val a: AttributeValue<Any>, override val b: AttributeValue<Any>) : Edge(a, b)

class SimilarityEdge(override val a: Node, override val b: Node) : Edge(a, b){
    var similarity = 0f
}

class ObjectEdge(override val a: AttributeValue<Any>, override val b: ObjectNode) : Edge(a, b)


open class Edge(open val a: Node, open val b: Node)

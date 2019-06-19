package com.prw.association.agds

import com.prw.association.agds.data.Iris
import org.junit.Test
import org.junit.Assert.*
import kotlin.math.round


internal class AGDSTest {

    @Test
    fun addObjectIris() {
        val iris = Iris()
        val data = iris.readDefaultData()
        val agds = iris.agds
        data.forEach { agds.addObject(it) }
        assertEquals("object number:", data.size, agds.objects.size)
        assertEquals("attributes number", 5, agds.attributes.size)
    }


    @Test
    fun findSimilarToObject() {
        val iris = Iris()
        val data = iris.readDefaultData()
        val agds = iris.agds
        data.take(3).forEach { agds.addObject(it) }
        val obj = agds.objects[1]
        val result = agds.findSimilar(obj, -1)
        assertEquals(1.000f, roundToHundreds(result[0].similarity))
        assertEquals(0.748f, roundToHundreds(result[1].similarity))
        assertEquals(0.620f, roundToHundreds(result[2].similarity))

    }

    @Test
    fun findSimilarToObjectR1() {
        val iris = Iris()
        val data = iris.readDefaultData()
        val agds = iris.agds
        data.forEach { agds.addObject(it) }
        val obj = agds.objects[1]
        val result = agds.findSimilar(obj, -1)
        assertEquals(1.000f, roundToHundreds(result[0].similarity))
        assertEquals(0.988f, roundToHundreds(result[1].similarity))
        assertEquals(0.986f, roundToHundreds(result[2].similarity))

    }

    @Test
    fun findSimilarToInputData() {
        val iris = Iris()
        val data = iris.readDefaultData()
        val agds = iris.agds
        listOf(data[0], data[2]).forEach { agds.addObject(it) }
        val inputData = data[1]
        val result = agds.findSimilar(inputData)
        assertEquals(2, agds.objects.size)
        assertEquals(2, result.size)
        assertEquals(0.748f, roundToHundreds(result[0].similarity))
        assertEquals(0.620f, roundToHundreds(result[1].similarity))
    }

    @Test
    fun findSimilarToInputDataR1() {
        val iris = Iris()
        val data = iris.readDefaultData()
        val agds = iris.agds
        listOf(data[0], data[2]).forEach { agds.addObject(it) }
        val inputData = data[1]
        val result = agds.findSimilar(inputData)
        assertEquals(2, result.size)
        assertEquals(0.748f, roundToHundreds(result[0].similarity))
        assertEquals(0.620f, roundToHundreds(result[1].similarity))
    }

    @Test
    fun classify() {
        val iris = Iris()
        val data = iris.readDefaultData()
        val agds = iris.agds
        data.forEach { agds.addObject(it) }
        val clas = agds.classify(listOf(6.8f, 2.8f, 4.8f, 1.4f))
        assertEquals("Iris-versicolor", clas)
    }

    @Test
    fun removeObject() {
        val iris = Iris()
        val data = iris.readDefaultData()
        val agds = iris.agds
        data.forEach { agds.addObject(it) }

        val toRemove = agds.objects[100]
        val res = agds.removeObject(toRemove)
        assertTrue(res)
        assertEquals(149, agds.objects.size)
        for (i in 0 until toRemove.values.size) {
            val f = agds.attributes[i].values.find { it == toRemove.values[i] }
            assertNull(f?.objects?.find { it == toRemove })
            f?.apply { assertEquals(objects.size, count) }
        }
    }


    private fun roundToHundreds(v: Float): Float {
        return round(v * 1000) / 1000
    }
}
package com.prw.association.agds.data

import org.junit.Test


internal class IrisTest {

    @Test
    fun readData() {
        val res = Iris().readData()
        System.out.println(res)
        assert(res.size > 1)
    }
}
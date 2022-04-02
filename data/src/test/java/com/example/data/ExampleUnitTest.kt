package com.example.data

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun subListTest() {

        val personList = mutableListOf<Person>()

        (1..93).forEachIndexed { index, i ->
            val rand = (1..3).random()
            val person = Person(id = "id$i", "name${rand})")
            personList.add(person)
        }

        val person0List = personList.subList(0, 10)
        val person1List = personList.subList(10, 20)
        val person2List = personList.subList(20, 30)

        var end = 105

        if (end >= personList.size) {
            end = personList.size
        }

        val person3List = personList.subList(90, end)

        person0List.forEach {
            println("id0 = ${it.id} | ${it.name}")
        }

        person1List.forEach {
            println("id1 = ${it.id} | ${it.name}")
        }

        person2List.forEach {
            println("id2 = ${it.id} | ${it.name}")
        }

        person3List.forEach {
            println("id3 = ${it.id} | ${it.name}")
        }

        println("end pos = $end")

        val testDistinct = personList.distinctBy {
            it.name
        }

        testDistinct.forEach {
            println("distinct = ${it.id} | ${it.name}")
        }

    }

    data class Person(
        val id: String,
        val name: String
    )

}
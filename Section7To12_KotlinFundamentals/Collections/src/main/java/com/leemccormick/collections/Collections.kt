package com.leemccormick.collections

fun main() {
    print("Hello Kotlin : Collections!")
    introToCollections()
    invokingMethodsOnLists()
    setAndMapCollections()
    initializingList()
    emptyCollections()
    collectionFilter()
    summaryCollections()
}

fun introToCollections() {
    println("\nI am learning about intro collections in Kotlin")

    val myListOfName = listOf("James", "Paul", "Rafael", "Gina")
    val myMutableList = mutableListOf(12, 23, 432, 231)
    myMutableList.add(34)
    myMutableList.removeAt(1)
    myMutableList.remove(432)

    println("List is a collection.")
    println("ArrayList is a collection.")
    println("listOf() is a collection. | $myListOfName ")
    println("myMutableList is $myMutableList ")

    for (item in myListOfName){
        println("item is $item")
    }

    for (item in myMutableList){
        println("item in myMutableList is $item")
    }

    myListOfName.forEach {
        println("it in forEach is $it")
    }
}

fun invokingMethodsOnLists() {
    println("\nI am learning about invoking methods on lists in Kotlin")

    val myMutableList = mutableListOf(12, 23, 432, 231)
    myMutableList.add(34)
    myMutableList.removeAt(1)
    myMutableList.remove(432)

    for (item in myMutableList){
        println("item in myMutableList is $item")
    }

    println("Number of elements is ${myMutableList.size}")
    println("Second elements is ${myMutableList.get(1)}")
    println("Second elements is ${myMutableList[1]}")
    println("Index if element \"two\" is ${myMutableList.indexOf(231)}")
}

fun setAndMapCollections() {
    println("\nI am learning about set and map collections in Kotlin")

    // Set
    val mySet = setOf("US", "MZ", "AU")
    val myMutableSet = mutableSetOf(1, 3, 4, 5)
    myMutableSet.add(3) // Does not add this one because duplicate data
    myMutableSet.add(6) // Add this one
    println("mySet is $mySet")
    println("myMutableSet is $myMutableSet")

    // Map == Dictionary
    val secretMap = mapOf("Up" to 1, "Down" to 2, "Left" to 3, "Right" to 4)
    val myMutableSecretMap = mutableMapOf("One" to 1,
                                        "Two" to 2,
                                        "Three" to 3)
    myMutableSecretMap.put("Four", 4)
    myMutableSecretMap["Five"] = 5

    println("secretMap.keys is ${secretMap.keys}")
    println("secretMap.values is ${secretMap.values}")
    println("myMutableSecretMap.keys is ${myMutableSecretMap.keys}")
    println("myMutableSecretMap.values is ${myMutableSecretMap.values}")

    if ("Up" in secretMap) println("Yes, it is in.")
    if (4 in secretMap.values) println("Yes, it is in.")
}

fun initializingList() {
    println("\nI am learning about initializing list  in Kotlin")

    val myNewList = mutableListOf<String>()
    myNewList.add("hey")
    myNewList.add("there")

    for (i in 1..10){
        myNewList.add(i, "Hey $i")
    }

    println("myNewList is $myNewList")

    val myNewImList = listOf<Int>(1, 3, 5) // Can not add more
    println("myNewImList is $myNewImList")
}

fun emptyCollections() {
    println("\nI am learning about empty collections  in Kotlin")

    val empty = emptyList<String>()
    val emptySet = emptySet<Int>()
    val emptyMap = emptyMap<Int, String>()

    println("empty : $empty")
    println("emptySet : $emptySet")
    println("emptyMap : $emptyMap")
}

fun collectionFilter() {
    println("\nI am learning about collection filters in Kotlin")

    val myListNames = listOf("Lee", "Jame", "Paul", "Gina", "testMoreThanFive", "Rafael")
    val found = myListNames.filter {
        it == "Paul"
    }

    val foundNameMoreThanFiveCharacters = myListNames.filter {
        it.length > 4
    }

    val foundStartsWith = myListNames.filter {
        it.startsWith("l")
    }

    val foundEndsWith = myListNames.filter {
        it.endsWith("a")
    }

    val foundIgnoreCase = myListNames.filter {
        it.startsWith("r", ignoreCase = true) && it.endsWith('L', ignoreCase = true)
    }

    println("myListNames is $myListNames")
    println("found is $found")
    println("foundNameMoreThanFiveCharacters is $foundNameMoreThanFiveCharacters")
    println("foundStartsWith is $foundStartsWith")
    println("foundEndsWith is $foundEndsWith")
    println("foundIgnoreCase is $foundIgnoreCase")
}

fun summaryCollections() {
    println("\nI am learning about summary collections in Kotlin")
    println("Learn more about collection here --> https://blog.kotlin-academy.com/kotlin-cheat-sheet-1137588c75a")
}
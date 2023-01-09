package com.leemccormick.loopandbranching

fun main() {
    print("Hello Kotlin Loop and Branching!")
    ifStatementAndWhenExpression()
    whenExpression()
    forLoop()
}

fun ifStatementAndWhenExpression() {
    println("\nI am learning about if statement and when expression in Kotlin")

    val amount = 1980

    if (amount == 1000){
        println("You are wealthy.")
    } else if (amount >= 1000) {
        println("Wow, you are very wealthy.")
    } else {
        println("You are getting  by.")
    }

    val amountAgain = 31432
    when (amountAgain) {
        100 -> println("You have 100.")
        125 -> println("You are getting there.")
        999 -> println("Really close.")
        1000 -> println("Rich but not there.")
        1100 -> println("You have made it.")
        else -> {
            println("$amountAgain is not going to work")
        }
    }
}

fun whenExpression() {
    println("\nI am learning about when expression in Kotlin")

    val amountAgainWithRange = 1232
    when (amountAgainWithRange) {
        in 1..100 -> println("This amount is between 1 and 100.")
        !in 10..90 -> println("This amount is outside of range.")
        999 -> println("Really close.")
        1000 -> println("Rich but not there.")
        1100 -> println("You have made it.")
        else -> {
            println("$amountAgainWithRange is not going to work")
        }
    }
}

fun forLoop() {
    println("\nI am learning about for loop in Kotlin")

    for (i in 1..1000) {
        if (i % 3 == 0) {
            println("$i is multiple by 3.")
        } else {
            println("i : $i")
        }
    }

}
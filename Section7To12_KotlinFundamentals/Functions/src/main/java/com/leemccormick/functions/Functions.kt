package com.leemccormick.functions

fun main() {
    print("Hello Kotlin : Functions!")
    simpleFunction()
    functionWithParameters()
    addingMoreParameters()
    defaultArgumentAndNamedArgument()
    functionsAndReturnTypes()
    returningABoolean()
    lambdaExpression()
    lambdaExpressionCatAgeExercise()
    itInLambdaExpression()
    lambdaExpressionReturnNothing()
    trailingLambda()
}

fun simpleFunction() {
    println("\nI am learning about simple function in Kotlin")

    sayHello()
}

fun sayHello() {
    println("This is a function.")
}

fun functionWithParameters() {
    println("\nI am learning about function with parameters in Kotlin")
    calculate(1, 100)
    calculate(300, 400)
}

fun calculate(first: Int, second: Int) {
    for (i in first..second){
        if (i%2 == 0) {
            println("$i is multiple by 2.")
        }
    }
}

fun addingMoreParameters() {
    println("\nI am learning about adding more parameters to a function in Kotlin")

    calculateWithMultipleNumber(500,550,5)
}

fun calculateWithMultipleNumber(first: Int,
                                second: Int,
                                multipleOf: Int) {
    for (i in first..second){
        if (i%multipleOf == 0) {
            println("$i is multiple by $multipleOf.")
        }
    }
}

fun defaultArgumentAndNamedArgument() {
    println("\nI am learning about default argument and named argument in Kotlin")

    calculateWithMessage(11,1000, "is multiple of", 11)
    calculateWithDefaultValues(message = "is multiple of", multipleOf = 6)
    calculateWithDefaultValues(first = 4,
                                second = 8,
                                message = "is multiple of",
                                multipleOf = 2)
}

fun calculateWithMessage(first: Int,
                                second: Int,
                                message: String = "",
                                multipleOf: Int) {
    for (i in first..second){
        if (i%multipleOf == 0) {
            println("$i $message $multipleOf.")
        }
    }
}

fun calculateWithDefaultValues(first: Int = 0,
                         second: Int = 100,
                         message: String = "",
                         multipleOf: Int) {
    for (i in first..second){
        if (i%multipleOf == 0) {
            println("$i $message $multipleOf.")
        }
    }
}

fun functionsAndReturnTypes() {
    println("\nI am learning about functions and return types in Kotlin")

    val catAge = calculateCatAge(age = 16)
    println("This cat is $catAge year old.")
}

fun calculateCatAge(age: Int) : Int{
    return  age * 7
}

fun returningABoolean() {
    println("\nI am learning about returning a boolean in Kotlin")

    val catAge = calculateCatAge(age = 1)

   if (checkAge(catAge))
       println("This cat is old.")
   else
       println("This cat is young")
}

fun checkAge(age: Int) : Boolean {
    return  age > 14
}

fun lambdaExpression() {
    println("\nI am learning about lambda expression in Kotlin")

    println("Lambda is a function without a name.")

    val a = 10
    val b = 20
    val sum : (Int, Int) -> Int = { a, b -> a + b }
    val add : (Int, Int) -> Int = { a: Int, b: Int -> a + b }
    val addWithString : (Int, Int, String) -> Int = { a, b, c ->
        println("Say something : $c")
        a + b
    }

    val total = add(2,3)
    val totalSum = sum(20, 30)
    val totalAddWithString = addWithString(1,2,"Hello")

    println("sum is $sum")
    println("add is $add")
    println("total is $total")
    println("totalSum is $totalSum")
    println("totalAddWithString is $totalAddWithString")
}

fun lambdaExpressionCatAgeExercise() {
    println("\nI am learning about lambda expression and doing exercise in Kotlin")

    val catAge: (Int) -> Int = { age -> age * 7 }
    val testCatAge = catAge(3)
    println("Cat Age is $testCatAge")
}

fun itInLambdaExpression() {
    println("\nI am learning about it in lambda expression in Kotlin")

    val catAgeUsingIt: (Int) -> Int = { it * 7 }
    val testCatAge = catAgeUsingIt(3)

    println("Cat Age using it : $testCatAge")
}

fun lambdaExpressionReturnNothing() {
    println("\nI am learning about lambda expression return nothing in Kotlin")

    // Unit == Void
    val name: (String) -> Unit = {it -> print("Name is $it")}
    val testName = name("Lee McCormick")

    println("Test Name is $testName")
}

fun trailingLambda() {
    println("\nI am learning about trailing lambda in Kotlin")

    val addAgain : (Int, Int) -> Int = { a: Int, b: Int -> a + b }
    enhancedMessage("Hello, Trailing Lambda.") {
        addAgain(12,12)
    }

    enhancedMessage("Hello, There.") {
        12
    }
}

fun enhancedMessage(message: String, funAsParameter: () -> Int) {
    val testResult = funAsParameter
    println("$message ${funAsParameter}")
    println("$message $testResult")
}
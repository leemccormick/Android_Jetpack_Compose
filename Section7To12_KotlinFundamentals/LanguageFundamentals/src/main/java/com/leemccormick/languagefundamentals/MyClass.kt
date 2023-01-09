package com.leemccormick.languagefundamentals

fun main(){
    print("Hello Kotlin!")
    kotlinVarAndValKeywords()
    initializeVariable()
    introToVariableType()
    basicType()
    longType()
    floatingType()
    kotlinOperator()
}

fun kotlinVarAndValKeywords(){
    println("\nI am learning about var and val keywords in Kotlin")

    val nameCanNotBeChanged = "Lee McCormick" // == let in Swift
    var nameCanBeChanged = "iOS Developer" // == var in Swift
    nameCanBeChanged = "Android Developer"

    println("This is name in val : $nameCanNotBeChanged")
    println("This is name in var : $nameCanBeChanged")
}

fun initializeVariable(){
    println("\nI am learning about initializing variables in Kotlin")

    val test: String
    test = "Initializing here..."
    println("Val has been initialize : $test")

    val testInference = "Initializing here by type inference"
    println("Val has been initialize by inference : $testInference")
}

fun introToVariableType() {
    println("\nI am learning about intro to variable type - Int and String in Kotlin")
    val myInt = 20
    val myName = "Lee McCormick"

    println("I am a string type and named : $myName, and my age of Int Type is : $myInt")
}


fun basicType() {
    println("\nI am learning about basic type in Kotlin")

    val aBit = "Smallest unit of data or information (0, 1)"
    val int = "A whole number, Holds 32 bits"
    val byte = "Holds 8 bits"
    var myByte: Byte
    myByte = 8
    // myByte = 128 --> Not work cause bigger than 8 bits
    val short = "Holds 16 bits"
    val long = "Holds 64 bits"

    println("Bit is $aBit")
    println("int is $int")
    println("byte is $byte : myByte is $myByte")
    println("short is $short")
    println("long is $long")
}

fun longType() {
    println("\nI am learning about long type in Kotlin")

    val long = "Holds 64 bits"
    val number = 1 // This is integer
    val oneLong = 1L // Not integer but long
    println("Long is $long | number is $number | oneLong is $oneLong")
}

fun floatingType() {
    println("\nI am learning about floating types in Kotlin")
    val float = "Decimal Number, Holds 32 bits"
    val double = "Decimal Number, Holds 64 bits" // More precise value use double

    val pi = 3.14 // Default is double type
    val piInFloat = 3.14f
    val e = 2.43532454536456534563
    val eFloat = 2.43532454536456534563F

    println("float is $float | piInFloat : $piInFloat | eFloat : $eFloat")
    println("double is $double| pi : $pi | e : $e")
}

fun kotlinOperator() {
    println("\nI am learning about operator in Kotlin")

    val a = 23
    val b = 12
    val aa = 23.0
    val bb = 12.0
    val c = a + b
    val cc = aa + bb
    val d = 10
    val e = 3
    println("a + b : ${a + b} | c is $c")
    println("a - b : ${a - b} | c is $c")
    println("a * b : ${a * b} | c is $c")
    println("a / b : ${a / b} | c is $c")

    println("aa + bb : ${aa + bb} | cc is $cc")
    println("aa - bb : ${aa - bb} | cc is $cc")
    println("aa * bb : ${aa * bb} | cc is $cc")
    println("aa / bb : ${aa / bb} | cc is $cc")
    println("d % e : ${d % e} | This is a remainder.")
}
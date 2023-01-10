package com.leemccormick.oop

fun main() {
    print("Hello Kotlin : OOP!")
    introOOP()
    classesAndConstructors()
    initBlock()
    addingAClassAndFunctionWithParameters()
    inheritanceAndOverride()
    inheritanceDesignStep()
    introToInterfaceClasses()
    interfaceClasses()
    extensionFunctions()
    dataClass()
}

fun introOOP() {
    println("\nI am learning about intro to Object Orientation Programing Language in Kotlin")

    println("Object - such as a car.")
    println("Class - Blueprint of Object.")

    val car = Car() // Initialized object
    car.color = "Blue"
    car.model = "MDF"
    car.drive()
    println("Car Color is ${car.color}")
    println("Car Model is ${car.model}")
}

class Car {
    var color: String = "Red"
    var model: String = "XMD"

    fun drive(){
        println("Drive...yoohoooo......")
    }
}

fun classesAndConstructors() {
    println("\nI am learning about classes and the primary constructor in Kotlin")

    val car = CarWithConstructor() // Initialized object
    car.drive()
    println("CarWithConstructor -> Car Color is ${car.color}")
    println("CarWithConstructor -> Car Model is ${car.model}")

    val newCar = CarWithConstructor(color = "Green", model = "LLM") // Initialized object
    val secondNewCar = CarWithConstructor(color = "Purple", model = "XXLX") // Initialized object

    car.drive()
    println("CarWithConstructor -> newCar Color is ${newCar.color}")
    println("CarWithConstructor -> newCar Model is ${newCar.model}")
    println("CarWithConstructor -> secondNewCar Color is ${secondNewCar.color}")
    println("CarWithConstructor -> secondNewCar Model is ${secondNewCar.model}")
}

class CarWithConstructor(var color: String = "Blue", var model: String = "XMDL") {
//    var color: String = color --> Do not need this because using constructor
//    var model: String = model --> Do not need this because using constructor

    fun drive(){
        println("Drive...yoohoooo......")
    }
}

fun initBlock() {
    println("\nI am learning about init block in Kotlin")

    val car = CarWithInitBlock(color = "Green", model = "LLM") // Initialized object
    car.color = "Black"
    // car.model = " QLM" --> Can not modifier because of val
    println("CarWithInitBlock -> car Color is ${car.color}")
    println("CarWithInitBlock -> car Model is ${car.model}")
}

class CarWithInitBlock(var color: String, val model: String) {

    init {
        // color = "Yellow"
        // model = "KLMMM"
        if (color == "Green") {
            println("Yayy.. Green...")
        } else {
            println("$color is not Green.")
        }
    }

    fun drive(){
        println("Drive...yoohoooo......")
    }
}

fun addingAClassAndFunctionWithParameters() {
    println("\nI am learning about adding a class function with parameters in Kotlin")

    val car = CarWithAddingAClassAndFunctionWithParameters(color = "Green", model = "LLM") // Initialized object
    car.speed(minSpeed = 100, maxSpeed = 199)
    car.drive()
    println("CarWithAddingAClassAndFunctionWithParameters -> car Color is ${car.color} | car Model is ${car.model}")
}

class CarWithAddingAClassAndFunctionWithParameters(var color: String, val model: String) {

    init {
        if (color == "Green") {
            println("Yayy.. Green...")
        } else {
            println("$color is not Green.")
        }
    }

    fun speed(minSpeed: Int, maxSpeed: Int){
        println("Min Speed : $minSpeed | and Max Speed : $maxSpeed")
    }

    fun drive(){
        println("Drive...yoohoooo......")
    }
}

fun inheritanceAndOverride() {
    println("\nI am learning about inheritance and override in Kotlin")

    val name: String // This one a class of String in Kotlin

    val truck = Truck(color = "Magenta", model = "F18")
    truck.drive()
    truck.speed(100,190)
}

open class CarWithInheritanceAndOverride(var color: String, val model: String) {

    init {
        if (color == "Green") {
            println("Yayy.. Green...")
        } else {
            println("$color is not Green.")
        }
    }

    open fun speed(minSpeed: Int, maxSpeed: Int){
        println("Min Speed : $minSpeed | and Max Speed : $maxSpeed")
    }

    open fun drive(){
        println("Drive...yoohoooo......")
    }
}

class Truck(color: String, model: String) : CarWithInheritanceAndOverride(color, model) {
    override fun speed(minSpeed: Int, maxSpeed: Int) {
        val fullSpeed = minSpeed * maxSpeed
            println("Min Speed : $minSpeed | and Max Speed : $maxSpeed | Full Speed : $fullSpeed")
    }

    override fun drive() {
        super.drive()
        println("Vroommm...Like a truck.")
    }
}

fun inheritanceDesignStep() {
    println("\nI am learning about inheritance design step in Kotlin")

    println("Look, Design, Decide --> Common attribute and behavior")
}

fun introToInterfaceClasses() {
    println("\nI am learning about intro to interface classes in Kotlin")

    println("For example --> Click Event (interface)")
    println("interface --> a specific what class can implement")
}

fun interfaceClasses() {
    println("\nI am learning about interface classes in Kotlin")

    val button = Button(label = "Button")
    button.onClick("This is a button.")

    val superMario = Character(name = "Super Mario")
    superMario.onClick("This is an actor.")
}

class Button(val label: String) : ClickEvent {
    override fun onClick(message: String) {
        println("Button --> Clicked by $label and here is a message : $message")
    }
}

class Character(val name: String) : ClickEvent {
    override fun onClick(message: String) {
        println("Character --> Clicked by $name and here is a message : $message")
    }
}

interface ClickEvent {
    fun onClick(message: String)
}

fun extensionFunctions() {
    println("\nI am learning about extension functions in Kotlin")
    println("append : ${"Hello Jenny".append("Friend :)")}")
    println("removeFirstAndLastChars : ${"This is fun".removeFirstAndLastChars()}")
}

fun String.append(toAppend: String): String = this.plus(toAppend)

fun String.removeFirstAndLastChars(): String = this.substring(1,this.length - 1)

// SAME AS ABOVE LINE OF CODE
//fun String.removeFirstAndLastChars(): String {
//    return this.substring(1,this.length - 1)
//}

fun dataClass() {
    println("\nI am learning about data class in Kotlin")

    val person = Person(
                name = "Joe",
                lastName = "Ball",
                age = 12)

    val listOfPeople = listOf(person,
                        Person("Lee", "McCormick", 33),
                        Person(name = "Don", lastName = "Mc", age = 31)
                        )
    println("person is $person")
    println("name is ${person.name}")
    println("lastName is ${person.lastName}")
    println("age is ${person.age}")

    listOfPeople.forEach { person ->
        println("person is $person")
        println("name is ${person.name}")
        println("lastName is ${person.lastName}")
        println("age is ${person.age}")
    }

    listOfPeople.forEach {
        println("person is $it")
        println("name is ${it.name}")
        println("lastName is ${it.lastName}")
        println("age is ${it.age}")
    }
}

data class Person(val name: String, val lastName: String, val age: Int)
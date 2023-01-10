package com.leemccormick.advanceconcept

fun main() {
    print("Hello Kotlin : Advanced Concept!")
    introToGenerics()
    usingGenerics()
    introToEnumsAndState()
    improvingEnums()
    sealedClassesCreation()
}

fun introToGenerics() {
    println("\nI am learning about intro to generics in Kotlin")

    val listOfItems = listOf("Rafael", "Gina", "George", "James")
    val finder = Finder(list = listOfItems)
    finder.findItem(element = "Gina") {
        println("Found $it")
    }

    finder.findItem(element = "") {
        println("Found $it")
    }
}

class Finder(private val list: List<String>){
    fun findItem(element: String, foundItem: (element: String?) -> Unit) {
        val itemFoundList = list.filter {
            it == element
        }

        if (itemFoundList.isNullOrEmpty()) foundItem(null) else foundItem(itemFoundList.first())
    }
}

fun usingGenerics() {
    println("\nI am learning about using generics in Kotlin")

    val listOfItems = listOf("Rafael", "Gina", "George", "James")
    val listOfNumber = listOf(23, 45, 45)

    val person = Person(
        name = "Joe",
        lastName = "Ball",
        age = 12)
    val listOfPeople = listOf(person,
        Person("Lee", "McCormick", 33),
        Person(name = "Don", lastName = "Mc", age = 31)
    )

    val finder = FinderWithGenerics(list = listOfItems)
    val newFinder = FinderWithGenerics(list = listOfNumber)
    val personFinder = FinderWithGenerics(list = listOfPeople)

    finder.findItem(element = "Gina") {
        println("Found $it")
    }

    newFinder.findItem(element = 23) {
        println("Found $it")
    }

    newFinder.findItem(element = 27683) {
        println("Found $it")
    }

    personFinder.findItem(element = person) {
        println("Found $it")
    }
}

class FinderWithGenerics<T>(private val list: List<T>){
    fun findItem(element: T, foundItem: (element: T?) -> Unit) {
        val itemFoundList = list.filter {
            it == element
        }

        if (itemFoundList.isNullOrEmpty()) foundItem(null) else foundItem(itemFoundList.first())
    }
}

data class Person(val name: String, val lastName: String, val age: Int)

fun introToEnumsAndState() {
    println("\nI am learning about intro to enum and state in Kotlin")

    var input = Result.ERROR
    getResult(result = input)

    input = Result.SUCCESS
    getResult(result = input)

    Repository.startFetch()
    getResult(result = Repository.getCurrentState())

    Repository.finishedFetch()
    getResult(result = Repository.getCurrentState())

    Repository.error()
    getResult(result = Repository.getCurrentState())
}

fun getResult(result: Result) {
    return when(result) {
        Result.SUCCESS -> println("SUCCESS | SUCCESS")
        Result.ERROR -> println("ERROR | ERROR")
        Result.IDLE -> println("IDLE | IDLE")
        Result.LOADING -> println("LOADING | LOADING")
    }
}

object Repository {
    private var loadState: Result = Result.IDLE
    private var dataFetched: String? = null

    fun startFetch() {
        println("startFetch")
        loadState = Result.LOADING
        dataFetched = "data"
    }

    fun finishedFetch() {
        println("finishedFetch")
        loadState = Result.SUCCESS
        dataFetched = null
    }

    fun error() {
        println("error")
        loadState = Result.ERROR
    }

    fun getCurrentState(): Result {
        println("getCurrentState")
        return loadState
    }
}

enum class Result {
    SUCCESS,
    ERROR,
    IDLE,
    LOADING
}

fun improvingEnums() {
    println("\nI am learning about improving enums and state in Kotlin")

    RepositoryAbstract.startFetch()
    getResultAbstract(result = RepositoryAbstract.getCurrentState())

    RepositoryAbstract.finishedFetch()
    getResultAbstract(result = RepositoryAbstract.getCurrentState())

    RepositoryAbstract.error()
    getResultAbstract(result = RepositoryAbstract.getCurrentState())
}

abstract class ResultAbstract
data class Success(val dataFetched: String?): ResultAbstract()
data class Error(val exception: Exception): ResultAbstract()
object NotLoading: ResultAbstract()
object Loading: ResultAbstract()

object RepositoryAbstract {
    private var loadState: ResultAbstract = NotLoading
    private var dataFetched: String? = null

    fun startFetch() {
        println("startFetch")
        loadState = Loading
        dataFetched = "data"
    }

    fun finishedFetch() {
        println("finishedFetch")
        loadState = Success(dataFetched)
        dataFetched = null
    }

    fun error() {
        println("error")
        loadState = Error(exception = Exception("Exception!!"))
    }

    fun getCurrentState(): ResultAbstract {
        println("getCurrentState")
        return loadState
    }
}

fun getResultAbstract(result: ResultAbstract) {
    return when(result) {
        is Error -> {
            println("Error | Error --> ${result.exception}")
        }

        is Success -> {
            println("Success | Success --> ${result.dataFetched}")
        }

        is Loading -> {
            println("Loading | Loading...")
        }

        is NotLoading -> {
            println("NotLoading | NotLoading...")
        }

        else ->   println("N/A | else not available....")
    }
}

fun sealedClassesCreation() {
    println("\nI am learning about sealed classes creation in Kotlin")

    RepositorySealed.startFetch()
    getResultSealed(result = RepositorySealed.getCurrentState())

    RepositorySealed.finishedFetch()
    getResultSealed(result = RepositorySealed.getCurrentState())

    RepositorySealed.error()
    getResultSealed(result = RepositorySealed.getCurrentState())

    RepositorySealed.customFailure()
    getResultSealed(result = RepositorySealed.getCurrentState())

    RepositorySealed.anotherCustomFailure()
    getResultSealed(result = RepositorySealed.getCurrentState())
}

sealed class ResultSealed
data class SuccessSealed(val dataFetched: String?): ResultSealed()
data class ErrorSealed(val exception: Exception): ResultSealed()
object NotLoadingSealed: ResultSealed()
object LoadingSealed: ResultSealed()

sealed class Failure: ResultSealed() {
    data class CustomFailure(val customFailure: Exception): Failure()
    data class AnotherCustomFailure(val anotherCustomFailure: NullPointerException): Failure()
}

object RepositorySealed {
    private var loadState: ResultSealed = NotLoadingSealed
    private var dataFetched: String? = null

    fun startFetch() {
        println("startFetch")
        loadState = LoadingSealed
        dataFetched = "data"
    }

    fun finishedFetch() {
        println("finishedFetch")
        loadState = SuccessSealed(dataFetched)
        dataFetched = null
    }

    fun error() {
        println("error")
        loadState = ErrorSealed(exception = Exception("Exception!!"))
    }

    fun getCurrentState(): ResultSealed {
        println("getCurrentState")
        return loadState
    }

    fun customFailure() {
        println("customFailure")
        loadState = Failure.CustomFailure(customFailure = Exception("Exception --> something went wrong."))
    }

    fun anotherCustomFailure() {
        println("anotherCustomFailure")
        loadState = Failure.AnotherCustomFailure(anotherCustomFailure = NullPointerException("NullPointerException --> something went wrong."))
    }
}

fun getResultSealed(result: ResultSealed) {
    return when(result) {
        is ErrorSealed -> {
            println("Error | Error --> ${result.exception}")
        }

        is SuccessSealed -> {
            println("Success | Success --> ${result.dataFetched}")
        }

        is LoadingSealed -> {
            println("Loading | Loading...")
        }

        is NotLoadingSealed -> {
            println("NotLoading | NotLoading...")
        }

        is Failure.CustomFailure -> {
            println("Failure.CustomFailure --> ${result.customFailure}")
        }

        is Failure.AnotherCustomFailure -> {
            println("Failure.AnotherCustomFailure --> ${result.anotherCustomFailure}")
        }
    }
}
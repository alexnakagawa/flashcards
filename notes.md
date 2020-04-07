# Kotlin

Created: Mar 22, 2020 6:19 PM
Updated: Apr 06, 2020 8:36 PM

### joinToString and IntArray and repeat

    import java.util.*
    
    fun main(args: Array<String>) {
        val scanner = Scanner(System.`in`)
        // put your code here
        val grades = IntArray(4)
        val n = scanner.nextInt()
        repeat(n) {
            grades[scanner.nextInt() - 2]++
        }
        println(grades.joinToString(separator = " "))
    }

### Ranges

    4 downTo 1 // reverse
    1..4 // inclusive
    1 until 4 // excluding upper limit
    i in 1..7 step 2 // just odds

### Iterating Through Arrays

    fun main(args: Array<String>) {
        val daysOfWeek = arrayOf("Sun", "Mon", "Tues", "Wed", "Thur", "Fri", "Sat")
     
        for (index in daysOfWeek.indices){
            println("$index: ${daysOfWeek[index]}")
        }
    
    		for (i in 0..array.lastIndex) {
            array[i] = scanner.nextInt()
        }
    }

### MutableMap

    // Does not keep natural ordering
    val marathon = mutableMapOf(1 to "John Doe", 2 to "Marty McFly", 3 to "Marie Curie")
    val marathon = mutableMapOf<Int, String>()
    marathon.clear()
    print(marathon.containsKey(2))
    print(marathon.containsValue("John Doe"))
    marathon.size
    marathon.isEmpty()

### Functional Programming and Lambdas

    val originalText = "I don't know... what to say..."
    val textWithoutDots = originalText.filter { it != '.' }

### Reading into Files

    val fileName = "src/reading.txt"
    val file = File(fileName)
    val lines = file.readText()
    print(lines)
    
    val fileName = "src/reading.txt"
    val lines = File(fileName).readLines()
    for (line in lines){
        println(line)
    }
    
    val lines = File(fileName).readBytes()
    
    // Lambda approach to read large files
    val fileName = "src/reading.txt"
    File(fileName).forEachLine { print(it) }
    
    // Charset
    val line = File(fileName).readText(Charsets.US_ASCII)
    
    //

### Data Class

Removes the need for `hashCode()` , `equals()`, `toString()` , and `copy()`  implementations

    data class Client(val name: String, val age: Int, val gender: String)

But you can still override all of them except `copy()`

    data class Client(val name: String, val age: Int, val gender: String) {
        var balance: Int = 0
     
        override fun toString(): String {
            return "Client(name='$name', age=$age, gender='$gender', balance=$balance)"
        }
     
    }
    // Copying objects with different parameters
    fun main() {
        val bob = Client("Bob", 29, "Male")
        val john = bob.copy(name = "John")
        println(bob)
        println(john)
    }
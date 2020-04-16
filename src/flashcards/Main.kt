package flashcards
import java.io.File
import java.util.Scanner

/**
 * Takes an [input] and returns a function to run in correspondance to it.
 * @return a function to run that takes in a Scanner and a MutableList<Triple<String, String, Int>>
 */
fun menuSelect(input: String): (Scanner, MutableList<Triple<String, String, Int>>) -> Unit {
    return when (input) {
        "add" -> ::addCard
        "remove" -> ::removeCard
        "import" -> ::importDeck
        "export" -> ::exportDeck
        "ask" -> ::ask
        "hardest card" -> ::hardestCard
        "reset stats" -> ::resetStats
        else -> {throw Exception("Invalid command.")}
    }
}

/**
 * Adds a card to the [cards] deck using the scanner for user input
 */
fun addCard(scanner: Scanner, cards: MutableList<Triple<String, String, Int>>) {
    println("The card:")
    val term : String = scanner.nextLine()
    if (cards.map { it.first }.contains(term)) {
        println("The card \"$term\" already exists.")
        return
    }
    println("The definition of the card:")
    val definition : String = scanner.nextLine()
    if (cards.map { it.second }.contains(definition)) {
        println("The definition \"$definition\" already exists.\n")
        return
    }
    cards.add(Triple(term, definition, 0))
    println("The pair (\"$term\":\"$definition\") has been added.\n")
}

/**
 * Removes a card from the [cards] deck using the [scanner] for user input
 */
fun removeCard(scanner: Scanner, cards: MutableList<Triple<String, String, Int>>) {
    println("The card:")
    val term = scanner.nextLine()
    val atIndex = cards.map { it.first }.indexOf(term)
    if (atIndex == -1) {
        println("Can't remove \"$term\": there is no such card.\n")
    } else {
        cards.removeAt(atIndex)
        println("The card has been removed.\n")
    }
}

/**
 * Imports a deck of terms and definitions into [cards] using [scanner] to
 * bring in the file name.
 */
fun importDeck(scanner: Scanner, cards: MutableList<Triple<String, String, Int>>) {
    println("File name:")
    val fileName = scanner.nextLine()
    readFileAndImport(fileName, cards)
}

/**
 * Imports a deck of terms and definitions into [cards] using a command line argument that
 * has a [fileName] to import from.
 */
fun readFileAndImport(fileName: String, cards: MutableList<Triple<String, String, Int>>) {
    val file = File("src/$fileName")

    if (!file.exists()) {
        println("File not found.\n")
        return
    }

    val lines = file.readLines()
    for (i in lines.indices step 3){
        val existingIdx = cards.map { it.first }.indexOf(lines[i])
        if (existingIdx != -1) {
            cards[existingIdx] = cards[existingIdx].copy(second = lines[i+1], third = lines[i+2].toInt())
        } else {
            cards.add(Triple(lines[i], lines[i+1], lines[i+2].toInt()))
        }
    }
    println("${lines.size/3} cards have been loaded.\n")
}

/**
 * Exports the [cards] by specifying a file name in an input into [scanner]
 */
fun exportDeck(scanner: Scanner, cards: MutableList<Triple<String, String, Int>>) {
    println("File name:")
    val fileName = scanner.nextLine()
    createFileAndExport(fileName, cards)

}

/**
 * Takes a [fileName] and creates a new txt file with the saved [cards] deck
 */
fun createFileAndExport(fileName: String, cards: MutableList<Triple<String, String, Int>>) {
    val file = File("src/$fileName")
    val fileText = cards.joinToString(separator = "\n") {"${it.first}\n${it.second}\n${it.third}"}
    file.writeText(fileText)
    println("${cards.size} cards have been saved.\n")
}

/**
 * Tests the user a certain number of times on the terms in the [cards] deck
 */
fun ask(scanner: Scanner, cards: MutableList<Triple<String, String, Int>>) {
    println("How many times to ask:")
    val times = scanner.nextInt()
    scanner.nextLine()

    val terms = cards.map {it.first}
    val definitions = cards.map {it.second}
    var guess: String
    for (i in 0 until times) {
        val idx = i % cards.size
        println("Print the definition of \"${terms[idx]}\":")
        guess = scanner.nextLine()
        when (val guessIdx = definitions.indexOf(guess)) {
            idx -> println("Correct answer.")
            -1 -> {
                println("Wrong answer. The correct one is \"${definitions[idx]}\".")
                cards[idx] = cards[idx].copy(third = cards[idx].third + 1)
            }
            else -> {println("Wrong answer. The correct one is \"${definitions[idx]}\", " +
                    "you've just written the definition of \"${terms[guessIdx]}\"")}
        }
    }
}


/**
 * Takes a log of all inputted actions during the session, and outputs [actionLog] to
 * a filename specified in user input with [scanner]
 */
fun log(scanner: Scanner, actionLog: ArrayList<String>) {
    println("File name:")
    val fileName = scanner.nextLine()
    val file = File("$fileName")
    val fileString = actionLog.joinToString(separator = "\n")
    file.writeText(fileString)
    println("The log has been saved.")
}

/**
 * Prints the card(s) with the highest number of errors when asked.
 */
@Suppress("unused")
fun hardestCard(scanner: Scanner, cards: MutableList<Triple<String, String, Int>>) {
    val worstCards: MutableList<String> = mutableListOf()
    var currentWorstError: Int = 0
    @Suppress("unused")
    for ((term, definition, numErrors) in cards) {
        if (numErrors > currentWorstError) {
            currentWorstError = numErrors
            worstCards.clear()
            worstCards.add(term)
        } else if (numErrors == currentWorstError && numErrors != 0) {
            worstCards.add(term)
        } else {
            continue
        }
    }
    when (worstCards.size) {
        0 -> println("There are no cards with errors.")
        1 -> println("The hardest card is \"${worstCards[0]}\". You have $currentWorstError errors answering it.")
        else -> { println("The hardest cards are ${worstCards.joinToString{ "\"$it\"" }}. " +
                "You have $currentWorstError errors answering them.") }
    }
}

/**
 * Reset the error counters for each of the cards in [cards]
 */
@Suppress("unused")
fun resetStats(scanner: Scanner, cards: MutableList<Triple<String, String, Int>>) {
    cards.replaceAll { it.copy(third = 0) }
    println("Card statistics has been reset.")
}

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)
    var cards: MutableList<Triple<String, String, Int>> = ArrayList()
    val validActions = mutableSetOf("add", "remove", "import", "export", "ask",
            "exit", "log", "hardest card", "reset stats")
    val actionLog = arrayListOf<String>()

    var action: (Scanner, MutableList<Triple<String, String, Int>>) -> Unit
    var exitProgram: Boolean = false
    var exportFinal: Boolean = false
    var exportFileName: String = ""


    // Initial check to see if there's an import command line argument
    for (i in args.indices step 2) {
        when (args[i]) {
            "-import" -> {
                readFileAndImport(args[i + 1], cards)
            }
            "-export" -> {
                exportFinal = true
                exportFileName = args[i + 1]
            }
        }
    }

    while (!exitProgram) {
        println("Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):")
        val input = scanner.nextLine()
        actionLog.add(input)
        if (!validActions.contains(input)) {
            continue
        } else if (input == "exit") {
            println("Bye bye!")
            if (exportFinal) createFileAndExport(exportFileName, cards)
            return
        } else if (input == "log") {
            log(scanner, actionLog)
        } else {
            action = menuSelect(input)
            action(scanner, cards)
        }
    }
}

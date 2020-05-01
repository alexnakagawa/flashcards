# Command Line Flashcards
---

This repository contains the source code and logic for a "Flashcards"-like script you can run inside of your own command line. 

This was a project I completed as part of the [JetBrains Academy](https://www.jetbrains.com/academy/) 30-Day Coding Challenge. I had heard the benefits of Kotlin for so long that I wanted to try it out for myself. I've left the course with a really strong understanding of the ergonomics of the language and how it makes up for a lot of the short-comings that Java has. If you're interested in the types of things I learned along the way, look under [`notes.md`](./notes.md)

## Running the Program
1. Spin up your machine's terminal. Clone the repository to anywhere you'd like.
```git clone https://github.com/alexnakagawa/flashcards.git```
2. Set your working directory as follows: `cd src || cd flashcards`
3. Compile using Kotlin's [command line compiler](https://kotlinlang.org/docs/tutorials/command-line.html): `kotlinc flashcards.kt -d flashcards.jar`
4. Run the application: `$ java -jar flashcards.jar`

## Versions
This program was tested on [JDK 8](https://docs.oracle.com/javase/8/docs/technotes/guides/install/install_overview.html), [Kotlin 1.3.70](https://blog.jetbrains.com/kotlin/2020/03/kotlin-1-3-70-released/?_ga=2.71639642.892768004.1588234421-1423448815.1573170531)

## LICENSE
This project is distributed under the [MIT License](https://opensource.org/licenses/MIT). Feel free to do with the code as you wish, with the proper license displayed.

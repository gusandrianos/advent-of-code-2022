object Problem1 {

    fun a() {
        println("Problem 1A: " +
                stringFromFile("inputs/problem1.txt").split("\n\n")
                    .map { it.split("\n") }.maxOfOrNull { it.sumOf { it.toInt() } }.toString()
        )
    }

    fun b() {
        println("Problem 1B: " +
                stringFromFile("inputs/problem1.txt").split("\n\n")
                    .map { it.split("\n") }
                    .map { it.sumOf { it.toInt() } }
                    .sorted().takeLast(3).sum().toString()
        )
    }
}
object Problem3 {

    fun a() {
        val dictionary = createDictionary()
        println("Problem 3A: " + stringFromFile("inputs/problem3.txt").lines().map { it.chunked(it.length / 2) }
            .sumOf { dictionary[it.chuckIntersect(2)] ?: 0 })
    }

    fun b() {
        val dictionary = createDictionary()
        val chunkSize = 3
        val result =
            stringFromFile("inputs/problem3.txt").lines().chunked(chunkSize).sumOf {
                dictionary[it.chuckIntersect(3)] ?: 0
            }
        println("Problem 3B: $result")
    }

    private fun List<String>.chuckIntersect(chunkSize: Int): Char {
        var compareWith = this[0].toList().toSet()
        for (i in 1 until chunkSize)
            compareWith = compareWith.intersect(this[i].toList().toSet())
        return compareWith.first()
    }

    private fun createDictionary(): Map<Char, Int> {
        val dictionary: MutableMap<Char, Int> = mutableMapOf()
        for (i in 1..26) {
            dictionary += 'a' + i - 1 to i
            dictionary += 'A' + i - 1 to i + 26

        }
        return dictionary
    }
}
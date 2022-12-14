object Problem6 {

    fun a() {
//        val msg = stringFromFile("inputs/problem6_example.txt").toCharArray()
        val msg = stringFromFile("inputs/problem6.txt").toCharArray()
        findConsecutiveChars(msg, 4, 'A')
    }

    fun b() {
//        val msg = stringFromFile("inputs/problem6_example.txt").toCharArray()
        val msg = stringFromFile("inputs/problem6.txt").toCharArray()
        findConsecutiveChars(msg, 14, 'B')
    }

    private fun findConsecutiveChars(msg: CharArray, numberOfConsecutiveChars: Int, problem: Char) {
        val sequenceOfChars = mutableSetOf<Char>()
        var startingIndex = 0
        var foundIndex = 0
        while (sequenceOfChars.size < numberOfConsecutiveChars) {
            for (i in startingIndex until msg.size) {
                if (sequenceOfChars.size == numberOfConsecutiveChars) {
                    foundIndex = i
                    break
                } else if (!sequenceOfChars.add(msg[i])) {
                    sequenceOfChars.clear()
                    msg.drop(msg.indexOf(msg[i]))
                    startingIndex++
                    break
                }
            }
        }
        println("Problem 6$problem: $foundIndex")
    }
}
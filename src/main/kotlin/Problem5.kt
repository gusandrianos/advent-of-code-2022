object Problem5 {

    /*
    [Q] [J]                         [H]
    [G] [S] [Q]     [Z]             [P]
    [P] [F] [M]     [F]     [F]     [S]
    [R] [R] [P] [F] [V]     [D]     [L]
    [L] [W] [W] [D] [W] [S] [V]     [G]
    [C] [H] [H] [T] [D] [L] [M] [B] [B]
    [T] [Q] [B] [S] [L] [C] [B] [J] [N]
    [F] [N] [F] [V] [Q] [Z] [Z] [T] [Q]
     1   2   3   4   5   6   7   8   9
     */

    private fun getStacks(): List<ArrayDeque<String>> {
        val stacksPart = stringFromFile("inputs/problem5.txt").split("\n\n").first()
//        val stacksPart = stringFromFile("inputs/problem5_example.txt").split("\n\n").first()
        val numberOfStacks = stacksPart.lines().last().split(" ").max().toInt()
        val stacks = stacksPart.lines().dropLast(1).reversed()
        val stackList = mutableListOf<ArrayDeque<String>>()
        for (i in 0 until numberOfStacks) {
            stackList.add(ArrayDeque())
        }
        for (i in stacks.indices) {
            val lineChunked = stacks[i].chunked(4)
            for (j in lineChunked.indices) {
                if (lineChunked[j].trim().isNotBlank()) stackList[j].add(lineChunked[j].trim())
            }
        }
        return stackList
    }

    private fun getDirections(): List<Triple<Int, Int, Int>> {
        val directionsPart = stringFromFile("inputs/problem5.txt").split("\n\n").last()
//        val directionsPart = stringFromFile("inputs/problem5_example.txt").split("\n\n").last()
        val directions = directionsPart.lines().map {
            val splitFrom = it.split("from")
            Triple(
                splitFrom.first().trim().split(" ").last().toInt(),
                splitFrom.last().trim().split("to").first().trim().toInt(),
                splitFrom.last().trim().split("to").last().trim().toInt(),
            )
        }
        return directions
    }

    fun a() {
        val directions = getDirections()
        val stacks = getStacks()
        for (i in directions) {
            for (j in 0 until i.first) {
                stacks[i.third - 1].addLast(stacks[i.second - 1].last())
                stacks[i.second - 1].removeLast()
            }
        }
        println("Problem 5A: ${stacks.map { it.last() }}")
    }

    fun b() {
        val directions = getDirections()
        val stacks = getStacks()
        for (i in directions) {
            stacks[i.third - 1].addAll(stacks[i.second - 1].takeLast(i.first))
            for (j in 0 until i.first) {
                stacks[i.second - 1].removeLast()
            }
        }
        println("Problem 5B: ${stacks.map { it.last() }}")

    }
}
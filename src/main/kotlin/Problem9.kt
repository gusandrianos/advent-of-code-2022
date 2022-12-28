import kotlin.math.abs

object Problem9 {

    fun a() {
        val input = stringFromFile("inputs/problem9.txt").lines().map { it.split(" ") }
            .map { it.first().single() to it.last().toString().toInt() }
        var head = Knot(id = 0)
        var tail = Knot(id = 1)

        for (move in input) {
            val headTail = moveInDirection(move, head, tail)
            head = headTail.first
            tail = headTail.second
        }

        println("Problem 9A: ${tail.positionsVisited.count()}")
    }

    fun b() {
        val input = stringFromFile("inputs/problem9.txt").lines().map { it.split(" ") }
            .map { it.first().single() to it.last().toString().toInt() }
        var knots: MutableList<Knot> = mutableListOf()
        for (i in 0 until 10) knots.add(Knot(id = i))
        for (move in input) knots = moveRopeInDirection(move, knots).toMutableList()
        println("Problem 9B: ${knots.last().positionsVisited.count()}")
    }

    private fun moveInDirection(
        move: Pair<Char, Int>, head: Knot, tail: Knot
    ): Pair<Knot, Knot> {
        return if (move.second > 0) {
            head.move(move.first)
            tail.followKnot(head)
            moveInDirection(move.copy(second = move.second - 1), head, tail)
        } else {
            Pair(head, tail)
        }
    }

    private fun moveRopeInDirection(
        move: Pair<Char, Int>, knots: List<Knot>
    ): List<Knot> {
        repeat(move.second) {
            knots.first().move(move.first)
            for (i in 1 until knots.size) knots[i].followKnot(knots[i - 1])
        }
        return knots
    }

    data class Knot(
        private var x: Int = 0,
        private var y: Int = 0,
        private var id: Int,
        val positionsVisited: MutableSet<Pair<Int, Int>> = mutableSetOf(Pair(0, 0))
    ) {
        fun move(direction: Char) {
            when (direction) {
                'U' -> moveUp()
                'D' -> moveDown()
                'L' -> moveLeft()
                else -> moveRight()
            }
        }

        fun followKnot(knot: Knot) {
            if (abs(y - knot.y) > 1 || abs(x - knot.x) > 1) {
                if (x == knot.x) {
                    if (y - knot.y > 1) moveDown() else moveUp()
                } else if (y == knot.y) {
                    if (x - knot.x > 1) moveLeft() else moveRight()
                } else {
                    followKnotHorizontally(knot)
                    followKnotVertically(knot)
                }
                positionsVisited += Pair(y, x)
            }
        }

        private fun moveUp() {
            y += 1
        }

        private fun moveDown() {
            y -= 1
        }

        private fun moveRight() {
            x += 1
        }

        private fun moveLeft() {
            x -= 1
        }

        private fun followKnotHorizontally(knot: Knot) {
            if (x - knot.x >= 1) moveLeft() else if (knot.x - x >= 1) moveRight()
        }

        private fun followKnotVertically(knot: Knot) {
            if (y - knot.y >= 1) moveDown() else if (knot.y - y >= 1) moveUp()
        }
    }
}
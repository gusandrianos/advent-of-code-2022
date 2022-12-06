import Problem2.Move.*
import Problem2.Outcome.*

object Problem2 {

    fun a() {
        val score = getLetterPairs().sumOf { resolveRoundScoreA(it.first(), it.last()) }
        println("Problem 2A: $score")
    }

    fun b() {
        val score = getLetterPairs().sumOf { resolveRoundScoreB(it.first(), it.last()) }
        println("Problem 2B: $score")
    }

    private fun getLetterPairs() =
        stringFromFile("inputs/problem2.txt").split("\n").map { it.split(" ") }


    // Variant A
    private fun resolveRoundScoreA(opponentLetter: String, myLetter: String): Int {
        val opponentMove = resolveMoveA(opponentLetter)
        val myMove = resolveMoveA(myLetter)
        return resolveMoveScoreA(opponentMove, myMove) + myMove.score
    }

    private fun resolveMoveScoreA(opponentMove: Move, myMove: Move): Int = when (opponentMove to myMove) {
        ROCK to ROCK -> DRAW.score
        ROCK to PAPER -> WIN.score
        ROCK to SCISSORS -> LOSE.score
        PAPER to ROCK -> LOSE.score
        PAPER to PAPER -> DRAW.score
        PAPER to SCISSORS -> WIN.score
        SCISSORS to ROCK -> WIN.score
        SCISSORS to PAPER -> LOSE.score
        else -> DRAW.score
    }

    private fun resolveMoveA(letter: String): Move = when (letter) {
        "A", "X" -> ROCK
        "B", "Y" -> PAPER
        "C", "Z" -> SCISSORS
        else -> UNKNOWN
    }

    // Variant B
    private fun resolveRoundScoreB(opponentLetter: String, outcomeLetter: String): Int {
        val outcome = resolveOutcome(outcomeLetter)
        return resolveMoveScoreForOutcome(resolveMoveB(opponentLetter), outcome) + outcome.score
    }

    private fun resolveMoveB(letter: String): Move = when (letter) {
        "A" -> ROCK
        "B" -> PAPER
        "C" -> SCISSORS
        else -> UNKNOWN
    }

    // Common
    private fun resolveMoveScoreForOutcome(opponentMove: Move, outcome: Outcome) = when (opponentMove to outcome) {
        ROCK to LOSE -> SCISSORS.score
        ROCK to DRAW -> ROCK.score
        ROCK to WIN -> PAPER.score
        PAPER to LOSE -> ROCK.score
        PAPER to DRAW -> PAPER.score
        PAPER to WIN -> SCISSORS.score
        SCISSORS to LOSE -> PAPER.score
        SCISSORS to DRAW -> SCISSORS.score
        SCISSORS to WIN -> ROCK.score
        else -> 0
    }

    private fun resolveOutcome(letter: String) = when (letter) {
        "X" -> LOSE
        "Y" -> DRAW
        "Z" -> WIN
        else -> LOSE
    }

    enum class Outcome(val score: Int) {
        WIN(6),
        LOSE(0),
        DRAW(3)
    }

    enum class Move(val score: Int) {
        ROCK(1),
        PAPER(2),
        SCISSORS(3),
        UNKNOWN(0)
        ;

    }
}
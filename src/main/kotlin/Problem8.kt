object Problem8 {

    fun a() {
        val input = stringFromFile("inputs/problem8.txt").lines()
            .map { line -> line.toCharArray().map { it.toString().toInt() } }
//        val input = stringFromFile("inputs/problem8_example.txt").lines()
//            .map { line -> line.toCharArray().map { it.toString().toInt() } }
        var visibleTrees = 0

        for (i in input.indices) {
            if (i == 0 || i == input.size - 1) {
                visibleTrees += input[i].size + input.size - 2
            } else {
                for (j in input[i].indices) {
                    if (j != 0 && j != (input[i].size - 1)) {

                        visibleTrees += if (checkIfVisibleInDirection(
                                ascending = false,
                                horizontal = false,
                                input = input,
                                row = i,
                                column = j,
                                currentDirectionIndex = i - 1,
                                maxIndex = input.size
                            ) || checkIfVisibleInDirection(
                                ascending = true,
                                horizontal = false,
                                input = input,
                                row = i,
                                column = j,
                                currentDirectionIndex = i + 1,
                                maxIndex = input.size
                            ) || checkIfVisibleInDirection(
                                ascending = false,
                                horizontal = true,
                                input = input,
                                row = i,
                                column = j,
                                currentDirectionIndex = j - 1,
                                maxIndex = input[i].size
                            ) || checkIfVisibleInDirection(
                                ascending = true,
                                horizontal = true,
                                input = input,
                                row = i,
                                column = j,
                                currentDirectionIndex = j + 1,
                                maxIndex = input[i].size
                            )
                        ) 1 else 0
                    }
                }
            }
        }
        println("Problem 8A: $visibleTrees")
    }

    private fun checkIfVisibleInDirection(
        ascending: Boolean,
        horizontal: Boolean,
        input: List<List<Int>>,
        row: Int,
        column: Int,
        currentDirectionIndex: Int,
        maxIndex: Int,
    ): Boolean {
        val increment = if (ascending) +1 else -1

        return if (currentDirectionIndex !in 0 until maxIndex) {
            true
        } else if ((if (horizontal) input[row][currentDirectionIndex] else input[currentDirectionIndex][column]) >= input[row][column]) {
            false
        } else {
            checkIfVisibleInDirection(
                ascending = ascending,
                horizontal = horizontal,
                input = input,
                row = row,
                column = column,
                currentDirectionIndex = currentDirectionIndex + increment,
                maxIndex = maxIndex
            )
        }
    }


    fun b() {
        val input = stringFromFile("inputs/problem8.txt").lines()
            .map { line -> line.toCharArray().map { it.toString().toInt() } }
//        val input = stringFromFile("inputs/problem8_example.txt").lines()
//            .map { line -> line.toCharArray().map { it.toString().toInt() } }

        val treesWithScores = mutableListOf<Pair<Int, Int>>()

        for (i in input.indices) {
            for (j in input[i].indices) {
                if (j != 0 && j != (input[i].size - 1)) {

                    val score = getScenicScore(
                        ascending = false,
                        horizontal = false,
                        input = input,
                        row = i,
                        column = j,
                        currentDirectionIndex = i - 1,
                        maxIndex = input.size
                    ) * getScenicScore(
                        ascending = true,
                        horizontal = false,
                        input = input,
                        row = i,
                        column = j,
                        currentDirectionIndex = i + 1,
                        maxIndex = input.size
                    ) * getScenicScore(
                        ascending = false,
                        horizontal = true,
                        input = input,
                        row = i,
                        column = j,
                        currentDirectionIndex = j - 1,
                        maxIndex = input[i].size
                    ) * getScenicScore(
                        ascending = true,
                        horizontal = true,
                        input = input,
                        row = i,
                        column = j,
                        currentDirectionIndex = j + 1,
                        maxIndex = input[i].size
                    )

                    treesWithScores.add(Pair(input[i][j], score))

                }
            }
        }
        println("Problem 8B: " + treesWithScores.maxByOrNull { it.second }!!.second)
    }

    private fun getScenicScore(
        ascending: Boolean,
        horizontal: Boolean,
        input: List<List<Int>>,
        row: Int,
        column: Int,
        currentDirectionIndex: Int,
        maxIndex: Int,
        currentScore: Int = 0,
    ): Int {
        val increment = if (ascending) +1 else -1

        return if (currentDirectionIndex !in 0 until maxIndex) {
            currentScore
        } else if ((if (horizontal) input[row][currentDirectionIndex] else input[currentDirectionIndex][column]) >= input[row][column]) {
            currentScore + 1
        } else {
            getScenicScore(
                ascending = ascending,
                horizontal = horizontal,
                input = input,
                row = row,
                column = column,
                currentDirectionIndex = currentDirectionIndex + increment,
                maxIndex = maxIndex,
                currentScore + 1
            )
        }
    }
}
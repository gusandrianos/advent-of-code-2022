object Problem4 {

    fun a() {
        var result = 0
        val dataset = stringFromFile("inputs/problem4.txt").lines().flatMap { it.split(",") }
            .map { it.split("-").map { it.toInt() } }

        for (i in dataset.indices step 2) {
            val firstRange = (dataset[i][0]..dataset[i][1])
            val secondRange = (dataset[i + 1][0]..dataset[i + 1][1])
            if (
                (firstRange.contains(dataset[i + 1][0]) && (firstRange.contains(dataset[i + 1][1]))) ||
                (secondRange.contains(dataset[i][0]) && secondRange.contains(dataset[i][1]))
            ) result++
        }

        println("Problem 4A: $result")
    }

    fun b() {
        var result = 0
        val dataset = stringFromFile("inputs/problem4.txt").lines().flatMap { it.split(",") }
            .map { it.split("-").map { it.toInt() } }

        for (i in dataset.indices step 2) {
            val firstRange = (dataset[i][0]..dataset[i][1])
            val secondRange = (dataset[i + 1][0]..dataset[i + 1][1])
            if (
                firstRange.contains(dataset[i + 1][0]) ||
                (firstRange.contains(dataset[i + 1][1])) ||
                secondRange.contains(dataset[i][0]) ||
                secondRange.contains(dataset[i][1])
            ) result++
        }

        println("Problem 4B: $result")
    }
}
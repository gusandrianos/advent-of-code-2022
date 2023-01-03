object Problem11 {

    fun a() {
        print("Problem 11A: ")
        monkeyBusiness(false, 20)
    }

    fun b() {
        print("Problem 11B: ")
        monkeyBusiness(true, 10000)
    }

    private fun monkeyBusiness(part2: Boolean, rounds: Int) {
        val input = stringFromFile("inputs/problem11.txt").split("\n\n")
        val monkeys: MutableList<Monkey> = mutableListOf()
        for (monkey in input) monkeys.add(Monkey.create(monkey))

        var lcm = 1
        if (part2) for (operand in monkeys.map { it.testOperand }) lcm *= operand

        repeat(rounds) {
            for (monkey in monkeys) {
                while (monkey.items.isNotEmpty()) {
                    monkey.updateFirstItemWorry(part2, lcm)
                    val itemToThrow = monkey.items.removeFirst()
                    if (monkey.testItem(itemToThrow)) {
                        monkeys[monkey.testSucceedsMonkey].items.add(itemToThrow)
                    } else {
                        monkeys[monkey.testFailsMonkey].items.add(itemToThrow)
                    }
                }
            }
        }
        val sortedMonkeys = monkeys.sortedBy { it.itemsInspected }

        println(sortedMonkeys.last().itemsInspected.toBigInteger() * sortedMonkeys[sortedMonkeys.size - 2].itemsInspected.toBigInteger())
    }

    data class Monkey(
        val items: MutableList<Long>,
        val operationSign: Char,
        val operand: Long?,
        val testOperand: Int,
        val testSucceedsMonkey: Int,
        val testFailsMonkey: Int,
        var itemsInspected: Int = 0,
    ) {

        fun updateFirstItemWorry(part2: Boolean, lcm: Int) {
            val old = items.first()
            val new = if (operand == null) {
                old * old
            } else {
                when (operationSign) {
                    '*' -> old * operand
                    '+' -> old + operand
                    else -> 0
                }
            }

            itemsInspected++
            if (part2) items[0] = new % lcm else items[0] = new.floorDiv(3)
        }

        fun testItem(item: Long): Boolean = item % testOperand == 0.toLong()


        companion object {
            fun create(input: String): Monkey = with(input.lines()) {
                val operation = getOperation(this[2])
                Monkey(
                    this[1].split(":")[1].split(",").map { it.trim().toLong() }.toMutableList(),
                    operation.first,
                    operation.second?.toLong(),
                    this[3].split(" ").last().toInt(),
                    this[4].split(" ").last().toInt(),
                    this[5].split(" ").last().toInt()
                )
            }

            private fun getOperation(input: String): Pair<Char, Int?> {
                val operationInParts = input.split(":")[1].trim().split(" ")
                return Pair(operationInParts[3].single(), operationInParts[4].toIntOrNull())
            }
        }
    }
}
object Problem10 {

    fun a() {
        val input = stringFromFile("inputs/problem10.txt").lines().map { it.split(" ") }
            .map { createCommand(it.first(), it.last().toString().toIntOrNull() ?: 0) }

        var x = 1
        var cycle = 1
        val signalStrengths = mutableListOf<Int>()

        for (command in input) {
            for (commandCycle in 0 until command.cycleCost) {
                if (cycle == 20 || (cycle - 20) % 40 == 0) {
                    signalStrengths.add(x * cycle)
                }
                cycle += 1
            }
            x += command.value
        }
        println("Problem 10A: ${signalStrengths.sum()}")
    }

    fun b() {

    }

    data class Command(val commandName: CommandName, val cycleCost: Int, val value: Int)

    enum class CommandName {
        ADDX,
        NOOP,
        ;
    }

    private fun createCommand(inputCommand: String, inputValue: Int): Command {
        val commandName = CommandName.valueOf(inputCommand.uppercase())

        val cycleCost = when (commandName) {
            CommandName.ADDX -> 2
            CommandName.NOOP -> 1
        }

        return Command(commandName, cycleCost, inputValue)
    }
}
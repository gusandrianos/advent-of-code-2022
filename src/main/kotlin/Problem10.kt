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

    data class Command(val commandName: CommandName, val cycleCost: Int, val value: Int)

    enum class CommandName {
        ADDX, NOOP, ;
    }

    private fun createCommand(inputCommand: String, inputValue: Int): Command {
        val commandName = CommandName.valueOf(inputCommand.uppercase())

        val cycleCost = when (commandName) {
            CommandName.ADDX -> 2
            CommandName.NOOP -> 1
        }

        return Command(commandName, cycleCost, inputValue)
    }

    fun b() {
        val input = stringFromFile("inputs/problem10.txt").lines().map { it.split(" ") }
            .map { createCommand(it.first(), it.last().toString().toIntOrNull() ?: 0) }

        var x = 1
        var cycle = 1
        val spriteWidth = 3
        val sprite = createSprite(spriteWidth, x)
        val screenWidth = 40
        val screenHeight = 6
        val screen: MutableList<MutableList<Char>> = MutableList(screenHeight) { MutableList(screenWidth) { '.' } }

        for (command in input) {
            sprite.centerPos = x
            for (commandCycle in 0 until command.cycleCost) {
                val screenRow = (cycle - 1) / screenWidth
                val currentPixel = (cycle - 1) % screenWidth
                val spriteStart = sprite.centerPos - sprite.sidePixels
                val spriteEnd = sprite.centerPos + sprite.sidePixels
                if (currentPixel in spriteStart..spriteEnd) {
                    screen[screenRow][currentPixel] = '#'
                }
                cycle += 1
            }
            x += command.value
        }

        println("Problem 10B:")
        for (line in screen) {
            for (pixel in 0 until line.size) {
                print(screen[screen.indexOf(line)][pixel])
            }
            println()
        }
    }

    // Assuming the Sprite has an odd number as a size.
    data class Sprite(val width: Int, var centerPos: Int, val sidePixels: Int)

    private fun createSprite(width: Int, centerPos: Int): Sprite = Sprite(width, centerPos, (width - 1) / 2)
}
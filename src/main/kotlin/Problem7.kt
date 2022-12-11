object Problem7 {

    fun a() {
        val dirs = getDirSizes()
        for (dir in dirs.first) dir.setValue(getDirSum(dir.key, dirs.first, dirs.second))
        println("Problem 7A: " + dirs.first.filter { it.value <= 100000 }.values.sum())
    }

    fun b() {
        val dirs = getDirSizes()
        var sizes = dirs.first
        val totalSpace = 70000000
        val availableSpace = totalSpace - sizes.values.sum()
        val requiredSpace = 30000000
        sizes = getDirSizesInclSubDirs(sizes, dirs.second)
        println("Problem 7B: " + sizes.filter { (it.value + availableSpace) >= requiredSpace }.values.min())
    }

    private fun getDirSizesInclSubDirs(
        sizes: MutableMap<String, Int>, relationships: MutableMap<String, Pair<String, MutableList<String>>>
    ): MutableMap<String, Int> {
        for (relationship in relationships) {
            sizes[relationship.key] = getDirSizeRecursively(sizes, relationships, relationship.key, relationship.value)
        }
        return sizes
    }

    private fun getDirSizeRecursively(
        sizes: MutableMap<String, Int>,
        relationships: MutableMap<String, Pair<String, MutableList<String>>>,
        dirName: String,
        relationship: Pair<String, MutableList<String>>,
    ): Int {
        var totalSum = sizes[dirName] ?: 0
        for (subdir in relationship.second)
            totalSum += if (relationships[subdir]?.second.orEmpty().isNotEmpty()) {
                getDirSizeRecursively(sizes, relationships, subdir, relationships[subdir]!!)
            } else {
                sizes[subdir] ?: 0
            }
        return totalSum
    }

    private fun getDirSizes(): Pair<MutableMap<String, Int>, MutableMap<String, Pair<String, MutableList<String>>>> {
        val input = stringFromFile("inputs/problem7.txt").lines().map { it.trim() }.toMutableList().uniqueDirNames()
//        val input =
//            stringFromFile("inputs/problem7_example.txt").lines().map { it.trim() }.toMutableList().uniqueDirNames()
        val dirs =
            input.filter { it.startsWith("$ cd") }.map { it.getDirName() }.filter { it != ".." }.associateWith { 0 }
                .toMutableMap()
        val dirRelationships =
            mutableMapOf<String, Pair<String, MutableList<String>>>("/" to Pair("/", mutableListOf()))
        var currentDir = ""

        for (line in input) {
            if (line.startsWith("$")) {
                if (line.isCdToDir()) {
                    var dirName = line.getDirName()
                    dirRelationships[dirName] = Pair(currentDir, mutableListOf())
                    currentDir = dirName
                } else if (line == "$ cd ..") {
                    currentDir = dirRelationships[currentDir]?.first.orEmpty()
                }
            } else if (line.isDir()) {
                dirRelationships[currentDir]!!.second.add(line.getDirName())
            } else {
                val split = line.split(" ").first()
                dirs[currentDir] = dirs[currentDir]!! + split.toInt()
            }
        }
        return Pair(dirs, dirRelationships)
    }

    private fun String.getDirName() = this.subSequence(4 until this.length).toString().trim()
    private fun String.isCdToDir() = this.startsWith("$ cd") && !this.endsWith("..")
    private fun String.isDir() = this.split(" ").first() == "dir"

    private fun MutableList<String>.uniqueDirNames(): List<String> {
        var currentDir = "/"
        return this.map {
            if (it.isCdToDir() && it.getDirName() != "/") {
                val commandWithAbsolutePath = "$ cd " + currentDir + it.getDirName()
                currentDir += it.getDirName() + "/"
                commandWithAbsolutePath
            } else if (it.isDir()) {
                "dir " + currentDir + it.getDirName()
            } else if (it == "$ cd ..") {
                currentDir = currentDir.dropLast(1).dropLastWhile { it != "/".single() }
                it
            } else it
        }
    }

    private fun getDirSum(
        dir: String,
        dirs: MutableMap<String, Int>,
        dirRelationships: MutableMap<String, Pair<String, MutableList<String>>>
    ): Int {
        var totalSize = dirs[dir] ?: 0
        for (subdir in dirRelationships[dir]!!.second) {
            totalSize += if (dirRelationships[subdir]?.second.orEmpty().isNotEmpty()) {
                getDirSum(subdir, dirs, dirRelationships)
            } else {
                dirs[subdir] ?: 0
            }
        }
        return totalSize
    }
}
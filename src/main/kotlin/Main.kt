import java.io.File
import java.io.InputStream
import java.nio.file.Paths

fun main() {
    Problem1.a()
    Problem1.b()
    Problem2.a()
    Problem2.b()
    Problem3.a()
    Problem3.b()
    Problem4.a()
    Problem4.b()
    Problem5.a()
    Problem5.b()
    Problem6.a()
    Problem6.b()
    Problem7.a()
    Problem7.b()
    Problem8.a()
    Problem8.b()
    Problem9.a()
    Problem9.b()
}

fun stringFromFile(filename: String): String {
    val inputStream: InputStream =
        File("${Paths.get("").toAbsolutePath()}/src/main/kotlin/${filename}").inputStream()
    return inputStream.bufferedReader().use { it.readText() }
}



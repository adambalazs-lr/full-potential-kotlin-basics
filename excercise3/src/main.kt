import java.lang.Math.pow
import kotlin.math.PI

/*

example:

-in: r=5
-out: This is a circle.
Area:
Perimeter:

-in: a=2, b=3
-out: This is a rectangual.
Area:
Perimeter:


-lexer  : r=5 -> var v[0] = 5
        : a=2, b=3 -> var v[0] = 2, v[1] = 3

possibilities[1] = [circle, square, equilateral triangle]
possibilities[2] = [rectangular, isosceles triangle]

lexer using rand to decide, maybe the variable names will be used as a weighted hint, eg.: r=1 -> circleish, a=1, squareish

*/

fun main(args: Array<String>) {
    while (true) {
        print(">")
        try {
            println(readLine()!!.replace("\\s".toRegex(), "").lexer().guess())


        } catch (e: Exception) {
            when (e) {
                is IndexOutOfBoundsException,
                is NumberFormatException,
                is LexerException -> {
                    println("Lexer error: {$e}")
                }
                else -> throw e

            }
        }

    }
}

fun Map<String, Double>.guess(): Result {
    var g = Result("I have no idea")

    when (this.size) {
        1 -> {
            when ((1..3).random()) {
                1 -> g = this.circle()
                2 -> g = this.square()
                else -> g = this.equilateralTriangle()
            }

        }
        2 -> {
            when ((1..2).random()) {
                1 -> g = this.rectangular()
                else -> g = this.isoscelesTriangle()
            }
        }
        0 -> {
            g = Result("I think bad input")
        }
    }
    return g
}

private fun Map<String, Double>.circle(): Result {
    val a = oneArgument()
    return Result(
        "I think this is a circle.",
        pow(a, 2.0) * PI,
        a * 2.0 * PI
    )
}


private fun Map<String, Double>.isoscelesTriangle(): Result {
    val (a, b) = twoArguments()
    return Result(
        "I think this is a isosceles triangle.",
        pow(a, 2.0) * PI,
        a * 2.0 + b
    )
}

private fun Map<String, Double>.rectangular(): Result {
    val (a, b) = twoArguments()

    return Result(
        "I think this is a rectangular.",
        a * b,
        2 * a + 2 * b
    )
}

private fun Map<String, Double>.twoArguments(): Pair<Double, Double> {
    val a = this.getOrDefault(this.keys.elementAt(0), -1.0)
    val b = this.getOrDefault(this.keys.elementAt(1), -1.0)
    checklist(listOf(a, b))
    return Pair(a, b)
}

private fun Map<String, Double>.oneArgument(): Double {
    val a = this.getOrDefault(this.keys.elementAt(0), -1.0)
    checklist(listOf(a))
    return a
}


private fun Map<String, Double>.equilateralTriangle(): Result {
    val a = this.getOrDefault(this.keys.elementAt(0), -1.0)
    checklist(listOf(a))
    return Result(
        "I think this is an equilateral triangle.",
        pow(3.0, 1 / 2.toDouble()) / 4 * pow(a, 2.0),
        a * 3.0
    )
}

private fun Map<String, Double>.square(): Result {
    val a = this.getOrDefault(this.keys.elementAt(0), -1.0)
    checklist(listOf(a))
    return Result(
        "I think this is a square.",
        pow(a, 2.0),
        a * 4.0
    )
}


fun String.lexer(): Map<String, Double> {
    println("I have got $this")
    return this.split(",").map { it.split("=")[0] to it.split("=")[1].toDouble() }.toMap()
}

data class Result(val guess: String, val area: Double = 0.0, val perimeter: Double = 0.0) {
    override fun toString(): String {
        return "$guess\narea=$area unit^2\nperimeter=$perimeter unit"
    }
}

fun checklist(list: List<Double>) {
    if (list.any { it <= 0.0 }) throw LexerException("I have got an unexpected number.")
}

class LexerException(s: String) : Exception(s)

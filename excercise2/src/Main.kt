fun main(args: Array<String>) {
    for (i in 1..105) {
        println("$i => ${raindrop(i)}")
    }
}

fun raindrop(i: Int): String {
    var r = ""
    when {(0 == i % 3) -> r += "Pling"}
    when {(0 == i % 5) -> r += "Plang"}
    when {(0 == i % 7) -> r += "Plong"}
    when {(r == "") -> r = i.toString()}
    return r
}
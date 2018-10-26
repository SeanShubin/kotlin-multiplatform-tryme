package com.seanshubin.kotlin.tryme.jvm.svg

import java.nio.file.Files
import java.nio.file.Paths

interface Entity {
    fun toSvg(): String
    fun toScreen(): Entity
}

data class Point(val x: Double, val y: Double) : Entity {
    val radius: Double get() = Math.sqrt(x * x + y * y)
    val angle: Double get() = Math.atan(y / x)
    override fun toScreen(): Point = Point(x + 105, 105 - y)

    override fun toSvg(): String {
        return """<circle cx="$x" cy="$y" r="5" fill="blue"/>"""
    }

    companion object {
        fun createRadiusAngle(radius: Double, angle: Double): Point {
            val x = radius * Math.cos(angle)
            val y = radius * Math.sin(angle)
            return Point(x, y)
        }
    }
}

data class Line(val p1: Point, val p2: Point) : Entity {
    override fun toScreen(): Line = Line(p1.toScreen(), p2.toScreen())

    override fun toSvg(): String {
        val x1 = p1.x
        val x2 = p2.x
        val y1 = p1.y
        val y2 = p2.y
        return """<line x1="$x1" y1="$y1" x2="$x2" y2="$y2" stroke="blue" stroke-width="4" />"""
    }

}

const val fullCircle = 2 * Math.PI

fun makePolygonPointIndexSides(index: Int, sides: Int): Point {
    val radius = 100.0
    val angle = fullCircle / sides * index
    return Point.createRadiusAngle(radius, angle)
}

fun pointsToLines(points: List<Point>): List<Line> {
    val lines = mutableListOf<Line>()
    for (i in 0 until points.size) {
        for (j in i until points.size) {
            if (i != j) {
                lines.add(Line(points[i], points[j]))
            }
        }
    }
    return lines
}

fun s(quantity: Int): String = if (quantity == 1) "" else "s"

fun svgPolygon(sides: Int): List<String> {
    val header = listOf("""<span>""", """<svg xmlns="http://www.w3.org/2000/svg" version="1.1" width="210" height="210">""")
    val footer = listOf("""</svg>""", """</span>""")
    val makePolygonPoint = { index: Int -> makePolygonPointIndexSides(index, sides) }
    val points = (0 until sides).map(makePolygonPoint)
    val lines = pointsToLines(points)
    val pointsAndLines: List<Entity> = points + lines
    val entitySvgLines = pointsAndLines.map(Entity::toScreen).map(Entity::toSvg)
    val size = lines.size
    val descriptionLines = listOf(
            """<div>$sides component${s(sides)} results in $size possible coupling${s(size)}</div>""",
            """<hr style="border-style=solid; border-width=1px"></hr>""")
    val svgLines = header + entitySvgLines + descriptionLines + footer
    return svgLines
}

fun svgLines(): List<String> {
    return (1..26).flatMap(::svgPolygon)
}

fun htmlLines(): List<String> {
    val header = listOf(
            """<!DOCTYPE html>""",
            """<html lang="en">""",
            """<head>""",
            """<meta charset="UTF-8">""",
            """<title>Factorial Complexity</title>""",
            """</head>""",
            """<body>""")
    val footer = listOf(
            """</body>""",
            """</html>"""
    )
    val svgLines = svgLines()
    return header + svgLines + footer
}

fun main(args: Array<String>) {
    val path = Paths.get("factorial-complexity.html")
    val lines = htmlLines()
    Files.write(path, lines)
    lines.forEach(::println)
}

package battleship

import battleship.controller.Controller
import battleship.model.*
import battleship.view.Console

fun main() {
    val field = Field(List(10) { List(10) { Cell(Coordinate(0, 0), State.FOG) } })
    val view = Console(field)
    val controller = Controller(view, field)
    controller.start()

}
package battleship

import battleship.controller.FieldController
import battleship.model.*
import battleship.view.FieldConsole

fun main() {
    val field = Field(List(10) { List(10) { Cell(Coordinate(0, 0), State.FOG) } })
    val view = FieldConsole(field)
    val fieldController = FieldController(view, field)
    fieldController.printField()

    val fleet = fieldController.createFleet()

}
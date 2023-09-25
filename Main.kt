package battleship

import battleship.controller.Controller
import battleship.model.Cell
import battleship.model.Coordinate
import battleship.model.Field
import battleship.model.State
import battleship.view.Console

fun main() {
    val size = 10

    val views = listOf(
        Console(Field(List(size) { List(size) { Cell(Coordinate(0, 0), State.FOG) } }), "Player 1"),
        Console(Field(List(size) { List(size) { Cell(Coordinate(0, 0), State.FOG) } }), "Player 2"),
    )

    val controllers = views.map { Controller(it) }

    var currentPlayerIndex = 0
    var lastPlayerIndex = (currentPlayerIndex + 1) % controllers.size
    while (true) {
        val currentController = controllers[currentPlayerIndex]
        val lastController = controllers[lastPlayerIndex]

        lastController.printField(true)
        currentController.printSeparator()
        currentController.printField(false)

        if (lastController.takeShot()) break

        lastPlayerIndex = currentPlayerIndex
        currentPlayerIndex = (currentPlayerIndex + 1) % controllers.size
    }
    controllers[lastPlayerIndex].printWinner()
}

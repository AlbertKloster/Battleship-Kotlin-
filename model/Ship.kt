package battleship.model

import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class Ship(shipType: ShipType, startCoordinate: Coordinate, endCoordinate: Coordinate) {
    val cells: List<Cell>

    init {
        val cells = mutableListOf<Cell>()
        if (startCoordinate.col == endCoordinate.col) {
            if (abs(startCoordinate.row - endCoordinate.row) != shipType.length - 1)
                throw RuntimeException("Error! Wrong length of the ${shipType.string}!")

            for (row in min(startCoordinate.row, endCoordinate.row)..max(startCoordinate.row, endCoordinate.row))
                cells.add(Cell(Coordinate(row, startCoordinate.col), State.SHIP))
            this.cells = cells

        } else if (startCoordinate.row == endCoordinate.row) {
            if (abs(startCoordinate.col - endCoordinate.col) != shipType.length - 1)
                throw RuntimeException("Error! Wrong length of the ${shipType.string}!")

            for (col in min(startCoordinate.col, endCoordinate.col)..max(startCoordinate.col, endCoordinate.col))
                cells.add(Cell(Coordinate(startCoordinate.row, col), State.SHIP))
            this.cells = cells

        } else throw RuntimeException("Error! Wrong ship location!")
    }
}
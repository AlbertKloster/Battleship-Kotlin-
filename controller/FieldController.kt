package battleship.controller

import battleship.model.*
import battleship.view.View

class FieldController(private val view: View, private val field: Field) {

    init {
        init()
    }

    private fun init() {
        for (j in field.value.indices) {
            for (i in field.value[j].indices) {
                val cell = field.value[j][i]
                val row = j + 1
                val col = i + 1
                cell.coordinate = Coordinate(row, col)
                cell.state = State.FOG
            }
        }
    }

    fun createFleet(): Fleet {
        val ships = mutableListOf<Ship>()
        for (shipType in ShipType.values()) {
            view.printMessage("Enter the coordinates of the ${shipType.string} (${shipType.length} cells):")
            while (true) {
                val (startString, endString) = view.enterShipCoordinates(shipType).split(' ')
                val startCoordinate = parseCoordinate(startString)
                val endCoordinate = parseCoordinate(endString)
                try {
                    val ship = Ship(shipType, startCoordinate, endCoordinate)
                    if (ship.isTooClose())
                        throw RuntimeException("Error! You placed it too close to another one.")
                    ships.add(ship)
                    break
                } catch (e: RuntimeException) {
                    println("\n${e.message} Try again:\n")
                }
            }
            setShips(ships)
            printField()
        }
        return Fleet(ships)
    }

    fun printField() {
        view.printField()
    }

    private fun Ship.isTooClose(): Boolean {
        this.cells.forEach {
            if (hasNeighbour(it))
                return true
        }
        return false
    }

    private fun hasNeighbour(cell: Cell): Boolean {
        for (row in cell.coordinate.row - 1..cell.coordinate.row + 1) {
            for (col in cell.coordinate.col - 1..cell.coordinate.col + 1) {
                if (field.value.flatten().find { it.coordinate == Coordinate(row, col) }?.state == State.SHIP)
                    return true
            }
        }
        return false
    }

    private fun parseCoordinate(string: String): Coordinate {
        return Coordinate(string.first() - 'A' + 1, string.substring(1).toInt())
    }

    private fun setShips(ships: List<Ship>) {
        ships.forEach { it.cells.forEach { getCellByCoordinate(it.coordinate)?.state = State.SHIP } }
    }

    private fun getCellByCoordinate(coordinate: Coordinate): Cell? {
        return field.value.flatten().find { it.coordinate == coordinate }
    }

}
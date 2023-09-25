package battleship.controller

import battleship.model.*
import battleship.view.View

class Controller(private val view: View) {

    private val fleet: Fleet

    init {
        init()
        fleet = createFleet()
    }

    private fun init() {
        for (j in view.field.value.indices) {
            for (i in view.field.value[j].indices) {
                val cell = view.field.value[j][i]
                val row = j + 1
                val col = i + 1
                cell.coordinate = Coordinate(row, col)
                cell.state = State.FOG
            }
        }

        view.printMessage("${view.name}, place your ships on the game field")
        printField()

    }

    private fun createFleet(): Fleet {
        val ships = mutableListOf<Ship>()
        for (shipType in ShipType.values()) {
            view.printMessage("Enter the coordinates of the ${shipType.string} (${shipType.length} cells):")
            while (true) {
                val (startString, endString) = view.enterShipCoordinates(shipType).split(' ')
                val startCoordinate = parseCoordinate(startString)
                val endCoordinate = parseCoordinate(endString)
                try {
                    val ship = Ship(view.field, shipType, startCoordinate, endCoordinate)
                    if (ship.isTooClose())
                        throw RuntimeException("Error! You placed it too close to another one.")
                    ships.add(ship)
                    break
                } catch (e: RuntimeException) {
                    view.printMessage("${e.message} Try again:")
                }
            }
            setShips(ships)
            view.printNewLine()
            printField()
        }
        view.printMessage("Press Enter and pass the move to another player")
        readln()

        return Fleet(ships)
    }

    fun printField(fogOfWar: Boolean = false) {
        view.printField(fogOfWar)
    }

    fun printSeparator() {
        view.printSeparator()
    }

    fun printWinner() {
        view.printWon()
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
                if (view.field.value.flatten().find { it.coordinate == Coordinate(row, col) }?.state == State.SHIP)
                    return true
            }
        }
        return false
    }

    private fun parseCoordinate(string: String): Coordinate {
        return Coordinate(string.first() - 'A' + 1, string.substring(1).toInt())
    }

    private fun setShips(ships: List<Ship>) {
        ships.forEach { ship ->
            ship.cells.forEach { cell ->
                getCellByCoordinate(cell.coordinate)?.state = State.SHIP
            }
        }
    }

    private fun getCellByCoordinate(coordinate: Coordinate): Cell? {
        return view.field.value.flatten().find { it.coordinate == coordinate }
    }

    fun takeShot(): Boolean {
        view.printMessage("${view.name}, it's your turn:")
        while (true) {
            try {
                val shotString = view.takeShot()
                if (!Regex("[A-J]([1-9]|10)").matches(shotString))
                    throw RuntimeException("Error! You entered the wrong coordinates!")
                val coordinate = parseCoordinate(shotString)
                val cell = getCellByCoordinate(coordinate)!!
                if (cell.state == State.SHIP || cell.state == State.HIT) {
                    cell.state = State.HIT
                    val ship = fleet.ships.find { it.cells.contains(cell) }

                    if (fleet.ships.flatMap { it.cells }.count { it.state == State.SHIP } == 0) {
                        return true
                    } else if (ship?.cells?.count { it.state == State.SHIP } == 0) {
                        view.printSank()
                    } else {
                        view.printHit()
                    }

                } else {
                    cell.state = State.MISS
                    view.printMissed()
                }
                break
            } catch (e: RuntimeException) {
                view.printMessage("${e.message} Try again:")
            }

        }
        return false
    }

}
package battleship.view

import battleship.model.Field
import battleship.model.ShipType
import battleship.model.State

class Console(override val field: Field, override val name: String) : View {
    override fun printMessage(message: String) {
        println("\n$message\n")
    }

    override fun printNewLine() {
        println()
    }

    override fun printSeparator() {
        println("---------------------")
    }

    override fun printHit() {
        println("You hit a ship!")
        pressEnter()
    }

    override fun printSank() {
        println("You sank a ship!")
        pressEnter()
    }

    override fun printMissed() {
        println("You missed!")
        pressEnter()
    }

    override fun printField(fogOfWar: Boolean) {
        val builder = StringBuilder("  1 2 3 4 5 6 7 8 9 10\n")
        for (row in field.value.indices) {
            builder.append('A' + row).append(' ')
            builder.append(field.value[row].map { if (fogOfWar && it.state == State.SHIP) State.FOG.char else it.state.char }.joinToString(" "))
            builder.append("\n")
        }
        println(builder.trim())
    }

    override fun enterShipCoordinates(shipType: ShipType): String {
        return readln()
    }

    override fun takeShot(): String {
        return readln()
    }

    override fun printWon() {
        println("You sank the last ship. You won. Congratulations!")
    }

    private fun pressEnter() {
        println("Press Enter and pass the move to another player")
        println("...")
        readln()
    }

}
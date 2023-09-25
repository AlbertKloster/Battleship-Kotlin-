package battleship.view

import battleship.model.Field
import battleship.model.ShipType

interface View {
    val field: Field
    val name: String
    fun printMessage(message: String)
    fun printNewLine()
    fun printSeparator()
    fun printHit()
    fun printSank()
    fun printMissed()
    fun printField(fogOfWar: Boolean)
    fun enterShipCoordinates(shipType: ShipType): String
    fun takeShot(): String
    fun printWon()
}
package battleship.view

import battleship.model.ShipType

interface View {
    fun printMessage(message: String)
    fun printField(fogOfWar: Boolean)
    fun enterShipCoordinates(shipType: ShipType): String
    fun takeShot(): String
}
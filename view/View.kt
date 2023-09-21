package battleship.view

import battleship.model.ShipType

interface View {
    fun printMessage(message: String)
    fun printField()
    fun enterShipCoordinates(shipType: ShipType): String
}
package battleship.view

import battleship.model.Field
import battleship.model.ShipType

class FieldConsole(private val field: Field) : View {
    override fun printMessage(message: String) {
        println(message)
    }

    override fun printField() {
        val builder = StringBuilder("  1 2 3 4 5 6 7 8 9 10\n")
        for (row in field.value.indices) {
            builder.append('A' + row).append(' ')
            builder.append(field.value[row].map { it.state.char }.joinToString(" "))
            builder.append("\n")
        }
        println(builder)
    }

    override fun enterShipCoordinates(shipType: ShipType): String {
        return readln()
    }

}
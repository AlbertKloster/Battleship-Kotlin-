package battleship.model

enum class State(val char: Char) {
    FOG('~'),
    SHIP('O'),
    HIT('X'),
    MISS('M')
}
package ru.trfx.games.chess.model

import ru.trfx.games.chess.model.piece.PieceColor

data class GameState(
    val blackState: PlayerState = PlayerState(PieceColor.Black),
    val whiteState: PlayerState = PlayerState(PieceColor.White)
) {
    fun getStateForPlayer(player: PieceColor) = when (player) {
        PieceColor.Black -> blackState
        PieceColor.White -> whiteState
    }
}

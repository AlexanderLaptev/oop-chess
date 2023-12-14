package ru.trfx.games.chess.model

import ru.trfx.games.chess.model.piece.PieceColor

class GameState {
    private var lastWhiteMove: PlayerMove? = null
    private var lastBlackMove: PlayerMove? = null

    fun getLastMoveForPlayer(color: PieceColor): PlayerMove? = when (color) {
        PieceColor.Black -> lastBlackMove
        PieceColor.White -> lastWhiteMove
    }

    fun setLastMoveForPlayer(move: PlayerMove, color: PieceColor) = when (color) {
        PieceColor.Black -> lastBlackMove = move
        PieceColor.White -> lastWhiteMove = move
    }
}

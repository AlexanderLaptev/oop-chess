package ru.trfx.games.chess.model

import ru.trfx.games.chess.model.piece.PieceColor

data class PlayerState(
    val color: PieceColor,
    var lastMove: PlayerMove? = null
)

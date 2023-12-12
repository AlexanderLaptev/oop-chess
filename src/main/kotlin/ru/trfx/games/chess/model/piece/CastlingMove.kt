package ru.trfx.games.chess.model.piece

import ru.trfx.games.chess.model.BoardSquare
import ru.trfx.games.chess.model.PlayerMove

class CastlingMove(
    king: King,
    rook: Rook,
    from: BoardSquare,
    to: BoardSquare
) : PlayerMove(king, from, to)

package domain.move;

import domain.Enum.MoveType;
import domain.board.Position;
import domain.piece.Piece;

public class EnPassantMovement extends Movement {
    private final Piece enPassantPiece;

    public EnPassantMovement(Position sourcePosition, Position targetPosition, Piece enPassantPiece) {
        super(sourcePosition, targetPosition, MoveType.EN_PASSANT);
        this.enPassantPiece =  enPassantPiece;
    }

    public Position getEnPassantPosition() {
        return this.enPassantPiece.getPiecePosition();
    }
}

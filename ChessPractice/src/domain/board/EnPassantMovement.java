package domain.board;

import domain.Enum.MoveType;
import domain.piece.Piece;

public class EnPassantMovement extends Movement {
    private Piece enPassantPiece;

    public EnPassantMovement(Piece sourcePiece, Piece targetPiece, Piece enPassantPiece) {
        super(sourcePiece, targetPiece, MoveType.EN_PASSANT);
        this.enPassantPiece =  enPassantPiece;
    }

    public Position getEnPassantPosition() {
        return this.enPassantPiece.getPiecePosition();
    }
}

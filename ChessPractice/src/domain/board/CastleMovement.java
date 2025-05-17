package domain.board;

import domain.Enum.MoveType;
import domain.Enum.Role;
import domain.piece.Piece;
import domain.piece.PieceFactory;

public class CastleMovement extends Movement {
    private Position rookSourcePosition;
    private Position rookTargetPosition;

    public CastleMovement(Position sourcePosition, Position targetPosition, Position rookSourcePosition, Position rookTargetPosition) {
        super(PieceFactory.createEmptyPiece(sourcePosition), PieceFactory.createEmptyPiece(targetPosition), MoveType.CASTLE);
        this.rookSourcePosition = rookSourcePosition;
        this.rookTargetPosition = rookTargetPosition;
    }

    public Position getRookSourcePosition() {
        return rookSourcePosition;
    }

    public Position getRookTargetPosition() {
        return rookTargetPosition;
    }

    public void setKingPiece(Piece piece) {
        this.sourcePiece = piece;
    }
    public void setRookPiece(Piece piece) {
        this.targetPiece = piece;
    }
}

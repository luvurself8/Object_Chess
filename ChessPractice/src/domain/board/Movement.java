package domain.board;

import domain.Enum.MoveType;
import domain.Enum.Role;
import domain.Enum.Team;
import domain.piece.King;
import domain.piece.Piece;


public class Movement {
    protected Piece sourcePiece;
    protected Piece targetPiece;
    protected MoveType moveType;

    public Movement (Piece sourcePiece, Piece targetPiece, MoveType moveType) {
        this.sourcePiece = sourcePiece;
        this.targetPiece = targetPiece;
        this.moveType = moveType;
    }

    public Position getSourcePosition() {
        return this.sourcePiece.getPiecePosition();
    }

    public Position getTargetPosition() {
        return this.targetPiece.getPiecePosition();
    }

    public Piece getSourcePiece() {
        return this.sourcePiece;
    }

    public Piece getTargetPiece() {
        return this.targetPiece;
    }

    public boolean isMoveType(MoveType moveType){
        return this.moveType == moveType;
    }

    public boolean isMovementCatchKing(){
        return this.targetPiece instanceof King;
    }
}

package domain.move;

import domain.Enum.MoveType;
import domain.board.Position;


public class Movement {
    protected final Position sourcePosition;
    protected final Position targetPosition;
    protected final MoveType moveType;
    protected boolean catchKing = false;

    public Movement (Position sourcePosition, Position targetPosition, MoveType moveType) {
        this.sourcePosition = sourcePosition;
        this.targetPosition = targetPosition;
        this.moveType = moveType;
    }

    public Position getSourcePosition() {
        return this.sourcePosition;
    }

    public Position getTargetPosition() {
        return this.targetPosition;
    }

    public boolean isMoveType(MoveType moveType){
        return this.moveType == moveType;
    }

    public void setCatchKing(boolean catchKing){
        this.catchKing = catchKing;
    }

    public boolean getCatchKing(){
        return this.catchKing;
    }


    @Override
    public String toString() {
        return "Movement{" +
                "sourcePosition=" + sourcePosition +
                ", targetPosition=" + targetPosition +
                ", moveType=" + moveType +
                ", catchKing=" + catchKing +
                '}';
    }
}

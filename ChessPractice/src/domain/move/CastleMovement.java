package domain.move;

import domain.Enum.MoveType;
import domain.board.Position;

public class CastleMovement extends Movement {
    private Position rookSourcePosition;
    private Position rookTargetPosition;

    public CastleMovement(Position kingSourcePosition, Position kingTargetPosition) {
        super(kingSourcePosition, kingTargetPosition, MoveType.CASTLE);
        setRookPosition();
    }

    private void setRookPosition(){
        if (this.getTargetPosition().getColumn()=='g'){
            this.rookSourcePosition = new Position(this.getTargetPosition().getRow(),'h');
            this.rookTargetPosition = new Position(this.getTargetPosition().getRow(),'f');
        }
        if (this.getTargetPosition().getColumn()=='c'){
            this.rookSourcePosition = new Position(this.getTargetPosition().getRow(),'a');
            this.rookTargetPosition = new Position(this.getTargetPosition().getRow(),'d');
        }

    }
    public Position getRookSourcePosition() {
        return rookSourcePosition;
    }

    public Position getRookTargetPosition() {
        return rookTargetPosition;
    }
}

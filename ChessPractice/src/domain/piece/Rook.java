package domain.piece;

import domain.Enum.Role;
import domain.Enum.Status;
import domain.Enum.Team;
import domain.Position;

public class Rook extends Piece{

    protected Status status = Status.ACTIVATD;

    protected Rook(Team team, Role role) {
        super(team, role);
    }


    @Override
    public void activatePiece (){
        this.status = Status.ACTIVATD;
    };
    @Override
    public void deactivatePiece (){
        this.status = Status.DEACTIVATED;
    }
}

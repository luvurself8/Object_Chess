package domain.piece;

import domain.Enum.Role;
import domain.Enum.Status;
import domain.Enum.Team;

public class Bishop extends Piece{


    protected Status status = Status.ACTIVATD;

    protected Bishop(Team team, Role role) {
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

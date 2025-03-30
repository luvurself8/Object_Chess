package domain.piece;

import domain.Enum.Status;
import domain.Position;
import domain.Enum.Role;
import domain.Enum.Team;

public class Queen extends Piece{

    protected Status status = Status.ACTIVATD;

    protected Queen(Team team, Role role) {
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

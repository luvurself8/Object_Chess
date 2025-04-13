package domain.piece;

import domain.Enum.Role;
import domain.Enum.Status;
import domain.Enum.Team;
import domain.Movement;
import domain.Position;

public class Rook extends Piece{


    protected Rook (Team team, Role role, int order) {
        super(team, role, order);
    }

}

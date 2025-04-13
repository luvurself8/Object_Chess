package domain.piece;

import domain.Enum.Direction;
import domain.Enum.Role;
import domain.Enum.Status;
import domain.Enum.Team;
import domain.Movement;

public class Pawn extends Piece{

    protected Pawn (Team team, Role role, int order) {
        super(team, role, order);
    }

}

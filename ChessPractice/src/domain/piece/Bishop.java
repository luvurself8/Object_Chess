package domain.piece;

import domain.Enum.Role;
import domain.Enum.Status;
import domain.Enum.Team;
import domain.Movement;

public class Bishop extends Piece{

    protected Bishop(Team team, Role role, int order) {
        super(team, role, order);
    }

}

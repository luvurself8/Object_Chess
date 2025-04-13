package domain.piece;

import domain.Enum.Role;
import domain.Enum.Status;
import domain.Enum.Team;
import domain.Movement;

public class Knight extends Piece{


    protected Knight(Team team, Role role, int order) {
        super(team, role, order);
    }

}

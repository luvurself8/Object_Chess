package domain.piece;

import domain.Enum.Status;
import domain.Movement;
import domain.Position;
import domain.Enum.Role;
import domain.Enum.Team;

public class Queen extends Piece{

    protected Queen (Team team, Role role, int order) {
        super(team, role, order);
    }

}

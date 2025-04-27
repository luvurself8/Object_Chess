package domain.piece;

import domain.Enum.Role;
import domain.Enum.Team;
import domain.Movement;
import domain.Position;

public class None extends Piece {
    protected None(Team team, Role role, Position position, int order) {
        super(team, role, position, order);
    }

    public boolean canMove(Movement move, Piece targetPiece) {
        return false;
    }
}

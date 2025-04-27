package domain.piece;

import domain.Enum.Direction;
import domain.Movement;
import domain.Position;
import domain.Enum.Role;
import domain.Enum.Team;

public class Queen extends Piece {

    protected Queen(Team team, Role role, Position position, int order) {
        super(team, role, position, order);
    }

    public boolean canMove(Movement move, Piece targetPiece) {
        if (move.getDirection() != Direction.NONE) {
            return true;
        }
        return false;
    }
}

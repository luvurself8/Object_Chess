package domain.piece;

import domain.Enum.Direction;
import domain.Movement;
import domain.Position;
import domain.Enum.Role;
import domain.Enum.Team;

public class King extends Piece {

    protected King(Team team, Role role, Position position, int order) {
        super(team, role, position, order);
    }

    public boolean canMove(Movement move, Piece targetPiece) {
        if (move.getLength() != 1) {
            return false;
        }
        if (move.getDirection() == Direction.DIAGNOAL
                || move.getDirection() == Direction.HORIZONTAL
                || move.getDirection() == Direction.DOWN
                || move.getDirection() == Direction.UP) {
            return false;
        }
        return true;
    }
}

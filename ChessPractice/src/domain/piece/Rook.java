package domain.piece;

import domain.Enum.Direction;
import domain.Enum.Role;
import domain.Enum.Team;
import domain.Movement;
import domain.Position;

public class Rook extends Piece {


    protected Rook(Team team, Role role, Position position, int order) {
        super(team, role, position, order);
    }

    public boolean canMove(Movement move, Piece targetPiece) {
        if (move.getDirection() == Direction.HORIZONTAL
                || move.getDirection() == Direction.UP
                || move.getDirection() == Direction.DOWN) {
            return true;
        }
        return false;
    }
}

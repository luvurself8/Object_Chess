package domain.piece;

import domain.Enum.Direction;
import domain.Enum.Role;
import domain.Enum.Team;
import domain.Movement;
import domain.Position;

public class Knight extends Piece{


    protected Knight(Team team, Role role, Position position, int order) {
        super(team, role, position, order);
    }
    public boolean canMove(Movement move, Piece targetPiece) {
        return (move.getDirection() == Direction.L);
    }
}

package domain.piece;

import domain.Enum.Direction;
import domain.Enum.Role;
import domain.Enum.Team;
import domain.Movement;
import domain.Position;

public class Pawn extends Piece {

    protected Pawn(Team team, Role role, Position position, int order) {
        super(team, role, position, order);
    }

    public boolean canMove(Movement move, Piece targetPiece) {
        if (move.getDirection() == Direction.DIAGNOAL && move.getLength() == 1) {
            return targetPiece.getRole() != Role.NONE && targetPiece.getTeam() != this.team;
        } else if (move.getDirection() == Direction.UP && move.getLength() == 1) {
            return targetPiece.getRole() == Role.NONE;
        } else if (move.getDirection() == Direction.UP && move.getLength() == 2) {
            return this.isInitialPosition() && targetPiece.getRole() == Role.NONE;
        }
        return false;
    }
}

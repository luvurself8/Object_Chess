package domain.piece;

import domain.Enum.Role;
import domain.Enum.Team;
import domain.Position;

public class PieceFactory {

    public static Piece create(Team team, Role role, Position position, int order) {
        switch (role) {
            case PAWN:
                return new Pawn(team, role, position, order);
            case ROOK:
                return new Rook(team, role, position, order);
            case KNIGHT:
                return new Knight(team, role, position, order);
            case BISHOP:
                return new Bishop(team, role, position, order);
            case QUEEN:
                return new Queen(team, role, position, order);
            case KING:
                return new King(team, role, position, order);
            case NONE:
                return new None(team, role, position, order);
            default:
                throw new IllegalArgumentException("Invalid role: " + role);
        }
    }
}

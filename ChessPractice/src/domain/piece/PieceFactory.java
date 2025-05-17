package domain.piece;

import domain.Enum.Role;
import domain.Enum.Team;
import domain.board.Position;

public class PieceFactory {

    public static Piece createPiece (Team team, Role role, Position position) {
        return switch (role) {
            case PAWN -> new Pawn(team, role, position);
            case ROOK -> new Rook(team, role, position);
            case KNIGHT -> new Knight(team, role, position);
            case BISHOP -> new Bishop(team, role, position);
            case QUEEN -> new Queen(team, role, position);
            case KING -> new King(team, role, position);
            case NONE -> new EmptyPiece(team, role, position);
            default -> throw new IllegalArgumentException("Invalid role: " + role);
        };
    }
    public static Piece createEmptyPiece(Position position){
        return new EmptyPiece(Team.NONE, Role.NONE, position);
    }
}

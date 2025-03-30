package domain.piece;

import domain.Enum.Status;
import domain.Position;
import domain.Enum.Role;
import domain.Enum.Team;

import static domain.Enum.Role.*;

public abstract class Piece {
    protected final Team team;
    protected final Role role;

    protected Piece ( Team team , Role role) {
        this.team = team;
        this.role = role;
    }
    public Team getTeam() {
        return team;
    }

    public Role getRole() {
        return role;
    }

    public String getPositionText (){
        return role.getPositionText(this.team);
    }
    public Position getInitialPosition (int order){
        return role.getInitialPosition(this.team, this.role, order);
    }

    public abstract void activatePiece ();
    public abstract void deactivatePiece ();
    public static Piece createPiece(Team team, Role role) {
            switch (role) {
                case PAWN:
                    return new Pawn(team, role);
                case ROOK:
                    return new Rook (team, role);
                case KNIGHT:
                    return new Knight(team, role);
                case BISHOP:
                    return new Bishop(team, role);
                case QUEEN:
                    return new Queen(team, role);
                case KING:
                    return new King(team, role);
                default:
                    throw new IllegalArgumentException("Invalid role: " + role);
            }

    }


}

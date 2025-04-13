package domain.piece;

import domain.Enum.Status;
import domain.Movement;
import domain.Position;
import domain.Enum.Role;
import domain.Enum.Team;

import static domain.Enum.Role.*;

public abstract class Piece {
    protected final Team team;
    protected final Role role;
    protected Status status;
    protected final int order;

    protected Piece ( Team team , Role role, int order) {
        this.team = team;
        this.role = role;
        this.order = order;
        this.status = Status.ACTIVATD;
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
    public Position getInitialPosition (){
        return role.getInitialPosition(this.team, this.role, this.order);
    }


    public void activatePiece(){
        this.status = Status.ACTIVATD;
    }
    public void deactivatePiece (){
        this.status = Status.DEACTIVATED;
    }

    public static Piece createPiece(Team team, Role role, int order) {
            switch (role) {
                case PAWN:
                    return new Pawn(team, role, order);
                case ROOK:
                    return new Rook (team, role, order);
                case KNIGHT:
                    return new Knight(team, role, order);
                case BISHOP:
                    return new Bishop(team, role, order);
                case QUEEN:
                    return new Queen(team, role, order);
                case KING:
                    return new King(team, role, order);
                default:
                    throw new IllegalArgumentException("Invalid role: " + role);
            }

    }


}

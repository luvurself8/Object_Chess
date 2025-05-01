package domain.piece;

import domain.Movement;
import domain.Position;
import domain.Enum.Role;
import domain.Enum.Team;


public abstract class Piece {
    protected final Team team;
    protected final Role role;
    protected final int order;
    protected Position position;

    protected Piece(Team team, Role role, Position position, int order) {
        this.team = team;
        this.role = role;
        this.order = order;
        this.position = position;
    }

    public Team getTeam() {
        return this.team;
    }

    public Role getRole() {
        return this.role;
    }

    public Position getPosition() {
        return this.position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public String getPositionText() {
        return this.role.getPositionText(this.team);
    }

    public Position getInitialPosition() {
        return this.role.getInitialPosition(this.team, this.order);
    }

    public boolean isInitialPosition() {
        return this.getInitialPosition().equals(this.position);
    }

    public abstract boolean canMove(Movement move, Piece targetPiece);

    @Override
    public String toString() {
        return "Piece{" +
                "team=" + team +
                ", role=" + role +
                ", order=" + order +
                ", position=" + position +
                '}';
    }
}

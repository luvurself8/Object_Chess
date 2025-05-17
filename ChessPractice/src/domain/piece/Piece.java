package domain.piece;

import domain.Enum.MoveType;
import domain.Enum.PieceStatus;
import domain.board.CastleMovement;
import domain.board.Movement;
import domain.board.Position;
import domain.Enum.Role;
import domain.Enum.Team;

import java.util.List;
import java.util.Map;
import static domain.board.BoardUtil.*;

public abstract class Piece {

    protected final Team team;
    protected Role role;
    protected Position position;
    protected PieceStatus status = PieceStatus.CREATED;
    protected Movement lastMove;

    protected Piece(Team team, Role role, Position position) {
        this.team = team;
        this.role = role;
        this.position = position;
    }

    public Position getPiecePosition() {
        return this.position;
    }

    public String getPositionText(){
        return this.role.getPositionText(this.team);
    }

    public boolean equalTeam (Team team) {
        return this.team.equals(team);
    }

    public boolean equalRole (Role role) {
        return this.role.equals(role);
    }

    public abstract List<Movement> getMovablePositions(Map<Position, Piece> board);

    public void updatePieceStatus (Movement move) {
        this.status = PieceStatus.MOVED;
        this.lastMove = move;
        if (move instanceof CastleMovement && this.equalRole(Role.ROOK)) {
            this.position = ((CastleMovement) move).getRookSourcePosition();
        }
        this.position = move.getTargetPosition();
    }

    public void deactivatePiece()  {
        this.status = PieceStatus.DEACTIVATED;
    }

    protected boolean hasMoved(){
        return this.status == PieceStatus.MOVED;
    }


    @Override
    public String toString() {
        return "Piece{" +
                "team=" + team +
                ", role=" + role +
                ", position=" + position +
                '}';
    }
}

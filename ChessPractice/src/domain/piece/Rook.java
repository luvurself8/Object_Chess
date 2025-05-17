package domain.piece;

import domain.Enum.Direction;
import domain.Enum.MoveType;
import domain.Enum.Role;
import domain.Enum.Team;
import domain.board.Movement;
import domain.board.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static domain.board.BoardUtil.*;

public class Rook extends Piece {


    protected Rook(Team team, Role role, Position position) {
        super(team, role, position);
    }

    @Override
    public List<Movement> getMovablePositions(Map<Position, Piece> board) {
        List<Movement> movableList = new ArrayList<>();
        List<Position> candidatePositions = new ArrayList<>();

        for (int i =1 ; i<=MAX_STEP; i++) {
            candidatePositions.addAll(move( this.team, this.position, Direction.HORIZONTAL, i));
            candidatePositions.addAll(move( this.team, this.position, Direction.VERTICAL,i));
        }

        for (Position targetPosition : candidatePositions) {
            Piece targetPiece = board.get(targetPosition);
            if(!targetPiece.equalTeam(this.team) && isPathClear(getPath(this.position, targetPosition), board)){
                movableList.add(new Movement(this,targetPiece, MoveType.MOVE));
            }
        }
        return movableList;
    }

    public boolean canBeTargetedByCastling(Team team){
        return this.equalTeam(team.getOppositTeam()) && !this.hasMoved();
    }
}

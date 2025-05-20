package domain.piece;

import domain.Enum.Direction;
import domain.Enum.MoveType;
import domain.Enum.Role;
import domain.Enum.Team;
import domain.move.Movement;
import domain.board.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static domain.board.BoardUtil.*;

public class Knight extends Piece{


    protected Knight(Team team, Role role, Position position) {
        super(team, role, position);
    }

    @Override
    public List<Movement> getMovablePositions(Map<Position, Piece> board) {
        List<Movement> movableList = new ArrayList<>();

        List<Position> candidatePositions = new ArrayList<>();
        candidatePositions.addAll(move(this.team, this.position, Direction.L_SHAPE,1));

        for (Position targetPosition : candidatePositions) {
            Piece targetPiece = board.get(targetPosition);
            if(!targetPiece.equalTeam(this.team)){
                movableList.add(new Movement(this.position,targetPosition, MoveType.MOVE));
            }
        }
        return movableList;
    }

}

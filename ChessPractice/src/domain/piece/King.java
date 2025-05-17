package domain.piece;

import domain.Enum.Direction;
import domain.Enum.MoveType;
import domain.Enum.Role;
import domain.Enum.Team;
import domain.board.CastleMovement;
import domain.board.Movement;
import domain.board.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static domain.board.BoardUtil.*;

public class King extends Piece {

    protected King(Team team, Role role, Position position) {
        super(team, role, position);
    }

    @Override
    public List<Movement> getMovablePositions(Map<Position, Piece> board) {
        List<Movement> movableList = new ArrayList<>();

        // 모든 방향에서 한 칸 이동 가능한 위치 수집
        List<Position> candidatePositions = new ArrayList<>();
        candidatePositions.addAll(move(this.team, this.position, Direction.DIAGONAL,1));
        candidatePositions.addAll(move(this.team, this.position, Direction.HORIZONTAL,1));

        for (Position targetPosition : candidatePositions) {
            Piece targetPiece = board.get(targetPosition);
            if(!targetPiece.equalTeam(this.team)){
                movableList.add(new Movement(this,targetPiece, MoveType.MOVE));
            }
        }

        // 캐슬링
        movableList.addAll(getCastlingPosition(board));
        return movableList;
    }

    private List<Movement> getCastlingPosition(Map<Position, Piece> board) {

        List<Movement> movableList = new ArrayList<>();

        if (hasMoved()) return movableList;
        for(CastleMovement move : getCastlingMovement(this.team)){
            if (!(this.position == move.getSourcePosition())){
                continue;
            }
            Position targetPosition = move.getTargetPosition();
            Piece targetPiece = board.get(targetPosition);
            if (targetPiece != null && targetPiece.equalTeam(this.team)) {
                if (targetPiece instanceof Rook ){
                    if (((Rook)targetPiece).canBeTargetedByCastling(this.team)){
                        move.setKingPiece(this);
                        move.setRookPiece(targetPiece);
                        movableList.add(move);
                    }
                }
            }
        }
        return movableList;
    }
}

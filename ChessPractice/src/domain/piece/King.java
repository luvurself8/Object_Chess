package domain.piece;

import domain.Enum.Direction;
import domain.Enum.MoveType;
import domain.Enum.Role;
import domain.Enum.Team;
import domain.move.CastleMovement;
import domain.move.Movement;
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
        candidatePositions.addAll(move(this.team, this.position, Direction.VERTICAL,1));

        for (Position targetPosition : candidatePositions) {
            Piece targetPiece = board.get(targetPosition);
            if(!targetPiece.equalTeam(this.team)){
                movableList.add(new Movement(this.position,targetPosition, MoveType.MOVE));
            }
        }

        // 캐슬링
        movableList.addAll(getCastlingPosition(board));
        return movableList;
    }

    private List<CastleMovement> getCastlingPosition(Map<Position, Piece> board) {
        List<CastleMovement> castleMovementList = new ArrayList<>();

        int row = (this.team == Team.BLACK) ? 8 : 1;
        Position kingInitialPosition = Role.KING.getInitialPositions(this.team).getFirst();

        if (!this.position.equals(kingInitialPosition) || this.hasMoved()) {
            return castleMovementList;
        }

        // 킹사이드 체크
        addCastleIfValid(castleMovementList, board, row, 'g');

        // 퀸사이드 체크
        addCastleIfValid(castleMovementList, board, row, 'c');

        return castleMovementList;
    }

    private void addCastleIfValid(List<CastleMovement> list, Map<Position, Piece> board,
                                  int row, char kingTargetCol) {

        //Position rookSourcePosition = new Position(row, rookSourceCol);
        Position kingTargetPosition = new Position(row, kingTargetCol);
        CastleMovement castleMovement = new CastleMovement(this.position, kingTargetPosition);

        if (!isPathClear(getPath(this.position, castleMovement.getRookSourcePosition()), board)) return;

        Piece rookSourcePiece = board.get(castleMovement.getRookSourcePosition());

        if (!(rookSourcePiece instanceof Rook)) return;
        if (!(((Rook) rookSourcePiece).canBeTargetedByCastling(this.team))) return;

        list.add(castleMovement);
    }
}

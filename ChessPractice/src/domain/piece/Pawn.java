package domain.piece;

import domain.Enum.Direction;
import domain.Enum.MoveType;
import domain.Enum.Role;
import domain.Enum.Team;
import domain.move.EnPassantMovement;
import domain.move.Movement;
import domain.board.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static domain.board.BoardUtil.*;

public class Pawn extends Piece {

    protected Pawn(Team team, Role role, Position position) {
        super(team, role, position);
    }

    @Override
    public List<Movement> getMovablePositions(Map<Position, Piece> board) {
        List<Movement> movableList = new ArrayList<>();
        // 두 칸 앞으로
        if (!hasMoved()) {
            Position targetPosition = move(this.team, this.position, Direction.UP,2).getFirst();
            Piece targetPiece = board.get(targetPosition);
            if(targetPiece instanceof EmptyPiece && isPathClear(getPath(this.position, targetPosition), board)) {
                movableList.add(new Movement (this.position, targetPosition, MoveType.PAWN_TWO_STEP));
            }
        }

        // 한칸 앞으로
        Position targetPosition = move(this.team, this.position, Direction.UP,1).getFirst();
        Piece targetPiece = board.get(targetPosition);
        if(targetPiece instanceof EmptyPiece) {
            movableList.add(new Movement(this.position, targetPosition, canPromote(targetPosition) ? MoveType.PROMOTE : MoveType.MOVE));
        }

        // 대각선 공격
        movableList.addAll(getMoveForwardDiagonalPositions(board));

        return movableList;
    }

    private List<Movement> getMoveForwardDiagonalPositions(Map<Position, Piece> board){
        List<Movement> movableList = new ArrayList<>();
        for (Position targetPosition : move(this.team, this.position, Direction.UP_DIAGONAL, 1)) {

            Piece targetPiece = board.get(targetPosition);
            Piece enemyPieceForEnPassant = board.get(move(this.team, targetPosition, Direction.DOWN,1).getFirst());

            if (targetPiece instanceof EmptyPiece && enemyPieceForEnPassant instanceof Pawn) {
                if(((Pawn)enemyPieceForEnPassant).canBeTargetedByEnPassant(this.team)) {
                    movableList.add(new EnPassantMovement(this.position, targetPosition, enemyPieceForEnPassant));
                }
            }
            if (targetPiece.equalTeam(this.team.getOppositTeam())){
                movableList.add(new Movement(this.position, targetPosition, canPromote(targetPosition) ? MoveType.PROMOTE : MoveType.MOVE));
            }
        }
        return movableList;
    }

    private boolean canPromote(Position targetPosition) {
        int targetRow = targetPosition.getRow();
        return targetRow == ROW_START || targetRow == ROW_END;
    }

    public boolean canBeTargetedByEnPassant(Team team){
        if(!this.hasMoved()){
            return false;
        }
        return this.lastMove.isMoveType(MoveType.PAWN_TWO_STEP) && (this.team).equals(team.getOppositTeam());
    }
}

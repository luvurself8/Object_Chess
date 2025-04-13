package domain;

import domain.Enum.Direction;
import domain.Enum.Role;
import domain.Enum.Team;
import domain.piece.Piece;

import java.util.*;

public class ChessBoard {
    private Map<Position, Piece> squares = new HashMap<>();

    public ChessBoard() {
        initializeChessBoard();
    }

    private void initializeChessBoard() {
        for (Team team : Team.values()) {
            for (Role role : Role.values()) {
                for (int order = 1; order <= role.getMaxCount(); order++) {

                    Piece piece = Piece.createPiece(team, role, order);
                    Position position = piece.getInitialPosition();
                    squares.put(position, piece);
                }
            }
        }
    }

    public Map<Position, Piece> getBoard() {
        return this.squares;
    }

    public Piece getPiece (Position position) {
        return squares.get(position);
    }

    public boolean isMovementValid(Position sourcePosition, Position targetPosition, Team turn) {
        Piece sourcePiece = getPiece(sourcePosition);
        Piece targetPiece = getPiece(targetPosition);

        if (sourcePiece == null) return false;
        if (sourcePiece.getTeam() != turn) return false;
        if (targetPiece != null && targetPiece.getTeam() == turn) return false;

        Movement move = new Movement(sourcePosition, targetPosition, turn);
        Role sourceRole = sourcePiece.getRole();

        switch (sourceRole) {
            case PAWN:
                // 대각선 공격 (적이 있을 경우만 가능)
                if (move.dir == Direction.DIAGNOAL && move.length == 1) {
                    return targetPiece != null && targetPiece.getTeam() != turn;
                }

                // 직진 - 한 칸 (빈 칸이어야 함)
                if (move.dir == Direction.UP && move.length == 1) {
                    return targetPiece == null;
                }

                // 직진 - 두 칸 (초기 위치에서만 가능, 중간칸도 비어있어야 함)
                if (move.dir == Direction.UP && move.length == 2) {
                    return sourcePiece.getInitialPosition() == sourcePosition && targetPiece == null && isPathClear(move);
                }
                return false;

            case ROOK:
            case BISHOP:
            case QUEEN:
            case KING:
            case KNIGHT:


            default:
                return false;
        }
    }

    public boolean isPathClear(Movement move) {
        for (Position position : move.getPositionBetween()){
            if (getPiece(position) != null){
                return false;
            }
        }

        return true;
    }
}

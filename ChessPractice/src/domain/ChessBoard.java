package domain;

import domain.Enum.Direction;
import domain.Enum.Role;
import domain.Enum.Team;
import domain.piece.None;
import domain.piece.Piece;
import domain.piece.PieceFactory;

import java.util.*;

public class ChessBoard {
    private final Map<Position, Piece> squares = new HashMap<>();
    private final Map<Role, Piece> caughtPiece = new EnumMap<>(Role.class);

    public ChessBoard() {
        initializeChessBoard();
    }

    private void initializeChessBoard() {
        for (int row = 1; row <= 8; row++) {
            for (char col = 'a'; col <= 'h'; col++) {
                Position pos = new Position(row, col);
                squares.put(pos, PieceFactory.create(Team.NONE, Role.NONE, pos, 0));
            }
        }
        initializeTeam(Team.WHITE);
        initializeTeam(Team.BLACK);
    }

    private void initializeTeam(Team team) {
        for (Role role : Role.values()) {
            if (role == Role.NONE) continue;

            for (int order = 1; order <= role.getMaxCount(); order++) {
                Position pos = role.getInitialPosition(team, role, order);
                squares.put(pos, PieceFactory.create(team, role, pos, order));
            }
        }
    }

    public Map<Position, Piece> getBoard() {
        return this.squares;
    }

    private Piece getPiece (Position position) {
        return squares.get(position);
    }

    private boolean isPathClear(Movement move) {
        for (Position position : move.getPositionBetween()){
            if (getPiece(position).getRole() != Role.NONE){
                return false;
            }
        }
        return true;
    }

    public boolean proceedProcess(Position sourcePosition, Position targetPosition, Team turn){
        Piece sourcePiece = getPiece(sourcePosition);
        Piece targetPiece = getPiece(targetPosition);

        if (sourcePiece.getRole() == Role.NONE){
            return false;
        }
        if (sourcePiece.getTeam() != turn) {
            return false;
        }
        if (targetPiece.getTeam() == turn) {
            return false;
        }

        Movement move = new Movement(sourcePosition, targetPosition, turn);
        if (move.getDirection() == Direction.NONE) {
            return false;
        }
        System.out.println(move);

        if(!sourcePiece.canMove(move, targetPiece)){
            return false;
        }
        if (sourcePiece.getRole() != Role.KNIGHT && !isPathClear(move)){
            return false;
        }else if (sourcePiece.getRole() == Role.KNIGHT && isPathClear(move)){
            return false;
        }

        squares.put(sourcePosition, PieceFactory.create(Team.NONE, Role.NONE, sourcePosition, 0 ) );
        if (targetPiece.getRole() != Role.NONE) {
            caughtPiece.put(targetPiece.getRole(), targetPiece);
        }
        sourcePiece.setPosition(targetPosition);
        squares.put(targetPosition, sourcePiece);

        return true;
    }
}

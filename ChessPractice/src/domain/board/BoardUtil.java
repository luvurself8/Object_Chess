package domain.board;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import domain.Enum.Direction;
import domain.Enum.MoveType;
import domain.Enum.Role;
import domain.Enum.Team;
import domain.piece.EmptyPiece;
import domain.piece.Piece;
import domain.Enum.Direction;

public class BoardUtil {

    public static final char COLUMN_START = 'a';
    public static final char COLUMN_END = 'h';
    public static final int ROW_START = 1;
    public static final int ROW_END = 8;
    public static final int MAX_STEP = 7;

    public static boolean isValid(int row, char column){
        return (row >= ROW_START && row <= ROW_END) && (column >= COLUMN_START && column <= COLUMN_END);
    }

    public static List<Position> move ( Team team, Position sourcePosition, Direction direction, int step) {

        List<Position> result = new ArrayList<>();
        int increment = (team == Team.BLACK) ? 1 : -1;
        // 블랙을 기준으로

        int sourceRow = sourcePosition.getRow();
        char sourceColumn = sourcePosition.getColumn();

        switch (direction) {
            case UP:
                if (isValid(sourceRow - increment * step, sourceColumn)) {
                    result.add(new Position(sourceRow - increment * step, sourceColumn));
                }
                break;
            case DOWN:
                if (isValid(sourceRow + increment * step, sourceColumn)) {
                    result.add(new Position(sourceRow + increment * step, sourceColumn));
                }
                break;
            case VERTICAL:
                if (isValid(sourceRow - increment * step, sourceColumn)) {
                    result.add(new Position(sourceRow - increment * step, sourceColumn));
                }
                if (isValid(sourceRow + increment * step, sourceColumn)) {
                    result.add(new Position(sourceRow + increment * step, sourceColumn));
                }
                break;
            case HORIZONTAL:
                if (isValid(sourceRow, (char) (sourceColumn + increment * step))) {
                    result.add(new Position(sourceRow, (char) (sourceColumn + increment * step)));
                }
                if (isValid(sourceRow, (char) (sourceColumn - increment * step))) {
                    result.add(new Position(sourceRow, (char) (sourceColumn - increment * step)));
                }
                break;
            case UP_DIAGONAL:
                if (isValid(sourceRow - increment * step, (char) (sourceColumn + increment * step))) {
                    result.add(new Position(sourceRow - increment * step, (char) (sourceColumn + increment * step)));
                }

                if (isValid(sourceRow - increment * step, (char) (sourceColumn - increment * step))) {
                    result.add(new Position(sourceRow - increment * step, (char) (sourceColumn - increment * step)));
                }
                break;
            case DIAGONAL:
                if (isValid(sourceRow - increment * step, (char) (sourceColumn + increment * step))) {
                    result.add(new Position(sourceRow - increment * step, (char) (sourceColumn + increment * step)));
                }

                if (isValid(sourceRow - increment * step, (char) (sourceColumn - increment * step))) {
                    result.add(new Position(sourceRow - increment * step, (char) (sourceColumn - increment * step)));
                }

                if (isValid(sourceRow + increment * step, (char) (sourceColumn + increment * step))) {
                    result.add(new Position(sourceRow + increment * step, (char) (sourceColumn + increment * step)));
                }

                if (isValid(sourceRow + increment * step, (char) (sourceColumn - increment * step))) {
                    result.add(new Position(sourceRow + increment * step, (char) (sourceColumn - increment * step)));
                }
                break;
            case L_SHAPE:
                int[] rowOffsets = {2, 1, -1, -2, -2, -1, 1, 2};
                int[] colOffsets = {1, 2, 2, 1, -1, -2, -2, -1};

                for (int i = 0; i < colOffsets.length; i++) {
                    int newRow = sourceRow + rowOffsets[i];
                    char newCol = (char) (sourceColumn + colOffsets[i]);

                    if (isValid(newRow, newCol)) {
                        result.add(new Position(newRow, newCol));
                    }
                }
        }
        return result;
    }
    /*// 앞 (전진)
    public static List<Position> moveForward (Position source, Team team, int step) {
        List<Position> result = new ArrayList<>();
        int increment = (team == Team.WHITE) ? 1 : -1;

        int sourceRow = source.getRow();
        char sourceColumn = source.getColumn();

        int newRow = sourceRow + increment * step;

        if (isValid(newRow, sourceColumn)) {
            result.add(new Position(newRow, sourceColumn));
        }
        return result;
    }
    // 뒤 (후진)
    public static List<Position> moveBackward (Position source, Team team, int step) {
        return moveForward (source, team == Team.WHITE ? Team.BLACK : Team.WHITE, step);
    }
    // 오른쪽
    public static List<Position> moveRight (Position source, Team team, int step) {
        List<Position> result = new ArrayList<>();
        int sourceRow = source.getRow();
        char sourceColumn = source.getColumn();
        int increment = (team == Team.WHITE) ? -1 : 1;


        char newCol = (char)(sourceColumn + step * increment);
        if (isValid(sourceRow, newCol)) {
            result.add(new Position(sourceRow, newCol));

        }
        return result;
    }
    // 왼쪽
    public static List<Position> moveLeft(Position source, Team team, int step) {
       return moveRight(source, team == Team.WHITE ? Team.BLACK : Team.WHITE, step);
    }
    // 앞 대각선
    public static List<Position> moveForwardDiagonal(Position source, Team team, int step) {
        List<Position> result = new ArrayList<>();

        int increment = (team == Team.WHITE) ? 1 : -1;
        int sourceRow = source.getRow();
        char sourceCol = source.getColumn();


        int newRow = sourceRow + increment * step;

        // 앞-왼쪽 대각선
        char newColLeft = (char) (sourceCol - step);
        if (isValid(newRow, newColLeft)) {
            result.add(new Position(newRow, newColLeft));
        }

        // 앞-오른쪽 대각선
        char newColRight = (char) (sourceCol + step);
        if (isValid(newRow, newColRight)) {
            result.add(new Position(newRow, newColRight));
        }

        return result;
    }
    // 뒤  대각선
    public static List<Position> moveBackwardDiagonal(Position source, Team team, int step) {
        return moveForwardDiagonal(source, team == Team.WHITE ? Team.BLACK : Team.WHITE, step);
    }
    // L자형
    public static List<Position> moveLShaped(Position source, Team team, int step) {
        List<Position> result = new ArrayList<>();

        int row = source.getRow();
        char col = source.getColumn();

        int[] rowOffsets = {2, 1, -1, -2, -2, -1, 1, 2};
        int[] colOffsets = {1, 2, 2, 1, -1, -2, -2, -1};

        for (int i = 0; i < 8; i++) {
            int newRow = row + rowOffsets[i];
            char newCol = (char) (col + colOffsets[i]);

            if (isValid(newRow, newCol)) {
                result.add(new Position(newRow, newCol));
            }
        }

        return result;
    }
    */
    public static List<Position> getPath(Position sourcePosition, Position targetPosition) {
        List<Position> result = new ArrayList<>();
        int sourceRow = sourcePosition.getRow();
        int targetRow = targetPosition.getRow();
        int rowDiff = targetRow - sourceRow;

        int sourceCol = sourcePosition.getColumn() - COLUMN_START;
        int targetCol = targetPosition.getColumn() - COLUMN_START;
        int colDiff = targetCol - sourceCol;

        int rowStep = Integer.signum(rowDiff);
        int colStep = Integer.signum(colDiff);
        int steps = Math.max(Math.abs(rowDiff), Math.abs(colDiff));


        for (int i = 1; i < steps; i++) {
            int newRow = sourceRow + rowStep * i;
            char newCol = (char) (sourceCol + colStep * i + COLUMN_START);
            result.add(new Position(newRow, newCol));
        }
        return result;
    }

    public static boolean isPathClear(List<Position> path, Map<Position, Piece> board) {
        for (Position position : path) {
            if (!(board.get(position) instanceof EmptyPiece)) {
                return false; // 기물이 경로에 있음 → 경로 막힘
            }
        }
        return true; // 경로가 비어 있음
    }

    public static List<CastleMovement> getCastlingMovement(Team team) {
        List<CastleMovement> movements = new ArrayList<>();
        movements.add(getKingSideCastlingPosition(team));
        movements.add(getQueenSideCastlingPosition(team));
        return movements;
    }

    public static CastleMovement getKingSideCastlingPosition (Team team){
        // King 위치
        Position kingSourcePosition = Role.KING.getInitialPositions(team).get(0);
        // King 이동 후 위치
        Position kingTargetPosition = new Position(kingSourcePosition.getRow(), (char) (kingSourcePosition.getColumn() + 2));

        // Rook 위치
        Position rookSourcePosition = Role.ROOK.getInitialPositions(team).get(0);
        // Rook 이동 후 위치
        Position rookTargetPosition = new Position(rookSourcePosition.getRow(), (char) (rookSourcePosition.getColumn() - 1));

        return new CastleMovement (kingSourcePosition, kingTargetPosition, rookSourcePosition,rookTargetPosition);
    }
    // 퀸사이드 캐슬링
    public static CastleMovement getQueenSideCastlingPosition (Team team){

        // King 위치
        Position kingSourcePosition = Role.KING.getInitialPositions(team).get(0);
        // King 이동 후 위치
        Position kingTargetPosition = new Position(kingSourcePosition.getRow(), (char) (kingSourcePosition.getColumn() - 2));

        // Rook 위치
        Position rookSourcePosition = Role.ROOK.getInitialPositions(team).get(1);
        // Rook 이동 후 위치
        Position rookTargetPosition = new Position(rookSourcePosition.getRow(), (char) (rookSourcePosition.getColumn() + 1));

        return new CastleMovement (kingSourcePosition, kingTargetPosition, rookSourcePosition,rookTargetPosition);

    }
}

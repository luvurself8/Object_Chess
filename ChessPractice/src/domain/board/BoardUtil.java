package domain.board;

import domain.Enum.Direction;
import domain.Enum.Team;
import domain.piece.EmptyPiece;
import domain.piece.Piece;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

public class BoardUtil {

    public static final char COLUMN_START = 'a';
    public static final char COLUMN_END = 'h';
    public static final int ROW_START = 1;
    public static final int ROW_END = 8;
    public static final int MAX_STEP = 7;

    public static boolean isValid(int row, char column){
        return (row >= ROW_START && row <= ROW_END) && (column >= COLUMN_START && column <= COLUMN_END);
    }

    public static List<Position> move(Team team, Position sourcePosition, Direction direction, int step) {
        List<Position> result = new ArrayList<>();
        int increment = (team == Team.BLACK) ? 1 : -1;

        int sourceRow = sourcePosition.getRow();
        char sourceCol = sourcePosition.getColumn();

        // 유효한 좌표면 리스트에 추가하는 헬퍼 메서드
        BiConsumer<Integer, Character> addIfValid = (row, col) -> {
            if (isValid(row, col)) {
                result.add(new Position(row, col));
            }
        };

        switch (direction) {
            case UP -> addIfValid.accept(sourceRow - increment * step, sourceCol);
            case DOWN -> addIfValid.accept(sourceRow + increment * step, sourceCol);

            case VERTICAL -> {
                addIfValid.accept(sourceRow - increment * step, sourceCol);
                addIfValid.accept(sourceRow + increment * step, sourceCol);
            }

            case HORIZONTAL -> {
                addIfValid.accept(sourceRow, (char) (sourceCol + increment * step));
                addIfValid.accept(sourceRow, (char) (sourceCol - increment * step));
            }

            case UP_DIAGONAL -> {
                addIfValid.accept(sourceRow - increment * step, (char) (sourceCol + increment * step));
                addIfValid.accept(sourceRow - increment * step, (char) (sourceCol - increment * step));
            }

            case DIAGONAL -> {
                addIfValid.accept(sourceRow - increment * step, (char) (sourceCol + increment * step));
                addIfValid.accept(sourceRow - increment * step, (char) (sourceCol - increment * step));
                addIfValid.accept(sourceRow + increment * step, (char) (sourceCol + increment * step));
                addIfValid.accept(sourceRow + increment * step, (char) (sourceCol - increment * step));
            }

            case L_SHAPE -> {
                int[] rowOffsets = {2, 1, -1, -2, -2, -1, 1, 2};
                int[] colOffsets = {1, 2, 2, 1, -1, -2, -2, -1};
                for (int i = 0; i < 8; i++) {
                    int newRow = sourceRow + rowOffsets[i];
                    char newCol = (char) (sourceCol + colOffsets[i]);
                    addIfValid.accept(newRow, newCol);
                }
            }
        }

        return result;
    }

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

}

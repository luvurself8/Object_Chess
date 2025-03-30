package domain.Enum;

import domain.Position;
import domain.piece.Piece;

public enum Role {
    KING(8, 'e', 1, 1, "\u2654", "\u265A"),
    QUEEN(8, 'd', 1, 1, "\u2655", "\u265B"),
    ROOK(8, 'a', 1, 2, "\u2656", "\u265C"), // (order 1 -> 'a', order 2 -> 'h')
    BISHOP(8, 'c', 1, 2, "\u2657", "\u265D"), // (order 1 -> 'c', order 2 -> 'f')
    KNIGHT(8, 'b', 1, 2, "\u2658", "\u265E"), // (order 1 -> 'b', order 2 -> 'g')
    PAWN(7, 'a', 2, 8, "\u2659", "\u265F"); // (order 1 -> 'a', ..., order 8 -> 'h')


    private final int whiteRow;
    private final char whiteColumn;
    private final int blackRow;
    private final int maxCount; // 기물 개수 (킹 1개, 폰 8개 등)
    private final String whiteTextString;
    private final String blackTextString;


    Role (int whiteRow, char whiteColumn, int blackRow, int maxCount,String whiteTextString, String blackTextString) {
        this.whiteRow = whiteRow;
        this.whiteColumn = whiteColumn;
        this.blackRow = blackRow;
        this.maxCount = maxCount;
        this.whiteTextString = whiteTextString;
        this.blackTextString = blackTextString;
    }

    public Position getPosition(Team team, int order) {
        if (order < 1 || order > maxCount) {
            throw new IllegalArgumentException("Invalid order for role: " + this.name());
        }

        int row = (team == Team.WHITE) ? whiteRow : blackRow;
        char column = (maxCount == 1) ? whiteColumn : (char) (whiteColumn + order - 1);

        if (this == ROOK) column = (order == 1) ? 'a' : 'h';
        if (this == BISHOP) column = (order == 1) ? 'c' : 'f';
        if (this == KNIGHT) column = (order == 1) ? 'b' : 'g';
        if (this == PAWN) column = (char) (whiteColumn + order - 1);
        return new Position(row, column);
    }

    public Position getInitialPosition(Team team, Role role, int order) {
        return valueOf(role.name()).getPosition(team, order);
    }

    public int getMaxCount() {
        return maxCount;
    }

    public String getPositionText(Team team) {
        return (team == Team.WHITE) ? whiteTextString : blackTextString;
    }

}

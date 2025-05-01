package domain.Enum;

import domain.Position;

public enum Role {
    KING(1, 'e', 8, 1, "♔", "♚"),
    QUEEN(1, 'd', 8, 1, "♕", "♛"),
    ROOK(1, 'a', 8, 2, "♖", "♜"), // (order 1 -> 'a', order 2 -> 'h')
    BISHOP(1, 'c', 8, 2, "♗", "♝"), // (order 1 -> 'c', order 2 -> 'f')
    KNIGHT(1, 'b', 8, 2, "♘", "♞"), // (order 1 -> 'b', order 2 -> 'g')
    PAWN(2, 'a', 7, 8, "♙", "♟"), // (order 1 -> 'a', ..., order 8 -> 'h')
    NONE(0, '0', 0, 0, "", ""); /* (order 1 -> 'a', ..., order 8 -> 'h') */


    private final int whiteRow;
    private final char whiteColumn;
    private final int blackRow;
    private final int maxCount; // 기물 개수 (킹 1개, 폰 8개 등)
    private final String whiteTextString;
    private final String blackTextString;


    Role (int whiteRow, char whiteColumn, int blackRow, int maxCount, String whiteTextString, String blackTextString) {
        this.whiteRow = whiteRow;
        this.whiteColumn = whiteColumn;
        this.blackRow = blackRow;
        this.maxCount = maxCount;
        this.whiteTextString = whiteTextString;
        this.blackTextString = blackTextString;
    }

    public Position getInitialPosition(Team team, int order) {
        int row = (team == Team.WHITE) ? whiteRow : blackRow;
        char column = (maxCount == 1) ? whiteColumn : (char) (whiteColumn + order - 1);
        if (this == ROOK) column = (order == 1) ? 'a' : 'h';
        if (this == BISHOP) column = (order == 1) ? 'c' : 'f';
        if (this == KNIGHT) column = (order == 1) ? 'b' : 'g';
        if (this == PAWN) column = (char) (whiteColumn + order - 1);
        return new Position(row, column);
    }

    public int getMaxCount() {
        return maxCount;
    }

    public String getPositionText(Team team) {
        return (team == Team.WHITE) ? whiteTextString : blackTextString;
    }

}

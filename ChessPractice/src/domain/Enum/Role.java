package domain.Enum;

import domain.board.Position;

import java.util.List;
import java.util.stream.IntStream;

public enum Role {

    KING(
            List.of(new Position(1, 'e')),
            List.of(new Position(8, 'e')),
            "♔", "♚"
    ),
    QUEEN(
            List.of(new Position(1, 'd')),
            List.of(new Position(8, 'd')),
            "♕", "♛"
    ),
    ROOK(
            List.of(new Position(1, 'a'), new Position(1, 'h')),
            List.of(new Position(8, 'a'), new Position(8, 'h')),
            "♖", "♜"
    ),
    BISHOP(
            List.of(new Position(1, 'c'), new Position(1, 'f')),
            List.of(new Position(8, 'c'), new Position(8, 'f')),
            "♗", "♝"
    ),
    KNIGHT(
            List.of(new Position(1, 'b'), new Position(1, 'g')),
            List.of(new Position(8, 'b'), new Position(8, 'g')),
            "♘", "♞"
    ),
    PAWN(
            IntStream.range(0, 8)
                    .mapToObj(i -> new Position(2, (char)('a' + i)))
                    .toList(),
            IntStream.range(0, 8)
                    .mapToObj(i -> new Position(7, (char)('a' + i)))
                    .toList(),
            "♙", "♟"
    ),
    NONE(
            List.of(),
            List.of(),
            "", ""
    );

    private final List<Position> whitePositions;
    private final List<Position> blackPositions;
    private final String whiteTextString;
    private final String blackTextString;

    Role(List<Position> whitePositions, List<Position> blackPositions,
         String whiteTextString, String blackTextString) {
        this.whitePositions = whitePositions;
        this.blackPositions = blackPositions;
        this.whiteTextString = whiteTextString;
        this.blackTextString = blackTextString;
    }

    public List<Position> getInitialPositions(Team team) {
        return (team == Team.WHITE) ? whitePositions : blackPositions;
    }

    public String getPositionText(Team team) {
        return (team == Team.WHITE) ? whiteTextString : blackTextString;
    }

    public static Role getRole(String input) {
        for (Role role : Role.values()) {
            if (role.name().equalsIgnoreCase(input)) {
                return role;
            }
        }
        return NONE;
    }

    public boolean isValidPromotionChoice(){
        return this == QUEEN || this == ROOK || this == BISHOP || this == KNIGHT;
    }
    public static boolean isValidPromotionChoice(String role) {
        return role.equalsIgnoreCase(QUEEN.name()) ||
                role.equalsIgnoreCase(ROOK.name()) ||
                role.equalsIgnoreCase(BISHOP.name()) ||
                role.equalsIgnoreCase(KNIGHT.name());
    }

}

package domain.board;
import domain.Enum.MoveType;
import domain.Enum.Role;
import domain.Enum.Team;
import domain.piece.King;
import domain.piece.Piece;
import domain.piece.PieceFactory;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import static domain.board.BoardUtil.*;
import static domain.piece.PieceFactory.createEmptyPiece;

public class ChessBoard {
    private final Map<Position, Piece> board = new HashMap<>();

    public ChessBoard() {
        initializeChessBoard();
    }

    private void initializeChessBoard() {
        for (int row = ROW_START; row <= ROW_END; row++) {
            for (char col = COLUMN_START; col <= COLUMN_END; col++) {
                Position position = new Position(row, col);
                board.put(position, createEmptyPiece(position));
            }
        }
        initializeTeam(Team.WHITE);
        initializeTeam(Team.BLACK);
    }

    private void initializeTeam(Team team) {
        for (Role role : Role.values()) {
            if (role == Role.NONE) continue;
            role.getInitialPositions(team).forEach(position -> {
                board.put(position, PieceFactory. createPiece (team, role, position));
            });
        }
    }

    public Map<Position, Piece> getBoard() {
        return this.board;
    }

    private Piece getPiece (Position position) {
        return board.get(position);
    }

    public List<Movement> getMovementList(Team team, Position position) {
        List<Movement> movableList = new ArrayList<>();
        Piece piece = getPiece(position);
        if (piece.equalTeam(team)){
            movableList.addAll(piece.getMovablePositions(board));
        }
        return movableList;
    }

    public void executePromotion(Team team, PromotionMovement move) {
        Position sourcePosition = move.getSourcePosition();
        Position targetPosition = move.getTargetPosition();
        Piece promotedPiece = PieceFactory.createPiece(team, move.getPromotionRole(), targetPosition);
        board.put(move.getSourcePosition(), createEmptyPiece(sourcePosition));
        board.put(move.getTargetPosition(), promotedPiece);
    }

    public void executeMove(Movement move) {
        Piece sourcePiece = move.getSourcePiece();
        Position sourcePosition = move.getSourcePosition();
        sourcePiece.updatePieceStatus(move);
        board.put(sourcePosition, createEmptyPiece(sourcePosition));
        board.put(move.getTargetPosition(), sourcePiece);
        if (move.isMoveType(MoveType.EN_PASSANT)){
            Position enPassantPosition = ((EnPassantMovement) move).getEnPassantPosition();
            board.put(enPassantPosition, createEmptyPiece(enPassantPosition));
        }
    }

    public void executeCastle(Team team, CastleMovement move) {
        Piece kingPiece = move.getSourcePiece();
        Piece rookPiece = move.getTargetPiece();
        Position sourcePosition = move.getSourcePosition();
        Position targetPosition = move.getTargetPosition();
        Position rookTargetPosition = move.getRookTargetPosition();
        kingPiece.updatePieceStatus(move);
        rookPiece.updatePieceStatus(move);
        board.put(sourcePosition, createEmptyPiece(sourcePosition));
        board.put(targetPosition, kingPiece);
        board.put(rookTargetPosition, rookPiece);
    }

    public boolean isCastleValid (Team team, CastleMovement move){
        Position sourcePosition = move.getSourcePosition();
        Position targetPosition = move.getTargetPosition();
        List<Position> path = getPath(sourcePosition, targetPosition);
        path.add(targetPosition);
        path.add(sourcePosition);
        for (Position position : path) {
           if(canBeChecked(team, position)){
               return false;
           }
        }
        return true;
    }

    public boolean canBeChecked (Team team) {
        // 자신의 팀의 킹 위치를 찾는다
        Position kingPosition =  board.values().stream()
        .filter(piece -> piece.equalTeam(team))
        .filter(piece -> piece.equalRole(Role.KING))
        .findAny().get().getPiecePosition();

        return canBeChecked(team, kingPosition);
    }

    private boolean canBeChecked (Team team, Position kingPosition) {
        // 상대 팀의 모든 기물의 이동 가능한 위치를 확인한다
        boolean isChecked = board.values().stream()
        .filter(piece -> piece.equalTeam(team.getOppositTeam()))
        .anyMatch(piece -> piece.getMovablePositions(board).stream()
        .anyMatch(move -> move.getTargetPosition().equals(kingPosition)));

        return isChecked;
    }
}

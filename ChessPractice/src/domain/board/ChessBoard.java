package domain.board;

import domain.Enum.MoveType;
import domain.Enum.Role;
import domain.Enum.Team;
import domain.move.CastleMovement;
import domain.move.EnPassantMovement;
import domain.move.Movement;
import domain.move.PromotionMovement;
import domain.piece.King;
import domain.piece.Piece;
import domain.piece.PieceFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static domain.board.BoardUtil.*;
import static domain.piece.PieceFactory.createEmptyPiece;

public class ChessBoard {
    private final Map<Position, Piece> board = new HashMap<>();

    public ChessBoard() {
        initializeChessBoard();
    }

    private void initializeChessBoard() {
        fillBoardWithEmptyPieces();
        initializeTeamPieces(Team.WHITE);
        initializeTeamPieces(Team.BLACK);
    }

    private void fillBoardWithEmptyPieces() {
        for (int row = ROW_START; row <= ROW_END; row++) {
            for (char col = COLUMN_START; col <= COLUMN_END; col++) {
                Position position = new Position(row, col);
                board.put(position, createEmptyPiece(position));
            }
        }
    }

    private void initializeTeamPieces(Team team) {
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

    public List<Movement> getMovementList(Team team, Position position) {
        Piece piece = board.get(position);
        if (!piece.equalTeam(team)) return Collections.emptyList();
        return piece.getMovablePositions(board);
    }

    public void executeMove(Movement move) {
        move.setCatchKing(board.get(move.getTargetPosition()) instanceof King);

        Piece sourcePiece = board.get(move.getSourcePosition());
        sourcePiece.updatePieceStatus(move);

        movePieceOnBoard(move.getSourcePosition(),sourcePiece);

        // 앙파상일 때, 상대 말 지워주기 (잡기)
        if (move.isMoveType(MoveType.EN_PASSANT)){
            Position enPassantPosition = ((EnPassantMovement) move).getEnPassantPosition();
            board.put(enPassantPosition, createEmptyPiece(enPassantPosition));
        }
    }

    public void executePromotion(Team team, PromotionMovement move) {
        move.setCatchKing(board.get(move.getTargetPosition()) instanceof King);
        movePieceOnBoard(move.getSourcePosition(), PieceFactory.createPiece(team,move.getPromotionRole(),move.getTargetPosition()));
    }

    public void executeCastle(CastleMovement move) {
        Piece kingPiece = board.get(move.getSourcePosition());
        Piece rookPiece = board.get(move.getRookSourcePosition());
        kingPiece.updatePieceStatus(move);
        rookPiece.updatePieceStatus(move);
        movePieceOnBoard(move.getSourcePosition(), kingPiece);
        movePieceOnBoard(move.getRookSourcePosition(), rookPiece);
    }

    private void movePieceOnBoard (Position sourcePosition, Piece piece){
        board.put(sourcePosition, createEmptyPiece(sourcePosition));
        board.put(piece.getPiecePosition(), piece);
    }

    public boolean isCastleValid (Team team, CastleMovement move){
        List<Position> path = getPath(move.getSourcePosition(), move.getTargetPosition());
        path.add(move.getSourcePosition());
        path.add(move.getTargetPosition());

        return path.stream().noneMatch(position -> canBeChecked(team, position));
    }

    public boolean canCheck(Team team){
        return canBeChecked(team.getOppositTeam());
    }

    private boolean canBeChecked (Team team) {
        // 자신의 팀의 킹 위치를 찾는다
        return board.values().stream()
        .filter(piece -> piece.equalTeam(team) && piece instanceof King)
        .map(Piece::getPiecePosition)
        .findAny()
        // 체크 상태일지 본다
        .map(kingPosition -> canBeChecked(team, kingPosition))
        .orElse(false);
    }

    private boolean canBeChecked (Team team, Position kingPosition) {
        // 상대 팀의 모든 기물의 이동 가능한 위치를 확인한다
        return board.values().stream()
        .filter(piece -> piece.equalTeam(team.getOppositTeam()))
        .flatMap(piece -> piece.getMovablePositions(board).stream())
        .anyMatch(move -> move.getTargetPosition().equals(kingPosition));
    }
}

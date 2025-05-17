package domain.piece;

import domain.Enum.Role;
import domain.Enum.Team;
import domain.board.Movement;
import domain.board.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EmptyPiece extends Piece {
    protected EmptyPiece(Team team, Role role, Position position) {
        super(team, role, position);
    }

    @Override
    public List<Movement> getMovablePositions(Map<Position, Piece> board) {
        List<Movement> movableList = new ArrayList<>();
        return movableList;
    }

}

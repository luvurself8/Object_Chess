package domain;

import domain.Enum.Role;
import domain.Enum.Team;
import domain.piece.Piece;

import java.util.*;

public class ChessBoard {
    private Map<Position, Piece> squares = new HashMap<>();

    public ChessBoard (){
        initializeChessBoard();
    }

    private void initializeChessBoard (){
        for ( Team team : Team.values()){
            for (Role role : Role.values ()){
                for ( int order = 1; order <= role.getMaxCount(); order++) {
                    Piece piece = Piece.createPiece(team,role);
                    Position position = piece.getInitialPosition(order) ;
                    squares.put(position, piece);
                }
            }
        }
    }

    public  Map<Position, Piece> getBoard (){
        return this.squares;
    }
}

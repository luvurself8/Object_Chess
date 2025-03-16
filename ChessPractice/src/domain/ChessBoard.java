package domain;

import domain.piece.Piece;

import java.util.Map;

public class ChessBoard {
    private final Map<Position, Piece> squares;

        public ChessBoard(Map<Position, Piece> squares) {
        this.squares = squares;
    }
    public ChessBoard (){
        Map<Position, Piece> squares;
        for (Role role : Role.values()){

        }
        this.squares = squares;
    }

}

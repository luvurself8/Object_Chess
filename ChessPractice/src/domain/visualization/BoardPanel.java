package domain.visualization;

import javax.swing.*;
import java.awt.*;

import domain.ChessBoard;
import domain.Position;
import domain.piece.Piece;
import java.util.Map;

public class BoardPanel extends JPanel {
    private final int BOARD_SIZE = 8;
    private JLabel[][] boardSquares;

    private ChessBoard chessBoard;

    public BoardPanel(ChessBoard chessBoard) {
        this.chessBoard = chessBoard;
        setLayout(new GridLayout(BOARD_SIZE + 2, BOARD_SIZE + 2)); // 10x10 그리드
        initializeBoard();
        initializePieces();
    }

    private void initializeBoard() {
        int gridSize = BOARD_SIZE + 2;
        boardSquares = new JLabel[BOARD_SIZE][BOARD_SIZE];

        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                if (row == 0 || row == gridSize - 1 || col == 0 || col == gridSize - 1) {
                    JLabel label = new JLabel("", SwingConstants.CENTER);
                    label.setFont(new Font("SansSerif", Font.BOLD, 16));
                    if ((row == 0 || row == gridSize - 1) && col != 0 && col != gridSize - 1) {
                        label.setText(String.valueOf((char) ('a' + col - 1)));
                    }
                    if ((col == 0 || col == gridSize - 1) && row != 0 && row != gridSize - 1) {
                        label.setText(String.valueOf(BOARD_SIZE - (row - 1)));
                    }
                    add(label);
                } else {
                    int boardRow = row - 1;
                    int boardCol = col - 1;
                    boardSquares[boardRow][boardCol] = new JLabel("", SwingConstants.CENTER);
                    boardSquares[boardRow][boardCol].setOpaque(true);
                    boardSquares[boardRow][boardCol].setFont(new Font("SansSerif", Font.PLAIN, 48));
                    if ((boardRow + boardCol) % 2 == 0) {
                        boardSquares[boardRow][boardCol].setBackground(Color.GRAY);
                    } else {
                        boardSquares[boardRow][boardCol].setBackground(Color.WHITE);
                    }
                    boardSquares[boardRow][boardCol].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    add(boardSquares[boardRow][boardCol]);
                }
            }
        }
    }

    private void initializePieces() {
        Map<Position, Piece> squares = chessBoard.getBoard();
        squares.forEach((position, piece) -> {
            int row = position.getRow().getRow() - 1;
            int column = (int) position.getColumn().getColumn() - 96 - 1;

            boardSquares[row][column].setText(piece.getPositionText());
        });
    }
}

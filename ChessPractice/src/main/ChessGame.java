package main;



import domain.visualization.Visualization;

import javax.swing.*;



public class ChessGame  extends JFrame {

    private Visualization visualizedChessBoard;

    public ChessGame() {
        visualizedChessBoard = new Visualization ();
        visualizedChessBoard.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ChessGame());

    }
}

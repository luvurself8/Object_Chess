package main;


import javax.swing.*;
import java.awt.*;




public class ChessGame  extends JFrame {

    public ChessGame( ) {
        initializeChessGame();
    }

    private void initializeChessGame (){

        GameOperation gameOperation = new GameOperation();
        // JSplitPane을 사용하여 왼쪽에 체스판, 오른쪽에 콘솔 배치
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, gameOperation.getBoardPanel(), gameOperation.getConsolePanel());

        splitPane.setDividerLocation(700);
        splitPane.setResizeWeight(0.0);
        add(splitPane, BorderLayout.CENTER);
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 패널 시각화
        this.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ChessGame::new);
    }
}

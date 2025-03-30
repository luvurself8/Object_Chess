package domain.visualization;

import domain.ChessBoard;

import javax.swing.*;
import java.awt.*;

public class Visualization extends JFrame {
    private final int BOARD_SIZE = 8;
    private ChessBoard chessBoard = new ChessBoard();

    public Visualization() {
        setTitle("Go Chess");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 체스판과 기물 초기화
        BoardPanel boardPanel = new BoardPanel(chessBoard);

        // 콘솔 패널 초기화
        ConsolePanel consolePanel = new ConsolePanel();

        // JSplitPane을 사용하여 왼쪽에 체스판, 오른쪽에 콘솔 배치
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, boardPanel, consolePanel);
        boardPanel.setPreferredSize(new Dimension(700, 700));
        boardPanel.setMinimumSize(new Dimension(700, 700));
        splitPane.setDividerLocation(700);
        splitPane.setResizeWeight(0.0);
        add(splitPane, BorderLayout.CENTER);
    }
}

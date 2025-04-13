package main;


import domain.Enum.Team;
import domain.visualization.BoardPanel;
import domain.visualization.ConsolePanel;

import javax.swing.*;
import java.awt.*;


public class ChessGame  extends JFrame {

    private Team turn = Team.BLACK;
    private BoardPanel boardPanel = new BoardPanel();
    private ConsolePanel consolePanel = new ConsolePanel();

    public ChessGame( ) {
        initializeChessGame();
    }
    private void initializeChessGame (){


        // JSplitPane을 사용하여 왼쪽에 체스판, 오른쪽에 콘솔 배치
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, boardPanel, consolePanel);

        splitPane.setDividerLocation(700);
        splitPane.setResizeWeight(0.0);
        add(splitPane, BorderLayout.CENTER);
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        consolePanel.consoleInput.addActionListener(e -> processCommand());
        consolePanel.printChangeTurn(this.turn);
        // 패널 시각화
        this.setVisible(true);
    }
    private void processCommand (){

        String consoleText = consolePanel.getConsoleText();
        consolePanel.initializeConsoleInput();

        if(!consolePanel.isCommandValid(consoleText)){
            consolePanel.appendToConsole("\n유효한 명령어를 입력해주세요");
            return;
        };

        boardPanel.processCommand( this.turn, consoleText );
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ChessGame());
        //System.out.println(Role.BISHOP.toString()); //);Role.BISHOP.toString();
    }
}

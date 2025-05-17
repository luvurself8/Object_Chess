package main;


import domain.Enum.CommandStatus;
import domain.Enum.GameStatus;
import domain.Enum.Team;
import domain.commandProcessing.Command;
import domain.commandProcessing.CommandFactory;
import domain.commandProcessing.MovePieceCommand;
import domain.commandProcessing.SelectPieceCommand;
import domain.visualization.BoardPanel;
import domain.visualization.ConsolePanel;

import javax.swing.*;
import java.awt.*;




public class ChessGame  extends JFrame {

    private Team turn = Team.BLACK;
    private final BoardPanel boardPanel = new BoardPanel();
    private final ConsolePanel consolePanel = new ConsolePanel();
    private GameStatus gameStatus = GameStatus.SELECT_PIECE;
    private Command previousCommand;

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


        consolePanel.consoleInput.addActionListener(e -> manageCommand());
        consolePanel.printChangeTurn(this.turn);
        // 패널 시각화
        this.setVisible(true);
    }

    private void manageCommand() {
        String input = consolePanel.getInput();

        switch (gameStatus) {
            case SELECT_PIECE -> selectPiece(input);
            case MOVE_PIECE -> movePiece(input);
            case CONFIRM_PROMOTE -> confirmAndExecutePromote(input);

        }
        consolePanel.clearInput();
    }

    private void selectPiece(String input) {
        SelectPieceCommand cmd = CommandFactory.createSelectPieceCommand(turn, gameStatus, input);
        if(!cmd.isCommandMessageValid()){
            consolePanel.printMessage(cmd.getOutputMessage());
            changeGameStatus(GameStatus.SELECT_PIECE,cmd);
            return;
        }
        boardPanel.processCommand(cmd);
        if(cmd.getStatus() == CommandStatus.FAILED){
            consolePanel.printMessage(cmd.getOutputMessage());
            changeGameStatus(GameStatus.SELECT_PIECE,cmd);
            return;
        }
        if(cmd.getStatus() == CommandStatus.SUCCESS){
            consolePanel.printMessage(cmd.getOutputMessage());
            changeGameStatus(GameStatus.MOVE_PIECE,cmd);
        }
    }

    private void movePiece(String input) {

        MovePieceCommand cmd = CommandFactory.createMovePieceCommand(turn, gameStatus, input, previousCommand);
        if(!cmd.isCommandMessageValid()){
            consolePanel.printMessage(cmd.getOutputMessage());
            this.previousCommand = cmd;
            changeGameStatus(GameStatus.SELECT_PIECE,cmd);
            return;
        }
        if(cmd.canPromote()){
            this.previousCommand = cmd;
            changeGameStatus(GameStatus.CONFIRM_PROMOTE ,cmd);
            return;
        }
        boardPanel.processCommand(cmd);
        if(cmd.getStatus() == CommandStatus.FAILED){
            consolePanel.printMessage(cmd.getOutputMessage());
            this.previousCommand = cmd;
            changeGameStatus(GameStatus.SELECT_PIECE,cmd);
            return;
        }
        if(cmd.getStatus() == CommandStatus.SUCCESS){
            consolePanel.printMessage(cmd.getOutputMessage());
            this.previousCommand = cmd;
            if(cmd.isGameEnd()) {
                changeGameStatus(GameStatus.GAME_OVER,cmd);
                return;
            }
            changeGameStatus(GameStatus.SELECT_PIECE,cmd);
            changeTurn();
        }
    }

    //private void hasMoveSelection(){
    //    if(!boardPanel.hasMoveSelection(this.team)){
    //        changeGameStatus(GameStatus.GAME_OVER,cmd);
    //    }
    //}

    private void confirmAndExecutePromote(String input) {
        MovePieceCommand cmd = (MovePieceCommand) previousCommand;
        cmd.setPromotionRole(input);
        boardPanel.processCommand(cmd);
        if(cmd.getStatus() == CommandStatus.FAILED){
            consolePanel.printMessage(cmd.getOutputMessage());
            this.previousCommand = cmd;
            changeGameStatus(GameStatus.SELECT_PIECE,cmd);
            return;
        }
        if(cmd.getStatus() == CommandStatus.SUCCESS){
            consolePanel.printMessage(cmd.getOutputMessage());
            this.previousCommand = cmd;
            changeGameStatus(GameStatus.SELECT_PIECE,cmd);
            changeTurn();
        }
    }

    private void changeGameStatus(GameStatus status, Command cmd) {
        this.previousCommand = cmd;
        switch (status) {
            case SELECT_PIECE -> consolePanel.printMessage("Select a piece to move.");
            case MOVE_PIECE -> consolePanel.printMessage("Select a target position to move the piece.");
            case CONFIRM_PROMOTE -> consolePanel.printMessage("Enter the role you want to promote: (Rook, Knight, Bishop, Queen) \n enter anything else if you dont want to promote");
            case GAME_OVER -> consolePanel.printMessage("Game over.");
        }
        this.gameStatus = status;
    }

    private void changeTurn() {
        this.turn = this.turn.getOppositTeam();
        consolePanel.printChangeTurn(this.turn);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ChessGame::new);
    }
}

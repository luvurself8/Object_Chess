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

import java.awt.*;

public class GameOperation {
    private Team turn = Team.BLACK;
    private final BoardPanel boardPanel = new BoardPanel();
    private final ConsolePanel consolePanel = new ConsolePanel();
    private GameStatus gameStatus = GameStatus.SELECT_PIECE;
    private Command previousCommand;

    public GameOperation(){
        consolePanel.consoleInput.addActionListener(e -> manageCommand());
        consolePanel.printInitialTurn(this.turn);
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
            changeTurn();
            changeGameStatus(GameStatus.SELECT_PIECE,cmd);
        }
    }

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
            changeTurn();
            changeGameStatus(GameStatus.SELECT_PIECE,cmd);
        }
    }

    private void changeGameStatus(GameStatus status, Command cmd) {
        this.previousCommand = cmd;
        switch (status) {
            case SELECT_PIECE -> consolePanel.printMessage("Select a piece to move. (1a)");
            case MOVE_PIECE -> consolePanel.printMessage("Select a target position to move the piece.");
            case CONFIRM_PROMOTE -> consolePanel.printMessage("Enter the role you want to promote: (Rook, Knight, Bishop, Queen)\nenter anything else if you dont want to promote");
            case GAME_OVER -> consolePanel.printMessage("Game over.");
        }
        this.gameStatus = status;
    }

    private void changeTurn() {
        this.turn = this.turn.getOppositTeam();
        consolePanel.printChangeTurn(this.turn);
    }

    public Component getBoardPanel() {
        return boardPanel;
    }

    public Component getConsolePanel(){
        return consolePanel;
    }
}

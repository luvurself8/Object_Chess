package domain.commandProcessing;
import domain.Enum.GameStatus;
import domain.Enum.Team;

public class CommandFactory {

    public static SelectPieceCommand createSelectPieceCommand (Team team, GameStatus gameStatus, String commandMessage){
        return new SelectPieceCommand(team, commandMessage);
    }
    public static MovePieceCommand createMovePieceCommand (Team team, GameStatus gameStatus, String commandMessage, Command previousCommand){
        return new MovePieceCommand(team, gameStatus, commandMessage, previousCommand);
    }

}

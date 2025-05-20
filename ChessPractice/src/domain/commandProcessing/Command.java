package domain.commandProcessing;
import domain.Enum.CommandStatus;
import domain.Enum.GameStatus;
import domain.Enum.Team;

import static domain.board.BoardUtil.*;

public abstract class Command {
   protected CommandStatus status;
   protected GameStatus commandType;
   protected String commandMessage;
   protected String outputMessage;
   protected Team team;

   public Command(Team team, GameStatus commandType, String commandMessage) {
        this.commandType = commandType;
        this.commandMessage = commandMessage;
        this.status = CommandStatus.PROCESSING;
        this.team = team;
   }

   public CommandStatus getStatus() {
          return status;
   }

   public String getOutputMessage() {
       return outputMessage;
   }

   public Team getTeam() {
       return team;
   }

   protected boolean isPositionStringValid (String positionString) {
       String regex = String.format("[%d-%d][%c-%c]", ROW_START, ROW_END, COLUMN_START, COLUMN_END);
       return positionString.matches(regex);
   }

   public void setErrorMessage(String message) {
          this.outputMessage = message;
          this.status = CommandStatus.FAILED;
   }

   public void setSuccessMessage(String message) {
          this.outputMessage = message;
          this.status = CommandStatus.SUCCESS;
   }

   public abstract boolean isCommandMessageValid();
}

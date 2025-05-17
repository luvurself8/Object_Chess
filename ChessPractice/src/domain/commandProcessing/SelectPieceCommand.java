package domain.commandProcessing;
import domain.Enum.GameStatus;
import domain.Enum.Team;
import domain.board.Movement;
import domain.board.Position;

import java.util.List;

public class SelectPieceCommand extends Command {

    protected Position sourcePosition;
    protected List<Movement> movableList;

    public SelectPieceCommand(Team team, String commandMessage) {
        super(team, GameStatus.SELECT_PIECE, commandMessage);
    }

    public Position getSourcePosition(){
        return this.sourcePosition;
    }

    public void setMovableList(List<Movement> movableList) {
        this.movableList = movableList;
        if (movableList.isEmpty()) {
            setErrorMessage("No movable positions available");
        } else {
            StringBuilder message = new StringBuilder();
            message.append("You can move to: ");
            movableList.forEach(movement-> message.append(movement.getTargetPosition().toString()).append(" "));
            setSuccessMessage(message.toString());
        }
    }

    public Movement getMovement(Position targetPosition) {
        for (Movement movement : movableList) {
            if (movement.getTargetPosition().equals(targetPosition)) {
                return movement;
            }
        }
        return null;
    }

    @Override
    public boolean isCommandMessageValid(){

        if(commandMessage.isBlank()){
            setErrorMessage("Command message is empty");
            return false;
        }
        if(!isPositionStringValid(this.commandMessage)){
            setErrorMessage("Invalid command message format - must be a number followed by a letter");
            return false;
        }
        this.sourcePosition = new Position(this.commandMessage);
        return true;
    }
}

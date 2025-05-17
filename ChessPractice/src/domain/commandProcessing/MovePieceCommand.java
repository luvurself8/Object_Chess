package domain.commandProcessing;

import domain.Enum.GameStatus;
import domain.Enum.MoveType;
import domain.Enum.Role;
import domain.Enum.Team;
import domain.board.Movement;
import domain.board.Position;
import domain.board.PromotionMovement;


public class MovePieceCommand extends Command {
    private Command previousCommand;
    private Movement move;

    public MovePieceCommand(Team team, GameStatus gameStatus, String commandMessage, Command previousCommand) {
        super(team, gameStatus, commandMessage);
        this.previousCommand = previousCommand;
    }

    public boolean canPromote() {
        return this.move.isMoveType(MoveType.PROMOTE);
    }

    public boolean setPromotionRole(String input) {
        Role role = Role.getRole(input);
        if(role.isValidPromotionChoice()){
            this.move = new PromotionMovement(this.move, role);
            return true;
        }
        return false;
    }

    public Movement getMovement(){
        return move;
    }

    public boolean isGameEnd (){
        return this.move.isMovementCatchKing();
    }

    @Override
    public boolean isCommandMessageValid() {
        if(this.commandMessage.isBlank()){
            setErrorMessage("Please enter a message.");
            return false;
        }
        if (!(this.previousCommand instanceof SelectPieceCommand)){
            setErrorMessage("Invalid command sequence. Please select a piece first.");
            return false; // 다시 SELECT_PIECE 상태로 돌아가야 함
        }
        if(!isPositionStringValid(this.commandMessage)){
            setErrorMessage("Invalid command message format - must be a number followed by a letter");
            return false;
        }
        this.move = ((SelectPieceCommand) this.previousCommand).getMovement(new Position(this.commandMessage));
        if(this.move == null){
            setErrorMessage("Invalid move - the selected piece cannot move to the target position");
            return false;
        }
        return true;
    }
}

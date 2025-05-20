package domain.move;

import domain.Enum.MoveType;
import domain.Enum.Role;
import domain.board.Position;

public class PromotionMovement extends Movement {
    private Role promoteRole;

    public PromotionMovement(Movement move, Role promoteRole) {
        super(move.getSourcePosition(), move.getTargetPosition(), MoveType.PROMOTE);
        this.promoteRole = promoteRole;
    }

    public PromotionMovement(Position sourcePosition, Position targetPosition , Role promoteRole) {
        super(sourcePosition, targetPosition, MoveType.PROMOTE);
        this.promoteRole= promoteRole;
    }

    public Role getPromotionRole() {
        return this.promoteRole;
    }
}

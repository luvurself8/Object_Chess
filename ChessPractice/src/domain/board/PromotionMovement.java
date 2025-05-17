package domain.board;

import domain.Enum.MoveType;
import domain.Enum.Role;

public class PromotionMovement extends Movement {
    private Role promoteRole;

    public PromotionMovement(Movement move, Role promoteRole) {
        super(move.getSourcePiece(), move.getTargetPiece(), MoveType.PROMOTE);
        this.promoteRole = promoteRole;
    }

    public Role getPromotionRole() {
        return this.promoteRole;
    }
}

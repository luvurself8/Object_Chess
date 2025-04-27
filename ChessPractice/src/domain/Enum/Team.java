package domain.Enum;

public enum Team {
    BLACK,
    WHITE,
    NONE;

    public Team getOppositTeam(){
        if(this == BLACK ){
            return WHITE;
        }
        if (this == WHITE){
            return BLACK;
        }
        return NONE;
    }
}

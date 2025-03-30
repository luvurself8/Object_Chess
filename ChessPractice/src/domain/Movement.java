package domain;

import domain.Enum.Direction;

import static domain.Enum.Direction.*;
import static java.lang.Math.abs;

public class Movement {

    protected Direction dir;
    protected int length;

    public Movement (){

    }
    public Movement (Direction dir, int length){
        this.dir = dir;
        this.length = length;
    }
    public void setDirection (Direction dir){
        this.dir = dir;
    }
    public void setLength (int length){
        this.length = length;
    }
    public Direction getDirection (){
        return this.dir;
    }
    public int getLength (){
        return this.length;
    }

    public  Movement getDirection (int gapRow , int gapColumn ){
        /*
        int nowRow = source.getRow().getRow();
        int nowColumn = ((int) source.getColumn().getColumn());
        int targetRow = source.getRow().getRow();
        int targetColumn = ((int) source.getColumn().getColumn());

        int multiple = 1;
        if (team == Team.BLACK) {
            multiple = -1;
        }

        int gapRow = multiple * (nowRow - targetRow);
        int gapColumn = multiple * (nowColumn - targetColumn);
        */
        Movement move = new Movement();
        if (gapRow == 0 && gapColumn == 0){
            throw new IllegalArgumentException("Same Position");
        }
        else if (abs (gapRow) ==  abs (gapColumn)){
            move.setDirection (DIAGNOAL);
            move.setLength (abs (gapRow) );
            return move;
        }
        else if ((abs (gapRow) == 1 &&  abs (gapColumn) ==2 )
                ||(abs (gapRow) == 2 &&  abs (gapColumn) ==1 )){
            move.setDirection(L);
            move.setLength(0);
            return move;
        }
        else if (gapRow == 0 &&  gapColumn >0){
            move.setDirection (LEFT);
            move.setLength (abs (gapColumn) );
            return move;
        }
        else if (gapRow == 0 && gapColumn < 0){
            move.setDirection (RIGHT);
            move.setLength (abs (gapColumn) );
            return move;
        }
        else if (gapRow >0 && gapColumn >0){
            move.setDirection (DOWN);
            move.setLength (abs (gapRow) );
            return move;
        }
        else if (gapRow < 0 && gapColumn <0){
            move.setDirection (UP);
            move.setLength (abs (gapRow) );
            return move;
        }
        return move;
    }
}

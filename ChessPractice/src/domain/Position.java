package domain;

import static java.lang.Math.abs;

public class Position {
    protected int row;
    //Column은 세로줄을 의미하며, a부터 h까지 알파벳으로 표현됩니다.
    protected char column;
    
    //생성자
    public Position (int row, char column){
        this.row = row;
        this.column = column;
    }

    public Position (String text) {
        this.row = Character.getNumericValue(text.charAt(0));
        this.column = text.charAt(1);
    }

    public Position () {

    }
    public void setRow (int row) {
        this.row = (row) ;
    }
   public void setColumn(char column){
        this.column = column;
    }

    public int getRow (){
        return this.row ;
    }
    public char getColumn (){
        return this.column;
    }

    @Override
    public String toString() {
        return "Position{" +
                "row=" + row +
                ", column=" + column +
                '}';
    }
}

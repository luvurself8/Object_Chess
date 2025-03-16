package domain;

import static domain.Direction.*;
import static java.lang.Math.abs;

public class Position {
    public class Row {
        private final String  ROW_NOT_VALID_EXCEPTION ="행이 유효하지 않습니다.";
        protected int row;
        
        // Row 생성자
        public Row (int row){
            if (isRowValid(row)){
                this.row = row;
            }
        }

        // Row setter
        public void setRow (int row) {
            if (isRowValid(row)){
                this.row = row;
            }
        }

        // Row getter
        public int getRow (){
            return this.row;
        }
        
        //Row 유효성 체크
        protected boolean isRowValid (int row){
            if( row >= 1 && row <= 8 ) {
                return true;
            }else{
                throw new IllegalArgumentException(ROW_NOT_VALID_EXCEPTION);
            }
        }
    }
    public class Column {
        private final String  COLUMN_NOT_VALID_EXCEPTION ="열이 유효하지 않습니다.";
        protected char column;

        // Cloumn 생성자
        public Column (char column){
            if (isColumnValid(column)){
                this.column = column;
            }
        }

        // Cloumn setter
        public void setColumn (char column) {
            if (isColumnValid(column)){
                this.column = column;
            }
        }

        // Cloumn getter
        public int getColumn (){
            return this.column;
        }


        // Column 이 valid 한지체크
        protected boolean isColumnValid (char column){
            int inputColumn = ((int) column-96);
            if( inputColumn >= 1 && inputColumn <= 8 ) {
                return true;
            }
            else {
                throw new IllegalArgumentException(COLUMN_NOT_VALID_EXCEPTION);
            }
        }
    }


    protected Row row;
    //Column은 세로줄을 의미하며, a부터 h까지 알파벳으로 표현됩니다.
    protected Column column;
    
    //생성자
    public Position (int row, char column){
        this.row = new Row (row);
        this.column = new Column (column);

    }
    public Position (Row row, Column column){
        this.row = row;
        this.column = column;

    }

    public void setRow (int row) {
        this.row.setRow(row) ;
    }
    public void setRow (Row row) {
        this.row=row ;
    }
    public void setColumn(char column){
        this.column.setColumn (column);
    }
    public void setColumn(Column column){
        this.column= column;
    }
    public Row getRow (){
        return this.row ;
    }
    public Column getColumn (){
        return this.column;
    }

}

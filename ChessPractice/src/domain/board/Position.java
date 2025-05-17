package domain.board;

import static java.util.Objects.*;

public class Position {
    private int row;
    private char column;

    //생성자
    public Position(int row, char column) {
        this.row = row;
        this.column = column;
    }

    public Position(String text) {
        this.row = Character.getNumericValue(text.charAt(0));
        this.column = text.charAt(1);
    }

    public int getRow() {
        return this.row;
    }

    public char getColumn() {
        return this.column;
    }

    @Override
    public String toString() {
        return "Position{" +
                "row=" + row +
                ", column=" + column +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return row == position.row && column == position.column;
    }

    @Override
    public int hashCode() {
        return hash(row, column);
    }

}

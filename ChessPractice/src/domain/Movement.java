package domain;

import domain.Enum.Direction;
import domain.Enum.Team;

import java.util.ArrayList;
import java.util.List;

import static domain.Enum.Direction.*;
import static java.lang.Math.abs;

public class Movement {

    protected Direction dir;
    protected int length;
    protected List <Position> positionBetween = new ArrayList<>();

    public Movement (Direction dir, int length){
        this.dir = dir;
        this.length = length;
    }

    private static final char COLUMN_START = 'a';

    public Movement(Position source, Position target, Team team) {
        int nowRow = source.getRow();
        char nowColumnChar = source.getColumn();
        int nowColumn = nowColumnChar - COLUMN_START;

        int targetRow = target.getRow();
        char targetColumnChar = target.getColumn();
        int targetColumn = targetColumnChar - COLUMN_START;

        int rowDiff = targetRow - nowRow;
        int colDiff = targetColumn - nowColumn;

        int moveDirection = (team == Team.BLACK) ? -1 : 1;

        int gapRow = moveDirection * rowDiff;
        int gapColumn = moveDirection * colDiff;

        if (gapRow == 0 && gapColumn == 0) {
            throw new IllegalArgumentException("Same Position");
        } else if (abs(gapRow) == abs(gapColumn)) {
            this.dir = DIAGNOAL;
            this.length = abs(gapRow);
            int rowIncrement = (gapRow > 0) ? 1 : -1;
            int colIncrement = (gapColumn > 0) ? 1 : -1;
            for (int i = 1; i < abs(gapRow); i++) {
                positionBetween.add(new Position(nowRow + moveDirection * i * rowIncrement, (char) (nowColumnChar + i * colIncrement)));
            }
        } else if ((abs(gapRow) == 1 && abs(gapColumn) == 2) || (abs(gapRow) == 2 && abs(gapColumn) == 1)) {
            this.dir = L;
            this.length = Math.max(abs(gapRow), abs(gapColumn));
            if (abs(gapRow) == 1 && abs(gapColumn) == 2) {
                positionBetween.add(new Position(targetRow, (char) (nowColumnChar + moveDirection * 1)));
                positionBetween.add(new Position(targetRow, (char) (nowColumnChar + moveDirection * 2)));
            } else { // abs(gapRow) == 2 && abs(gapColumn) == 1
                positionBetween.add(new Position(nowRow + moveDirection * 1, targetColumnChar));
                positionBetween.add(new Position(nowRow + moveDirection * 2, targetColumnChar));
            }
        } else if (gapRow == 0 && gapColumn != 0) {
            this.dir = (gapColumn > 0) ? RIGHT : LEFT;
            this.length = abs(gapColumn);
            int colIncrement = (gapColumn > 0) ? 1 : -1;
            for (int i = 1; i < abs(gapColumn); i++) {
                positionBetween.add(new Position(nowRow, (char) (nowColumnChar + i * colIncrement)));
            }
        } else if (gapRow != 0 && gapColumn == 0) {
            this.dir = (gapRow > 0) ? DOWN : UP;
            this.length = abs(gapRow);
            int rowIncrement = (gapRow > 0) ? 1 : -1;
            for (int i = 1; i < abs(gapRow); i++) {
                positionBetween.add(new Position(nowRow + moveDirection * i * rowIncrement, nowColumnChar));
            }
        }
    }

    public  List <Position> getPositionBetween () {
        return positionBetween;
    }

    @Override
    public String toString() {
        return "Movement{" +
                "dir=" + dir +
                ", length=" + length +
                '}';
    }
}

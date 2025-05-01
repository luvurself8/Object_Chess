package domain;

import domain.Enum.Direction;
import domain.Enum.Team;

import java.util.ArrayList;
import java.util.List;

import static domain.Enum.Direction.*;
import static java.lang.Math.abs;

public class Movement {

    private Direction dir;
    private int length;
    private List<Position> positionBetween = new ArrayList<>();
    private Team team;

    private static final char COLUMN_START = 'a';

    /**
     *
     * @param sourcePosition
     * @param targetPosition
     * @param team
     */
    public Movement(Position sourcePosition, Position targetPosition, Team team) {
        this.team = team;
        int nowRow = sourcePosition.getRow();
        char nowColumnChar = sourcePosition.getColumn();
        int nowColumn = nowColumnChar - COLUMN_START;

        int targetRow = targetPosition.getRow();
        char targetColumnChar = targetPosition.getColumn();
        int targetColumn = targetPosition.getColumn() - COLUMN_START;

        int gapRow = targetRow - nowRow;
        int gapColumn = targetColumn - nowColumn;

        int colIncrement = (gapColumn > 0) ? 1 : -1;
        int rowIncrement = (gapRow > 0) ? 1 : -1;

        if (gapRow == gapColumn) {
            this.dir = DIAGNOAL;
            this.length = abs(gapRow);
            for (int i = 1; i < abs(gapRow); i++) {
                this.positionBetween.add(new Position(nowRow + i * rowIncrement, (char) (nowColumnChar + i * rowIncrement)));
            }
        } else if ((abs(gapRow) == 1 && abs(gapColumn) == 2) || (abs(gapRow) == 2 && abs(gapColumn) == 1)) {
            this.dir = L;
            this.length = Math.max(abs(gapRow), abs(gapColumn));
        } else if (gapRow == 0 && gapColumn != 0) {
            this.dir = HORIZONTAL;
            this.length = abs(gapColumn);
            for (int i = 1; i < abs(gapColumn); i++) {
                positionBetween.add(new Position(nowRow, (char) (nowColumnChar + i * colIncrement)));
            }
        } else if (gapRow != 0 && gapColumn == 0) {
            this.dir = (gapRow < 0) ? UP : DOWN;
            this.length = abs(gapRow);
            for (int i = 1; i < abs(gapRow); i++) {
                this.positionBetween.add(new Position(nowRow + rowIncrement * i , nowColumnChar));
            }
            changeDirection();
        } else {
            this.dir = NONE;
        }
    }

    public List<Position> getPositionBetween() {
        return this.positionBetween;
    }

    public Direction getDirection() {
        return this.dir;
    }

    public int getLength() {
        return this.length;
    }

    private void changeDirection() {
        if (this.team == Team.WHITE) {
            if (this.dir == Direction.UP) {
                this.dir = Direction.DOWN;
            }
            if (this.dir == Direction.DOWN) {
                this.dir = Direction.UP;
            }
        }
    }

    @Override
    public String toString() {
        return "Movement{" +
                "dir=" + dir +
                ", length=" + length +
                ", positionBetween=" + positionBetween +
                ", team=" + team +
                '}';
    }
}

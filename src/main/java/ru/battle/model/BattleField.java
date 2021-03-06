package ru.battle.model;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by onotole on 20/05/2017.
 */
@Slf4j
public class BattleField {
    public static final int SIZE = 10;
    private List<List<Cell>> field;
    private Random random = new Random();

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                sb.append(field.get(i).get(j).symbol);
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public BattleField() {
        field = new ArrayList<>();
        getEmptyField();
    }

    public void getEmptyField() {
        fillFieldWith(Cell.EMPTY);
    }

    public void getUnknownField() {
        fillFieldWith(Cell.UNKNOWN);
    }

    public void put(int x, int y, Cell cell) {
        log.info("mark cell: " + x + ":" + y + " with: " + cell);
        if (x < 0 || y < 0 || x >= SIZE || y >= SIZE) return;
        field.get(x).set(y, cell);
    }

    public String getConfiguration() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (field.get(i).get(j) == Cell.SHIP) {
                    sb.append(i+1).append(",").append(j+1).append(";");
                }
            }
        }
        String str = sb.toString();
        return str.substring(0, str.length()-1);
    }

    public String getCell(int x, int y) {
        return field.get(x).get(y).symbol;
    }

    public boolean isCellType(Point point, Cell cell) {
        return isCellType(point.getX(), point.getY(), cell);
    }

    public boolean isExistCellInAround(int x, int y, Cell cell) {
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (isCellType(x + i, y + j, cell)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isCellType(int x, int y, Cell cell) {
        if (x < 0 || y < 0 || x >= SIZE || y >= SIZE) {
            return false;
        }
        return field.get(x).get(y) == cell;
    }

    private void fillFieldWith(Cell cell) {
        field = new ArrayList<>();
        ArrayList<Cell> list;
        for (int i = 0; i < SIZE; i++) {
            list = new ArrayList<>();

            for (int j = 0; j < SIZE; j++) {
                list.add(cell);
            }
            field.add(list);
        }
    }

    public String getRandomUnknownCell() {
        boolean success = false;
        int x = 0;
        int y = 0;
        while (! success) {
            x = random.nextInt(SIZE);
            y = random.nextInt(SIZE);
            if (field.get(x).get(y) == Cell.UNKNOWN) {
                success = true;
            }
        }
        return "" + (x + 1) + "," + (y + 1);
    }


    public enum Cell {
        EMPTY("o"), SHIP("X"), WRECK("*"), MINE("@"), UNKNOWN(".");


        private String symbol;
        Cell(String symbol) {
            this.symbol = symbol;
        }
    }

}
